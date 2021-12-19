package com.example.geowalk.services;

import com.example.geowalk.exceptions.BadRequestException;
import com.example.geowalk.exceptions.NotFoundException;
import com.example.geowalk.models.dto.ObjectMapperUtils;
import com.example.geowalk.models.dto.requests.BlogPostRequest;
import com.example.geowalk.models.dto.responses.BlogPostResponse;
import com.example.geowalk.models.dto.responses.BlogPostShortcutResponse;
import com.example.geowalk.models.entities.*;
import com.example.geowalk.models.repositories.BlogPostRepo;
import com.example.geowalk.models.repositories.TravelRouteRepo;
import com.example.geowalk.models.repositories.TravelStopRepo;
import com.example.geowalk.utils.SwearWordsFilter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service
public class BlogPostService {

    private final BlogPostRepo blogPostRepository;
    private final TravelRouteRepo travelRouteRepository;
    private final TravelStopRepo travelStopRepository;
    private final UserService userService;
    private final TravelStopService travelStopService;
    private final TravelRouteService travelRouteService;
    private final TagService tagService;

    private ModelMapper mapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(BlogPostService.class);
    private final SwearWordsFilter swearWordsFilter;

    private final String LOGGER_GET_BLOGPOST_FAILED = "Getting BlogPost failed:\t";
    private final String LOGGER_CREATE_BLOGPOST_FAILED = "Creating BlogPost failed:\t";
    private final String LOGGER_UPDATE_BLOGPOST_FAILED = "Updating BlogPost failed:\t";
    private final String LOGGER_DELETE_BLOGPOST_FAILED = "Deleting BlogPost failed:\t";

    private final String NOT_FOUND_BLOG_POST = "Blog post with given id not found";
    private final String BAD_REQUEST_BLOG_POST = "Blog post request has bad body";
    private final String SWEAR_WORDS_FILTER_MESSAGE = "Post moved to verification due to profanity";
    private final TagService tagRepo;

    public BlogPostService(BlogPostRepo blogPostRepository, TravelRouteRepo travelRouteRepository, TravelStopRepo travelStopRepository, UserService userService, TravelStopService travelStopService, TravelRouteService travelRouteService, TagService tagService, SwearWordsFilter swearWordsFilter, TagService tagRepo) {
        this.blogPostRepository = blogPostRepository;
        this.travelRouteRepository = travelRouteRepository;
        this.travelStopRepository = travelStopRepository;
        this.userService = userService;
        this.travelStopService = travelStopService;
        this.travelRouteService = travelRouteService;
        this.tagService = tagService;
        this.swearWordsFilter = swearWordsFilter;
        this.tagRepo = tagRepo;
    }

    public void createBlogPost(BlogPostRequest toCreate) {

        User user = userService.getUser(toCreate.getUserId());

        BlogPost blogPost = new BlogPost(
                toCreate.getContent(),
                toCreate.getTitle()
        );
        blogPost.setUser(user);

        if (toCreate.getTravelRouteRequestList() != null && toCreate.getTravelStopRequestList() != null) {
            logger.warn(LOGGER_GET_BLOGPOST_FAILED + BAD_REQUEST_BLOG_POST);
            throw new BadRequestException(BAD_REQUEST_BLOG_POST);
        }

        if (toCreate.getTravelRouteRequestList() != null) {
            // TODO: 05.12.2021 TravelRoute nie sprawdzam bo jak ktos moze tworzyc sobie go z innya nazwa albo innyą trudnościa.
            List<TravelRoute> travelRouteList = new ArrayList<>();
            toCreate.getTravelRouteRequestList().forEach(e -> {
                if (e.getTravelStopList().size() < 2) {
                    logger.warn(LOGGER_GET_BLOGPOST_FAILED + BAD_REQUEST_BLOG_POST);
                    throw new BadRequestException(BAD_REQUEST_BLOG_POST);
                }
                travelRouteList.add(travelRouteService.createTravelRoute(e));
            });
            travelRouteRepository.saveAll(travelRouteList);
            blogPost.getTravelRoutes().addAll(travelRouteList);
        } else if (toCreate.getTravelStopRequestList() != null) {
            List<TravelStop> travelStopList = new ArrayList<>(travelStopService.getOrCreateTravelStopsByLocation(toCreate.getTravelStopRequestList()));
            blogPost.getTravelStops().addAll(travelStopList);
        }

        if (toCreate.getTagList() != null) {
            List<Tag> tagList = new ArrayList<>();
            List<Tag> newTagList = new ArrayList<>();
            toCreate.getTagList().forEach(e -> {
                Tag tag = tagService.findTagByName(e);
                if (tag != null)
                    tagList.add(tag);
                else {
                    tag = new Tag(e);
                    newTagList.add(tag);
                }
            });
            tagService.saveAll(newTagList);
            tagList.addAll(newTagList);
            blogPost.getTags().addAll(tagList);
        }

        // TODO: 05.12.2021 Dodawanie zdjęć

        if(swearWordsFilter.hasSwearWord(toCreate.getContent())) {
            blogPost.setNeedToVerify(true);
        }
        boolean needToVerify = blogPost.isNeedToVerify();
        blogPostRepository.save(blogPost);
        logger.info(needToVerify ? SWEAR_WORDS_FILTER_MESSAGE : "Creating post success");
    }


    public Set<BlogPostResponse> getBlogPostsAboutTravelStopByName(String country, String city, String street) {


        List<TravelStop> travelStop = travelStopService.getAllTravelStopByLocation(country, city, street);
        List<BlogPost> blogPostList = new ArrayList<>();

        travelStop.forEach(e -> {
            blogPostList.addAll(e.getBlogPosts());
        });

        return new HashSet<>(ObjectMapperUtils.mapAll(new HashSet<>(blogPostList), BlogPostResponse.class));
    }

    public Set<BlogPostResponse> getAllBlogPostAboutTravelRouteByTravelStopLocationName(String country, String city, String street) {

        List<TravelStop> travelStop = travelStopService.getAllTravelStopByLocation(country, city, street);
        List<BlogPost> blogPostList = new ArrayList<>();

        travelStop.forEach(e -> {
            e.getTravelRoutes().forEach(el -> blogPostList.addAll(el.getBlogPosts()));
        });

        return new HashSet<>(ObjectMapperUtils.mapAll(new HashSet<>(blogPostList), BlogPostResponse.class));
    }

    // TODO: 01.12.2021 Nie dziala stronnicowanie
    public Page<BlogPostResponse> getAllBlogPostByTitle(int offset, int pageSize, String title) {
        List<BlogPost> blogPostList = new ArrayList<>(new HashSet<>(blogPostRepository.findAllByTitleContainingIgnoreCase(title)));
        return new PageImpl<BlogPost>(blogPostList, PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.DESC, "numberOfVisits")), blogPostList.size())
                .map(new Function<BlogPost, BlogPostResponse>() {
                    @Override
                    public BlogPostResponse apply(BlogPost blogPost) {
                        BlogPostResponse bpSR = mapper.map(blogPost, BlogPostResponse.class);
                        bpSR.setRateAverage(blogPost.rateAverage());
                        return bpSR;
                    }
                });
    }

    public Set<BlogPostResponse> getAllBlogPostByTag(List<String> tags) {

        List<Tag> tagList = new ArrayList<>();
        tags.forEach(e -> {
            Tag tag = tagService.findTagByName(e);
            if (tag != null)
                tagList.add(tag);
        });

        List<BlogPost> blogPostList = findAllBlogPost().stream()
                .filter(e -> e.getTags().containsAll(tagList))
                .collect(Collectors.toList());
        return new HashSet<>(ObjectMapperUtils.mapAll(new HashSet<>(blogPostList), BlogPostResponse.class));
    }


    public List<BlogPostShortcutResponse> getTopRatedBlogPost(long page, long howManyRecord) {

        if (howManyRecord < 0) {
            howManyRecord = 0;
        }

        List<BlogPost> returnPageList = blogPostRepository.
                findAllBlogPostOrderByMaxAverageCommentRating(howManyRecord, (howManyRecord * page));

        return ObjectMapperUtils.mapAll(returnPageList, BlogPostShortcutResponse.class);
    }

    public Page<BlogPostShortcutResponse> getBlogPostByPageAndSort(int offset, int pageSize, String column) {

        Page<BlogPost> returnPageList = blogPostRepository.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, column)));
        return returnPageList.map(new Function<BlogPost, BlogPostShortcutResponse>() {
            @Override
            public BlogPostShortcutResponse apply(BlogPost blogPost) {
                BlogPostShortcutResponse bpSR = mapper.map(blogPost, BlogPostShortcutResponse.class);
                bpSR.setRateAverage(blogPost.rateAverage());
                return bpSR;
            }
        });
    }

    private BlogPostResponse map(BlogPost blogPost) {

        BlogPostResponse bpR = mapper.map(blogPost, BlogPostResponse.class);
        bpR.setRateAverage(blogPost.rateAverage());
        return bpR;
    }

    public BlogPost findBlogPostById(Long blogPostId, String loggerMsg) {
        return blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> throwExcWithLogger(loggerMsg));
    }

    public BlogPostResponse getBlogPostById(Long blogPostId) {
        BlogPost bP = findBlogPostById(blogPostId, LOGGER_GET_BLOGPOST_FAILED);
        bP.setNumberOfVisits(bP.getNumberOfVisits() + 1);
        return map(bP);
    }


    public List<BlogPost> findAllBlogPost() {
        return blogPostRepository.findAll();
    }

    private List<BlogPostResponse> mapAll(List<BlogPost> blogPostList) {
        return ObjectMapperUtils.mapAll(blogPostList, BlogPostResponse.class);
    }

    private NotFoundException throwExcWithLogger(String loggerMsg){
        logger.warn(loggerMsg+NOT_FOUND_BLOG_POST);
        return new NotFoundException(NOT_FOUND_BLOG_POST);
    }
}