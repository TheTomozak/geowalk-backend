package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogPostRepo extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findAllByTitleContainingIgnoreCase(String title);

    @Query(value = "Select * from BLOG_POST where ID IN (SELECT post.id FROM BLOG_POST post " +
            "LEFT JOIN BLOG_COMMENT comment ON post.ID = comment.BLOG_POST_ID " +
            "GROUP BY post.id " +
            "ORDER BY AVG(comment.rating) DESC ) LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<BlogPost> findAllBlogPostOrderByMaxAverageCommentRating(@Param("limit") Long limit, @Param("offset") Long offset);

    Optional<BlogPost> findByIdAndVisibleTrue(long id);
}
