package com.example.conferenceroom.service;

import com.example.conferenceroom.model.ConferenceRoom;
import com.example.conferenceroom.model.Reservation;
import com.example.conferenceroom.repository.ConferenceRoomRepository;
import com.example.conferenceroom.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public interface ConferenceRoomService {
     List<ConferenceRoom> getConferenceRooms();

     List<ConferenceRoom> searchAvailableConferenceRooms(LocalDateTime startDate, LocalDateTime endDate, int capacity);

     boolean reserveConferenceRoom(String conferenceRoomName, LocalDateTime startDate, LocalDateTime endDate);

     ConferenceRoom addConferenceRoom(ConferenceRoom conferenceRoom) ;

}


