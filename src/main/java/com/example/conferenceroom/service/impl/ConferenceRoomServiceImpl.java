package com.example.conferenceroom.service.impl;

import com.example.conferenceroom.model.ConferenceRoom;
import com.example.conferenceroom.model.Reservation;
import com.example.conferenceroom.repository.ConferenceRoomRepository;
import com.example.conferenceroom.repository.ReservationRepository;
import com.example.conferenceroom.service.ConferenceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConferenceRoomServiceImpl implements ConferenceRoomService {
    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<ConferenceRoom> getConferenceRooms() {
        return conferenceRoomRepository.findAll();
    }

    @Override
    public List<ConferenceRoom> searchAvailableConferenceRooms(LocalDateTime startDate, LocalDateTime endDate, int capacity) {
        List<ConferenceRoom> conferenceRooms = conferenceRoomRepository.findByCapacityOrderByCapacity(capacity);
        List<ConferenceRoom> availableConferenceRooms = new ArrayList<>();
        for (ConferenceRoom conferenceRoom : conferenceRooms) {
            List<Reservation> reservations = reservationRepository.findByConferenceRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqual(conferenceRoom, startDate, endDate);
            if (reservations.isEmpty()) {
                availableConferenceRooms.add(conferenceRoom);
            }
        }
        return availableConferenceRooms;
    }

    @Override
    public boolean reserveConferenceRoom(String conferenceRoomName, LocalDateTime startDate, LocalDateTime endDate) {
        ConferenceRoom conferenceRoom = conferenceRoomRepository.findByName(conferenceRoomName);
        Reservation reservation = new Reservation(conferenceRoom, startDate, endDate);
        reservationRepository.save(reservation);
        return true;
    }

    @Override
    public ConferenceRoom addConferenceRoom(ConferenceRoom conferenceRoom) {
        // Check if the conference room already exists
        if (conferenceRoomRepository.existsByName(conferenceRoom.getName())) {
            throw new IllegalArgumentException("Conference room with the same name already exists");
        }
        // Save the new conference room to the database
        return conferenceRoomRepository.save(conferenceRoom);
    }

}


