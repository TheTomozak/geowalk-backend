package com.example.geowalk.services;

import com.example.geowalk.exceptions.BadRequestBlogPostException;
import com.example.geowalk.exceptions.NotFoundBlogPostException;
import com.example.geowalk.exceptions.NotFoundUserException;
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
    private final UserRepo userRepository;

    public BlogPostService(BlogPostRepo blogPostRepository, UserRepo userRepository, UserService userService) {
        this.blogPostRepository = blogPostRepository;
        this.userRepository = userRepository;
    }

    public List<BlogPost> getAllBlogPost(){
        return blogPostRepository.findAll();
    }

    public BlogPost getBlogPostById(Long BlogPostId) throws NotFoundBlogPostException {
        return blogPostRepository.findById(BlogPostId)
                .orElseThrow(() ->
                        new NotFoundBlogPostException(String.format("Cannot find BlogPost by id: %s", BlogPostId))
                );
    }

    public void createBlogPost(BlogPostRequest blogPostRequest){

        User user = userRepository.findById(blogPostRequest.getUserId())
                .orElseThrow(() -> new NotFoundUserException("Cannot find user with id: " + blogPostRequest.getUserId()));

        BlogPost blogPost = new BlogPost(
                blogPostRequest.getContent()
        );
        blogPost.setUser(user);
        
        if((blogPostRequest.getTravelRouteRequest() == null && blogPostRequest.getTravelStopRequest() == null) ||
           (blogPostRequest.getTravelRouteRequest() != null && blogPostRequest.getTravelStopRequest() != null)){
            throw new BadRequestBlogPostException("Only one Request is needed. TravelRoute or TravelStop");
        }
        // TODO: 07.11.2021 Czy blog post moze miec wiele tras lub przystnkow? Imo nie!
        if(blogPostRequest.getTravelRouteRequest() != null){
            // TODO: 07.11.2021 Dodanie TravelRoute. Pamietaj ze w srodku jest lista min. 2 Travel Stopow 
        }
        else {
            // TODO: 07.11.2021 Dodanie Travel Stopa 
        }
        blogPostRepository.save(blogPost);
    }
}
