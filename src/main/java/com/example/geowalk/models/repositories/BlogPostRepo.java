package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.BlogPost;
import com.example.geowalk.models.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    Page<BlogPost> findBlogPostsByVisibleTrueAndNeedToVerifyFalse(Pageable pageable);
    List<BlogPost> findBlogPostsByVisibleTrueAndNeedToVerifyFalse();
    Optional<BlogPost> findByIdAndVisibleTrueAndNeedToVerifyFalse(long id);
    Page<BlogPost> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

    String query = "SELECT DISTINCT bp.ID, bp.VISIBLE, bp.CONTENT, bp.CREATION_DATE_TIME, " +
            "bp.LAST_EDIT_DATE_TIME, bp.NEED_TO_VERIFY, bp.NUMBER_OF_VISITS, bp.SHORT_DESCRIPTION, " +
            "bp.TITLE, bp.USER_ID  FROM BLOG_POST  bp " +
            "JOIN BLOG_POSTS_TAGS bpt ON bpt.BLOG_POST_ID = bp.id " +
            "JOIN TAG t ON t.ID = bpt.TAG_ID " +
            "WHERE UPPER(bp.CONTENT) LIKE UPPER(CONCAT('%', :searchWord, '%')) OR " +
            "UPPER(bp.TITLE) LIKE UPPER(CONCAT('%', :searchWord, '%')) OR " +
            "UPPER(t.NAME) LIKE UPPER(CONCAT('%', :searchWord, '%'))";
    @Query(value = query, nativeQuery = true)
    Page<BlogPost> findAllBlogPostsBySearchWord(@Param("searchWord") String searchWord, Pageable pageable);
}
