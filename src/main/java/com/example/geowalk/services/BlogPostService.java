package com.example.geowalk.services;

import com.example.geowalk.exceptions.base.BadRequestException;
import com.example.geowalk.exceptions.base.NotFoundException;
import com.example.geowalk.models.dto.ObjectMapperUtils;
import com.example.geowalk.models.dto.requests.BlogPostRequest;
import com.example.geowalk.models.dto.responses.BlogPostResponse;
import com.example.geowalk.models.entities.BlogPost;
import com.example.geowalk.models.entities.TravelStop;
import com.example.geowalk.models.entities.User;
import com.example.geowalk.models.repositories.BlogPostRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class BlogPostService {

    private final BlogPostRepo blogPostRepository;
    private final UserService userService;
    private final TravelStopService travelStopService;
    private final TravelRouteService travelRouteService;

    private final String NOT_FOUND_BLOG_POST = "Blog post with given id not found";
    private final String BAD_REQUEST_BLOG_POST = "Blog post request has bad body";

    public BlogPostService(BlogPostRepo blogPostRepository, UserService userService, TravelStopService travelStopService, TravelRouteService travelRouteService) {
        this.blogPostRepository = blogPostRepository;
        this.userService = userService;
        this.travelStopService = travelStopService;
        this.travelRouteService = travelRouteService;
    }

    public List<BlogPost> getAllBlogPost(){
        return blogPostRepository.findAll();
    }

    public BlogPost getBlogPostById(Long BlogPostId) {
        return blogPostRepository.findById(BlogPostId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BLOG_POST));
    }

    public void createBlogPost(BlogPostRequest blogPostRequest){

        User user = userService.getUser(blogPostRequest.getUserId());

        BlogPost blogPost = new BlogPost(blogPostRequest.getContent());
        blogPost.setUser(user);

        if((blogPostRequest.getTravelRouteRequest() == null && blogPostRequest.getTravelStopRequest() == null) ||
                (blogPostRequest.getTravelRouteRequest() != null && blogPostRequest.getTravelStopRequest() != null)){
            throw new BadRequestException(BAD_REQUEST_BLOG_POST);
        }

        if(blogPostRequest.getTravelRouteRequest() != null){
            blogPostRequest.getTravelRouteRequest().forEach(e-> {
                if(e.getTravelStopList().size() < 2){
                    throw new BadRequestException(BAD_REQUEST_BLOG_POST);
                }
                blogPost.getTravelRoutes().add(travelRouteService.createTravelRoute(e));
            });
        }
        else if(blogPostRequest.getTravelStopRequest() != null){
            blogPostRequest.getTravelStopRequest().forEach(e-> {
                blogPost.getTravelStops().add(travelStopService.createTravelStop(e));
            });
        }
        blogPostRepository.save(blogPost);
    }

    public Set<BlogPostResponse> findBlogPostAboutTravelStopByName(String locationName){

//        List<TravelStop> travelStopList = travelStopService.getAllTravelStop()
//                .stream().filter(e -> e.getName().equals(locationName)).collect(Collect

        TravelStop travelStop = travelStopService.getTravelStopByName(locationName);
        return new HashSet<>(ObjectMapperUtils.mapAll(new HashSet<>(travelStop.getBlogPosts()), BlogPostResponse.class));
    }

    public Set<BlogPostResponse> findAllBlogPostAboutTravelRouteByTravelStopLocationName(String locationName){

//        List<TravelStop> travelStopList = travelStopService.getAllTravelStop()
//                .stream().filter(e -> e.getName().equals(locationName)).collect(Collect

        TravelStop travelStop = travelStopService.getTravelStopByName(locationName);

        List<BlogPost> blogPostList = new ArrayList<>();
        travelStop.getTravelRoutes().forEach(e -> blogPostList.addAll(e.getBlogPosts()));
        blogPostList.addAll(travelStop.getBlogPosts());

        return new HashSet<>(ObjectMapperUtils.mapAll(new HashSet<>(blogPostList), BlogPostResponse.class));
    }
}