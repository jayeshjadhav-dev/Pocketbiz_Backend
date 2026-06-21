package com.pocketbiz.demo.controller;

import com.pocketbiz.demo.entity.CommunityMessage;
import com.pocketbiz.demo.entity.CommunityPost;
import com.pocketbiz.demo.entity.Vendor;
import com.pocketbiz.demo.enums.SubscriptionTier; // Import Enum
import com.pocketbiz.demo.repository.CommunityMessageRepository;
import com.pocketbiz.demo.repository.CommunityPostRepository;
import com.pocketbiz.demo.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@CrossOrigin(origins = "http://localhost:5173")
public class CommunityController {

    @Autowired private CommunityPostRepository postRepository;
    @Autowired private CommunityMessageRepository messageRepository;
    @Autowired private VendorRepository vendorRepository;

    // --- POSTS ---
    @GetMapping("/posts")
    public List<CommunityPost> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping("/posts")
    public CommunityPost createPost(@RequestBody CommunityPost post) {
        return postRepository.save(post);
    }

    @PutMapping("/posts/{id}/like")
    public CommunityPost likePost(@PathVariable Long id) {
        return postRepository.findById(id).map(post -> {
            post.setLikes(post.getLikes() + 1);
            return postRepository.save(post);
        }).orElse(null);
    }

    // --- MEMBERS (UPDATED TO USE ENUM) ---
    @GetMapping("/members")
    public List<Vendor> getAllMembers() {
        // Correctly using the Enum constant
        return vendorRepository.findByTier(SubscriptionTier.PLATINUM);
    }

    // --- MESSAGING ---
    @GetMapping("/messages/{myId}/{otherId}")
    public List<CommunityMessage> getChatHistory(@PathVariable String myId, @PathVariable String otherId) {
        return messageRepository.findChatHistory(myId, otherId);
    }

    @PostMapping("/messages")
    public CommunityMessage sendMessage(@RequestBody CommunityMessage message) {
        return messageRepository.save(message);
    }
}