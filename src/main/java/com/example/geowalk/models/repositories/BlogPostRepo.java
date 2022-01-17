package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.BlogPost;
import com.example.geowalk.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogPostRepo extends JpaRepository<BlogPost, Long> {

    Page<BlogPost> findBlogPostsByVisibleTrueAndNeedToVerifyFalse(Pageable pageable);
    List<BlogPost> findBlogPostsByVisibleTrueAndNeedToVerifyFalse();
    Optional<BlogPost> findByIdAndVisibleTrue(long id);
    Optional<BlogPost> findByIdAndVisibleTrueAndNeedToVerifyFalse(long id);
    BlogPost findFirstByVisibleTrueOrderByCreationDateTimeDesc();
    Integer countBlogPostsByVisibleTrue();
    List<BlogPost> findBlogPostsByVisibleTrueAndNeedToVerifyTrueOrderByCreationDateTime();
    Optional<BlogPost> findByIdAndVisibleTrueAndNeedToVerifyTrue(Long blogPostId);
    Page<BlogPost> findBlogPostsByVisibleTrueAndNeedToVerifyFalseAndUserOrderByCreationDateTimeDesc(User user, Pageable pageable);

    String findAllBlogPostsBySearchWordQuery = "SELECT DISTINCT bp.ID, bp.VISIBLE, bp.CONTENT, bp.CREATION_DATE_TIME, " +
            "bp.LAST_EDIT_DATE_TIME, bp.NEED_TO_VERIFY, bp.NUMBER_OF_VISITS, bp.SHORT_DESCRIPTION, " +
            "bp.TITLE, bp.USER_ID  FROM BLOG_POST  bp " +
            "JOIN BLOG_POSTS_TAGS bpt ON bpt.BLOG_POST_ID = bp.id " +
            "JOIN TAG t ON t.ID = bpt.TAG_ID " +
            "WHERE (bp.VISIBLE AND NOT bp.NEED_TO_VERIFY) AND" +
            "(UPPER(bp.CONTENT) LIKE UPPER(CONCAT('%', :searchWord, '%')) OR " +
            "UPPER(bp.TITLE) LIKE UPPER(CONCAT('%', :searchWord, '%')) OR " +
            "UPPER(t.NAME) LIKE UPPER(CONCAT('%', :searchWord, '%')))";
    @Query(value = findAllBlogPostsBySearchWordQuery, nativeQuery = true)
    Page<BlogPost> findAllBlogPostsBySearchWord(@Param("searchWord") String searchWord, Pageable pageable);

    String findAllBlogPostByTagQuery = "SELECT bp.id, bp.visible, bp.content, bp.creation_date_time, bp.last_edit_date_time, " +
            "bp.need_to_verify, bp.number_of_visits, bp.short_description, bp.title, bp.user_id FROM BLOG_POST bp " +
            "JOIN BLOG_POSTS_TAGS bpt ON bp.ID = bpt.BLOG_POST_ID " +
            "JOIN TAG t ON bpt.TAG_ID = t.id " +
            "WHERE TAG_ID = :tagId " +
            "ORDER BY CREATION_DATE_TIME DESC";
    @Query(value = findAllBlogPostByTagQuery, nativeQuery = true)
    Page<BlogPost> findAllBlogPostByTag(@Param("tagId") Long tagId, Pageable pageable);
}
