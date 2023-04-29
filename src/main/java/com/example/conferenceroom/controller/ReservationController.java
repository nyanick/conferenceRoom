package com.example.conferenceroom.controller;

import com.example.conferenceroom.payload.ReservationRequest;
import com.example.conferenceroom.service.ConferenceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {
    @Autowired
    private ConferenceRoomService conferenceRoomService;

    @PostMapping
    public ResponseEntity<Void> reserveConferenceRoom(@RequestBody ReservationRequest reservationRequest) {
        conferenceRoomService.reserveConferenceRoom(reservationRequest.getConferenceRoomName(), reservationRequest.getStartDate(), reservationRequest.getEndDate());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

