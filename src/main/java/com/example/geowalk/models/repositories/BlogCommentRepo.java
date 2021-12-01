package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogCommentRepo extends JpaRepository<BlogComment, Long> {

    Optional<BlogComment> findByIdAndVisibleTrue(long id);
}
