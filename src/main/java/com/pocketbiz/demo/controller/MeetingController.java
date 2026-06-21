package com.pocketbiz.demo.controller;

import com.pocketbiz.demo.entity.Meeting;
import com.pocketbiz.demo.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meetings")
@CrossOrigin(origins = "http://localhost:5173")
public class MeetingController {

    @Autowired
    private MeetingRepository meetingRepository;

    @GetMapping
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAllByOrderByScheduledTimeDesc();
    }

    @PostMapping("/create")
    public Meeting createMeeting(@RequestBody Meeting meeting) {
        // Generate a unique secure room code
        String uniqueRoom = "PocketBiz_" + UUID.randomUUID().toString().substring(0, 8);
        meeting.setRoomCode(uniqueRoom);
        meeting.setStatus("SCHEDULED");
        // Ensure time is set if null
        if(meeting.getScheduledTime() == null) meeting.setScheduledTime(LocalDateTime.now());

        return meetingRepository.save(meeting);
    }
}