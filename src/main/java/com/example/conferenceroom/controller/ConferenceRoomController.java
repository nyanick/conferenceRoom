package com.example.conferenceroom.controller;

import com.example.conferenceroom.model.ConferenceRoom;
import com.example.conferenceroom.payload.SearchCriteria;
import com.example.conferenceroom.service.ConferenceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conference-rooms")
@CrossOrigin(origins = "*")
public class ConferenceRoomController {
    @Autowired
    private ConferenceRoomService conferenceRoomService;

    @GetMapping
    public ResponseEntity<List<ConferenceRoom>> getConferenceRooms() {
        List<ConferenceRoom> conferenceRooms = conferenceRoomService.getConferenceRooms();
        return new ResponseEntity<>(conferenceRooms, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ConferenceRoom>> searchConferenceRooms(@RequestBody SearchCriteria searchCriteria) {
        List<ConferenceRoom> availableConferenceRooms = conferenceRoomService.searchAvailableConferenceRooms(searchCriteria.getStartDate(), searchCriteria.getEndDate(), searchCriteria.getCapacity());
        return new ResponseEntity<>(availableConferenceRooms, HttpStatus.OK);
    }
}

