package com.pocketbiz.demo.repository;

import com.pocketbiz.demo.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    // Fetch posts, newest first
    List<CommunityPost> findAllByOrderByCreatedAtDesc();
}