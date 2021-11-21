package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepo extends JpaRepository<BlogPost, Long> {
}
