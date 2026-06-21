package com.pocketbiz.demo.repository;

import com.pocketbiz.demo.entity.CommunityMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CommunityMessageRepository extends JpaRepository<CommunityMessage, Long> {

    // Fetch chat history between two users (A->B OR B->A)
    @Query("SELECT m FROM CommunityMessage m WHERE " +
            "(m.senderId = :user1 AND m.receiverId = :user2) OR " +
            "(m.senderId = :user2 AND m.receiverId = :user1) " +
            "ORDER BY m.timestamp ASC")
    List<CommunityMessage> findChatHistory(String user1, String user2);
}