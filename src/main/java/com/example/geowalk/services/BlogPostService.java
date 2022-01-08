package com.example.geowalk.services;

import com.example.geowalk.exceptions.BadRequestException;
import com.example.geowalk.exceptions.NotFoundException;
import com.example.geowalk.exceptions.UnauthorizedException;
import com.example.geowalk.models.dto.ObjectMapperUtils;
import com.example.geowalk.models.dto.requests.BlogPostReqDto;
import com.example.geowalk.models.dto.responses.BlogPostResDto;
import com.example.geowalk.models.dto.responses.BlogPostShortResDto;
import com.example.geowalk.models.entities.*;
import com.example.geowalk.models.repositories.*;
import com.example.geowalk.utils.ISessionUtil;
import com.example.geowalk.utils.SwearWordsFilter;
import com.example.geowalk.utils.messages.MessagesUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.geowalk.utils.messages.MessageKeys.*;

@Transactional
@Service
public class BlogPostService {

    private static final Logger logger = LoggerFactory.getLogger(BlogPostService.class);

    private final ModelMapper mapper;
    private final SwearWordsFilter swearWordsFilter;
    private final MessagesUtil dict;
    private final ISessionUtil sessionUtil;

    private final UserService userService;
    private final TravelStopService travelStopService;
    private final TravelRouteService travelRouteService;
    private final TagService tagService;

    private final BlogPostRepo blogPostRepo;
    private final TravelRouteRepo travelRouteRepo;
    private final TravelStopRepo travelStopRepo;
    private final UserRepo userRepo;

    public BlogPostService(ModelMapper mapper,
                           MessagesUtil dict,
                           ISessionUtil sessionUtil,
                           BlogPostRepo blogPostRepository,
                           TravelRouteRepo travelRouteRepository,
                           TravelStopRepo travelStopRepository,
                           UserService userService,
                           TravelStopService travelStopService,
                           TravelRouteService travelRouteService,
                           TagService tagService,
                           SwearWordsFilter swearWordsFilter,
                           UserRepo userRepo) {
        this.mapper = mapper;
        this.dict = dict;
        this.sessionUtil = sessionUtil;
        this.blogPostRepo = blogPostRepository;
        this.travelRouteRepo = travelRouteRepository;
        this.travelStopRepo = travelStopRepository;
        this.userService = userService;
        this.travelStopService = travelStopService;
        this.travelRouteService = travelRouteService;
        this.tagService = tagService;
        this.swearWordsFilter = swearWordsFilter;
        this.userRepo = userRepo;
    }

    public Page<BlogPostShortResDto> getBlogPostsByPageAndSort(int offset, int pageSize, String column) {

        Page<BlogPost> returnPageList = blogPostRepo.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, column)));
        return returnPageList.map(new Function<BlogPost, BlogPostShortResDto>() {
            @Override
            public BlogPostShortResDto apply(BlogPost blogPost) {
                BlogPostShortResDto bpSR = mapper.map(blogPost, BlogPostShortResDto.class);
                bpSR.setRateAverage(blogPost.rateAverage());
                return bpSR;
            }
        });
    }

    public Page<BlogPostShortResDto> getTopRatedBlogPosts(int page, int howManyRecord) {
        List<BlogPost> listBP = findAllBlogPost().stream().sorted(Comparator.comparingDouble(BlogPost::rateAverage).reversed()).collect(Collectors.toList());
        return returnMappedPageBlogPostShortcutResponse(listBP, page, howManyRecord);
    }

    public Page<BlogPostShortResDto> getBlogPostsAboutTravelStopByName(String country, String city, String street, int page, int howManyRecord) {


        List<TravelStop> travelStop = travelStopService.getAllTravelStopByLocation(country, city, street);

        List<BlogPost> listBlogPost = new ArrayList<>();
        travelStop.forEach(e -> {
            listBlogPost.addAll(e.getBlogPosts());
        });

        return returnMappedPageBlogPostShortcutResponse(listBlogPost, page, howManyRecord);
    }

    public Page<BlogPostShortResDto> getAllBlogPostAboutTravelRouteByTravelStopLocationName(String country, String city, String street, int page, int howManyRecord) {

        List<TravelStop> travelStop = travelStopService.getAllTravelStopByLocation(country, city, street);

        List<BlogPost> listBlogPost = new ArrayList<>();
        travelStop.forEach(e -> {
            e.getTravelRoutes().forEach(el -> listBlogPost.addAll(el.getBlogPosts()));
        });

        return returnMappedPageBlogPostShortcutResponse(listBlogPost, page, howManyRecord);
    }

    public Page<BlogPostShortResDto> getAllBlogPostByTitleAndTags(int page, int howManyRecord, List<String> tagListParam, String titleParam) {

        if (titleParam != null && tagListParam != null) {
            List<Tag> tagList = new ArrayList<>();
            tagListParam.forEach(e -> {
                Tag tag = tagService.findTagByName(e);
                if (tag != null)
                    tagList.add(tag);
            });
            List<BlogPost> blogPostList = findAllBlogPost().stream()
                    .filter(e -> e.getTitle().toLowerCase().contains(titleParam.toLowerCase()))
                    .filter(e -> e.getTags().containsAll(tagList))
                    .collect(Collectors.toList());
            return returnMappedPageBlogPostShortcutResponse(blogPostList, page, howManyRecord);

        } else if (tagListParam != null) {
            List<Tag> tagList = new ArrayList<>();
            tagListParam.forEach(e -> {
                Tag tag = tagService.findTagByName(e);
                if (tag != null)
                    tagList.add(tag);
            });
            List<BlogPost> blogPostList = findAllBlogPost().stream()
                    .filter(e -> e.getTags().containsAll(tagList))
                    .collect(Collectors.toList());
            return returnMappedPageBlogPostShortcutResponse(blogPostList, page, howManyRecord);

        } else if (titleParam != null) {
            Page<BlogPostShortResDto> blogPostList = blogPostRepo.findAllByTitleContainingIgnoreCase(titleParam, PageRequest.of(page, howManyRecord))
                    .map(new Function<BlogPost, BlogPostShortResDto>() {
                        @Override
                        public BlogPostShortResDto apply(BlogPost blogPost) {
                            BlogPostShortResDto bpSR = mapper.map(blogPost, BlogPostShortResDto.class);
                            bpSR.setRateAverage(blogPost.rateAverage());
                            return bpSR;
                        }
                    });
        }
        return new PageImpl<BlogPostShortResDto>(Collections.emptyList(), PageRequest.of(page, howManyRecord), 0);
    }

    public BlogPostResDto getBlogPostById(Long blogPostId) {
        BlogPost bP = findBlogPostById(blogPostId, dict.getDict().get(LOGGER_GET_POST_FAILED));
        bP.setNumberOfVisits(bP.getNumberOfVisits() + 1);
        return map(bP);
    }

    public void createBlogPost(BlogPostReqDto request) {
        Optional<String> loggedUserUsername = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if (loggedUserUsername.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_POST_FAILED), dict.getDict().get(USER_NOT_AUTHORIZED));
            throw new UnauthorizedException(dict.getDict().get(USER_NOT_AUTHORIZED));
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUsername.get());
        if (loggedUser.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_COMMENT_FAILED), dict.getDict().get(USER_BLOCKED_OR_DELETED));
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        BlogPost blogPost = new BlogPost(
                request.getContent(),
                request.getTitle()
        );
        blogPost.setUser(loggedUser.get());

        if (request.getTravelRouteRequestList() != null && request.getTravelStopRequestList() != null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_POST_FAILED), dict.getDict().get(BLOG_POST_BAD_REQUEST));
            throw new BadRequestException(dict.getDict().get(BLOG_POST_BAD_REQUEST));
        }

        if (request.getTravelRouteRequestList() != null) {
            List<TravelRoute> travelRouteList = new ArrayList<>();
            request.getTravelRouteRequestList().forEach(e -> {
                if (e.getTravelStopList().size() < 2) {
                    logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_POST_FAILED), dict.getDict().get(BLOG_POST_BAD_REQUEST));
                    throw new BadRequestException(dict.getDict().get(BLOG_POST_BAD_REQUEST));
                }
                travelRouteList.add(travelRouteService.createTravelRoute(e));
            });
            travelRouteRepo.saveAll(travelRouteList);
            blogPost.getTravelRoutes().addAll(travelRouteList);
        } else if (request.getTravelStopRequestList() != null) {
            List<TravelStop> travelStopList = new ArrayList<>(travelStopService.getOrCreateTravelStopsByLocation(request.getTravelStopRequestList()));
            blogPost.getTravelStops().addAll(travelStopList);
        }

        if (request.getTagList() != null) {
            List<Tag> tagList = new ArrayList<>();
            List<Tag> newTagList = new ArrayList<>();
            request.getTagList().forEach(e -> {
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

        if (swearWordsFilter.hasSwearWord(request.getContent())) {
            blogPost.setNeedToVerify(true);
        }
        blogPostRepo.save(blogPost);
        logger.info(blogPost.isNeedToVerify() ? dict.getDict().get(SWEAR_WORDS_FILTER_MESSAGE_BLOG_POST) : "Creating post success");
    }
    
    private BlogPostResDto map(BlogPost blogPost) {

        BlogPostResDto bpR = mapper.map(blogPost, BlogPostResDto.class);
        bpR.setRateAverage(blogPost.rateAverage());
        return bpR;
    }

    public BlogPost findBlogPostById(Long blogPostId, String loggerMsg) {
        return blogPostRepo.findById(blogPostId)
                .orElseThrow(() -> throwExcWithLogger(loggerMsg));
    }


    public List<BlogPost> findAllBlogPost() {
        return blogPostRepo.findAll();
    }

    private List<BlogPostResDto> mapAll(List<BlogPost> blogPostList) {
        return ObjectMapperUtils.mapAll(blogPostList, BlogPostResDto.class);
    }

    private NotFoundException throwExcWithLogger(String loggerMsg) {
        logger.error("{}{}", loggerMsg, dict.getDict().get(BLOG_POST_NOT_FOUND));
        return new NotFoundException(dict.getDict().get(BLOG_POST_NOT_FOUND));
    }

    private Page<BlogPostShortResDto> returnMappedPageBlogPostShortcutResponse(List<BlogPost> listBlogPost, int page, int howManyRecord) {
        var distinctListBlogPost = new ArrayList<>(new HashSet<>(listBlogPost));

        if (page < 0) {
            page = 0;
        }
        if (howManyRecord < 0) {
            howManyRecord = 0;
        }

        int fromIndex = page * howManyRecord + page;
        int toIndex;

        Page<BlogPost> blogPostList;
        try {
            toIndex = Math.min(fromIndex + howManyRecord, distinctListBlogPost.size() - 1);
            blogPostList = new PageImpl<BlogPost>(distinctListBlogPost.subList(fromIndex, toIndex), PageRequest.of(page, howManyRecord), distinctListBlogPost.size());
        } catch (IllegalArgumentException e) {
            toIndex = Math.min(5, distinctListBlogPost.size());
            blogPostList = new PageImpl<BlogPost>(distinctListBlogPost.subList(0, toIndex), PageRequest.of(0, 5), distinctListBlogPost.size());
        }


        return blogPostList.map(new Function<BlogPost, BlogPostShortResDto>() {
            @Override
            public BlogPostShortResDto apply(BlogPost blogPost) {
                BlogPostShortResDto bpSR = mapper.map(blogPost, BlogPostShortResDto.class);
                bpSR.setRateAverage(blogPost.rateAverage());
                return bpSR;
            }
        });
    }
}