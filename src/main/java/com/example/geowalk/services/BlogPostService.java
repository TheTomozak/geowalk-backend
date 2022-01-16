package com.example.geowalk.services;

import com.example.geowalk.exceptions.BadRequestException;
import com.example.geowalk.exceptions.ForbiddenException;
import com.example.geowalk.exceptions.NotFoundException;
import com.example.geowalk.models.dto.requests.BlogPostReqDto;
import com.example.geowalk.models.dto.requests.BlogPostVerificationReqDto;
import com.example.geowalk.models.dto.requests.TravelRouteReqDto;
import com.example.geowalk.models.dto.requests.TravelStopReqDto;
import com.example.geowalk.models.dto.responses.BlogPostResDto;
import com.example.geowalk.models.dto.responses.BlogPostShortResDto;
import com.example.geowalk.models.entities.*;
import com.example.geowalk.models.enums.Role;
import com.example.geowalk.models.repositories.BlogPostRepo;
import com.example.geowalk.models.repositories.TagRepo;
import com.example.geowalk.models.repositories.UserRepo;
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

    private final TravelStopService travelStopService;
    private final TravelRouteService travelRouteService;
    private final TagService tagService;

    private final BlogPostRepo blogPostRepo;
    private final UserRepo userRepo;
    private final TagRepo tagRepo;

    public BlogPostService(ModelMapper mapper,
                           MessagesUtil dict,
                           ISessionUtil sessionUtil,
                           BlogPostRepo blogPostRepository,
                           TravelStopService travelStopService,
                           TravelRouteService travelRouteService,
                           SwearWordsFilter swearWordsFilter,
                           TagService tagService,
                           UserRepo userRepo,
                           TagRepo tagRepo) {
        this.mapper = mapper;
        this.dict = dict;
        this.sessionUtil = sessionUtil;
        this.blogPostRepo = blogPostRepository;
        this.travelStopService = travelStopService;
        this.travelRouteService = travelRouteService;
        this.swearWordsFilter = swearWordsFilter;
        this.tagService = tagService;
        this.userRepo = userRepo;
        this.tagRepo = tagRepo;
    }

    public Page<BlogPostShortResDto> getBlogPosts(int page, int size) {
        if (page < 0 || size <= 0) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_POST_FAILED), dict.getDict().get(BLOG_POST_BAD_REQUEST));
            throw new BadRequestException(dict.getDict().get(BLOG_POST_BAD_REQUEST));
        }
        Page<BlogPost> blogPostPage = blogPostRepo.findBlogPostsByVisibleTrueAndNeedToVerifyFalse(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creationDateTime")));
        return convertToBlogPostShortResDto(blogPostPage);
    }

    public Page<BlogPostShortResDto> getBlogPostsTopRated(int page, int size) {
        if (page < 0 || size <= 0) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_POST_FAILED), dict.getDict().get(BLOG_POST_BAD_REQUEST));
            throw new BadRequestException(dict.getDict().get(BLOG_POST_BAD_REQUEST));
        }
        List<BlogPost> blogPostList = blogPostRepo.findBlogPostsByVisibleTrueAndNeedToVerifyFalse().stream().sorted(Comparator.comparingDouble(BlogPost::getAverageRate).reversed()).collect(Collectors.toList());
        Page<BlogPost> blogPostPage = convertListToPage(blogPostList, page, size);
        return convertToBlogPostShortResDto(blogPostPage);
    }

    public Page<BlogPostShortResDto> getBlogPostsRelatedToTravelStop(String country, String city, String street, int page, int size) {
        if (page < 0 || size <= 0) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_POST_FAILED), dict.getDict().get(BLOG_POST_BAD_REQUEST));
            throw new BadRequestException(dict.getDict().get(BLOG_POST_BAD_REQUEST));
        }

        List<TravelStop> travelStopList = travelStopService.getAllTravelStopByLocation(country, city, street);
        Set<BlogPost> blogPostSet = new LinkedHashSet<>();
        travelStopList.forEach(travelStop -> {
            Set<BlogPost> blogPosts = travelStop.getBlogPosts().stream()
                                                                .filter(blogPost -> !blogPost.isNeedToVerify() && blogPost.isVisible())
                                                                .collect(Collectors.toSet());
            blogPostSet.addAll(blogPosts);
        });

        Page<BlogPost> blogPostPage = convertListToPage(new ArrayList<>(blogPostSet), page, size);

        return convertToBlogPostShortResDto(blogPostPage);
    }

    public Page<BlogPostShortResDto> getBlogPostsRelatedToTravelRouteWithTravelStop(String country, String city, String street, int page, int size) {
        if (page < 0 || size <= 0) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_POST_FAILED), dict.getDict().get(BLOG_POST_BAD_REQUEST));
            throw new BadRequestException(dict.getDict().get(BLOG_POST_BAD_REQUEST));
        }
        List<TravelStop> travelStopList = travelStopService.getAllTravelStopByLocation(country, city, street);

        Set<BlogPost> blogPostSet = new LinkedHashSet<>();
        for (TravelStop travelStop : travelStopList) {
            travelStop.getTravelRoutes().forEach(travelRoute -> {
                Set<BlogPost> blogPosts = travelRoute.getBlogPosts().stream()
                                                                    .filter(blogPost -> !blogPost.isNeedToVerify() && blogPost.isVisible())
                                                                    .collect(Collectors.toSet());
                blogPostSet.addAll(blogPosts);
            });
        }

        Page<BlogPost> blogPostPage = convertListToPage(new ArrayList<>(blogPostSet), page, size);

        return convertToBlogPostShortResDto(blogPostPage);
    }

    public Page<BlogPostShortResDto> getBlogPostsBySearchParam(int page, int size, String searchValue) {
        if (page < 0 || size <= 0) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_POST_FAILED), dict.getDict().get(BLOG_POST_BAD_REQUEST));
            throw new BadRequestException(dict.getDict().get(BLOG_POST_BAD_REQUEST));
        }
        Page<BlogPost> blogPostPage = blogPostRepo.findAllBlogPostsBySearchWord(searchValue, PageRequest.of(page, size));
        return convertToBlogPostShortResDto(blogPostPage);
    }

    public BlogPostShortResDto getLatestBlogPost() {
        BlogPost latestBlogPost = blogPostRepo.findFirstByVisibleTrueOrderByCreationDateTimeDesc();
        return mapper.map(latestBlogPost, BlogPostShortResDto.class);
    }

    public BlogPostResDto getBlogPost(Long blogPostId) {
        Optional<BlogPost> blogPost = blogPostRepo.findByIdAndVisibleTrueAndNeedToVerifyFalse(blogPostId);
        if (blogPost.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_POST_FAILED), dict.getDict().get(BLOG_POST_NOT_FOUND));
            throw new NotFoundException(dict.getDict().get(BLOG_POST_NOT_FOUND));
        }

        blogPost.get().setNumberOfVisits(blogPost.get().getNumberOfVisits() + 1);
        return convertBlogPostToBlogPostResDto(blogPost.get());
    }

    public void createBlogPost(BlogPostReqDto request) {
        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(sessionUtil.getLoggedUserUsername());
        if (loggedUser.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_COMMENT_FAILED), dict.getDict().get(USER_BLOCKED_OR_DELETED));
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        BlogPost blogPost = new BlogPost(
                request.getContent(),
                request.getTitle(),
                request.getShortDescription()
        );
        blogPost.setUser(loggedUser.get());

        if (request.getTravelRoutes() != null && request.getTravelStops() != null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_POST_FAILED), dict.getDict().get(BLOG_POST_BAD_REQUEST));
            throw new BadRequestException(dict.getDict().get(BLOG_POST_BAD_REQUEST));
        }

        if (request.getTravelRoutes() != null) {
            List<TravelRoute> travelRouteList = new ArrayList<>();
            request.getTravelRoutes().forEach(travelRouteReq -> {
                if (travelRouteReq.getTravelStops().size() < 1) {
                    logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_POST_FAILED), dict.getDict().get(BLOG_POST_BAD_REQUEST));
                    throw new BadRequestException(dict.getDict().get(BLOG_POST_BAD_REQUEST));
                }
                travelRouteList.add(travelRouteService.createTravelRoute(travelRouteReq));
            });
            blogPost.setTravelRoutes(travelRouteList);
        } else if (request.getTravelStops() != null) {
            List<TravelStop> travelStopList = travelStopService.getOrCreateTravelStopsByLocation(request.getTravelStops());
            blogPost.setTravelStops(travelStopList);
        }

        if (request.getTagList() != null) {
            List<Tag> existsTags = new ArrayList<>();
            List<Tag> newTags = new ArrayList<>();

            request.getTagList().forEach(tagReq -> {
                Optional<Tag> tag = tagRepo.findByNameIgnoreCase(tagReq);
                if (tag.isPresent())
                    existsTags.add(tag.get());
                else {
                    newTags.add(new Tag(tagReq));
                }
            });
            tagRepo.saveAll(newTags);
            existsTags.addAll(newTags);
            blogPost.setTags(existsTags);
        }

        // TODO: 05.12.2021 Dodawanie zdjęć

        if (swearWordsFilter.hasSwearWord(request.getContent()) || swearWordsFilter.hasSwearWord(request.getTitle()) || swearWordsFilter.hasSwearWord(request.getShortDescription())) {
            blogPost.setNeedToVerify(true);
        }

        blogPostRepo.save(blogPost);
        logger.info(blogPost.isNeedToVerify() ? dict.getDict().get(SWEAR_WORDS_FILTER_MESSAGE_BLOG_POST) : "Creating post success");
    }

    public void updateBlogPost(Long blogPostId, BlogPostReqDto request) {
        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(sessionUtil.getLoggedUserUsername());
        if (loggedUser.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_POST_FAILED), dict.getDict().get(USER_BLOCKED_OR_DELETED));
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        Optional<BlogPost> blogPost = blogPostRepo.findByIdAndVisibleTrueAndNeedToVerifyFalse(blogPostId);
        if (blogPost.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_POST_FAILED), dict.getDict().get(BLOG_POST_NOT_FOUND));
            throw new NotFoundException(dict.getDict().get(BLOG_POST_NOT_FOUND));
        }

        if (!loggedUser.get().equals(blogPost.get().getUser())) {
            if (!(loggedUser.get().getRole().equals(Role.ADMIN) || loggedUser.get().getRole().equals(Role.MODERATOR))) {
                logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_COMMENT_FAILED), dict.getDict().get(BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER));
                throw new ForbiddenException(dict.getDict().get(BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER));
            }
        }

        if (!request.getShortDescription().equals(blogPost.get().getShortDescription())) {
            if (!request.getShortDescription().isBlank()) {
                blogPost.get().setShortDescription(request.getShortDescription());
            }
        }

        if (!request.getContent().equals(blogPost.get().getContent())) {
            if (!request.getContent().isBlank()) {
                blogPost.get().setContent(request.getContent());
            }
        }

        if (!request.getTitle().equals(blogPost.get().getTitle())) {
            if (!request.getTitle().isBlank()) {
                blogPost.get().setTitle(request.getTitle());
            }
        }

        if (request.getTravelRoutes() != null && request.getTravelStops() != null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_POST_FAILED), dict.getDict().get(BLOG_POST_BAD_REQUEST));
            throw new BadRequestException(dict.getDict().get(BLOG_POST_BAD_REQUEST));
        }

        if (request.getTravelRoutes() != null) {
            updateBlogPostTravelRoutes(blogPost.get(), request.getTravelRoutes());
        }

        if (request.getTravelStops() != null) {
            updateBlogPostTravelStops(blogPost.get(), request.getTravelStops());
        }

        updateBlogPostTags(blogPost.get(), request.getTagList());

        if (swearWordsFilter.hasSwearWord(request.getTitle()) || swearWordsFilter.hasSwearWord(request.getContent()) || swearWordsFilter.hasSwearWord(request.getShortDescription())) {
            blogPost.get().setNeedToVerify(true);
        }

        blogPostRepo.save(blogPost.get());
        logger.info(blogPost.get().isNeedToVerify() ? dict.getDict().get(SWEAR_WORDS_FILTER_MESSAGE_BLOG_POST) : "Updating post success");
    }

    public Page<BlogPostShortResDto> getBlogPostsToVerify(int page, int size) {
        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(sessionUtil.getLoggedUserUsername());
        if (loggedUser.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_POST_FAILED), dict.getDict().get(USER_BLOCKED_OR_DELETED));
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        List<BlogPost> blogPosts = new ArrayList<>(blogPostRepo.findBlogPostsByVisibleTrueAndNeedToVerifyTrueOrderByCreationDateTime());
        Page<BlogPost> blogPostPage = convertListToPage(blogPosts, page, size);
        return convertToBlogPostShortResDto(blogPostPage);
    }

    public void verifyBlogPost(BlogPostVerificationReqDto request) {
        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(sessionUtil.getLoggedUserUsername());
        if (loggedUser.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_VERIFY_POST_FAILED), dict.getDict().get(USER_BLOCKED_OR_DELETED));
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        Optional<BlogPost> blogPost = blogPostRepo.findByIdAndVisibleTrueAndNeedToVerifyTrue(request.getBlogPostId());

        if(blogPost.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_VERIFY_POST_FAILED), dict.getDict().get(BLOG_POST_NOT_FOUND));
            throw new NotFoundException(dict.getDict().get(BLOG_POST_NOT_FOUND));
        }

        if(!request.getResult().equals("REJECTED")) {
            if(!request.getResult().equals("ACCEPTED")) {
                logger.error("{}{}", dict.getDict().get(LOGGER_VERIFY_POST_FAILED), dict.getDict().get(INVALID_RESULT_VALUE));
                throw new BadRequestException(dict.getDict().get(INVALID_RESULT_VALUE));
            }
        }

        if(request.getResult().equals("REJECTED")) {
            blogPost.get().setVisible(false);
        }

        blogPost.get().setNeedToVerify(false);
        logger.info("Verification post success");
    }

    private BlogPostResDto convertBlogPostToBlogPostResDto(BlogPost blogPost) {
        BlogPostResDto blogPostResDto = mapper.map(blogPost, BlogPostResDto.class);
        blogPostResDto.setRateAverage(blogPost.getAverageRate());
        return blogPostResDto;
    }

    public Page<BlogPostShortResDto> convertToBlogPostShortResDto(Page<BlogPost> blogPostsPage) {
        return blogPostsPage.map(blogPost -> {
            BlogPostShortResDto blogPostShort = mapper.map(blogPost, BlogPostShortResDto.class);
            blogPostShort.setRateAverage(blogPost.getAverageRate());
            return blogPostShort;
        });
    }

    public Page<BlogPost> convertListToPage(List<BlogPost> blogPostList, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        int total = blogPostList.size();
        int start = Math.toIntExact(pageRequest.getOffset());
        int end = Math.min((start + pageRequest.getPageSize()), total);

        List<BlogPost> output = new ArrayList<>();
        if (start <= end) {
            output = blogPostList.subList(start, end);
        }
        return new PageImpl<>(output, pageRequest, total);
    }

    private void updateBlogPostTravelRoutes(BlogPost blogPost, List<TravelRouteReqDto> travelRoutes) {
        blogPost.getTravelRoutes().clear();
        blogPost.setTravelRoutes(new ArrayList<>());
        for (TravelRouteReqDto request : travelRoutes) {
            TravelRoute travelRoute = travelRouteService.getOrCreateTravelRoute(request);
            blogPost.getTravelRoutes().add(travelRoute);
        }
        blogPostRepo.save(blogPost);
    }

    private void updateBlogPostTravelStops(BlogPost blogPost, List<TravelStopReqDto> travelStops) {
        blogPost.getTravelStops().clear();
        blogPost.setTravelStops(new ArrayList<>());
        for (TravelStopReqDto request : travelStops) {
            TravelStop travelStop = travelStopService.getOrCreateTravelStop(request);
            blogPost.getTravelStops().add(travelStop);
        }
        blogPostRepo.save(blogPost);
    }

    private void updateBlogPostTags(BlogPost blogPost, List<String> tagList) {
        blogPost.getTags().clear();
        blogPost.setTags(new ArrayList<>());
        for (String tagString : tagList) {
            Tag tag = tagService.getOrCreateTag(tagString);
            blogPost.getTags().add(tag);
        }
        blogPostRepo.save(blogPost);
    }
}
