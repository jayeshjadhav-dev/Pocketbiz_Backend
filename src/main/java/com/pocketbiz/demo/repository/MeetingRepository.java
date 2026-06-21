package com.pocketbiz.demo.repository;

import com.pocketbiz.demo.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    // Fetch all meetings (You can filter by host later if needed)
    List<Meeting> findAllByOrderByScheduledTimeDesc();
}