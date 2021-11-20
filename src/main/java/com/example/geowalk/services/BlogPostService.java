package com.example.geowalk.services;

import com.example.geowalk.exceptions.base.BadRequestException;
import com.example.geowalk.exceptions.base.NotFoundException;
import com.example.geowalk.models.dto.requests.BlogPostRequest;
import com.example.geowalk.models.entities.BlogPost;
import com.example.geowalk.models.entities.User;
import com.example.geowalk.models.repositories.BlogPostRepo;
import com.example.geowalk.models.repositories.UserRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class BlogPostService {

    private final BlogPostRepo blogPostRepository;
    private final UserService userService;

    private final String NOT_FOUND_BLOG_POST = "Blog post with given id not found";
    private final String BAD_REQUEST_BLOG_POST = "Blog post request has bad body";

    public BlogPostService(BlogPostRepo blogPostRepository, UserRepo userRepository, UserService userService) {
        this.blogPostRepository = blogPostRepository;
        this.userService = userService;
    }

    public List<BlogPost> getAllBlogPost(){
        return blogPostRepository.findAll();
    }

    public BlogPost getBlogPostById(Long BlogPostId) {
        return blogPostRepository.findById(BlogPostId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BLOG_POST));
    }

    // TODO:
    public void createBlogPost(BlogPostRequest blogPostRequest){

        User user = userService.getUser(blogPostRequest.getUserId());

        BlogPost blogPost = new BlogPost(blogPostRequest.getContent());
        blogPost.setUser(user);

        if((blogPostRequest.getTravelRouteRequest() == null && blogPostRequest.getTravelStopRequest() == null) ||
                (blogPostRequest.getTravelRouteRequest() != null && blogPostRequest.getTravelStopRequest() != null)){
            throw new BadRequestException(BAD_REQUEST_BLOG_POST);
        }

        if(blogPostRequest.getTravelRouteRequest() != null){
            if(blogPostRequest.getTravelRouteRequest().getTravelStopList().size() < 2){
                throw new BadRequestException(BAD_REQUEST_BLOG_POST);
            }
            // TODO: 07.11.2021 Dodanie TravelRoute.
        }
        else {
            // TODO: 07.11.2021 Dodanie Travel Stopa
        }
        blogPostRepository.save(blogPost);
    }
}