package com.example.conferenceroom.repository;

import com.example.conferenceroom.model.ConferenceRoom;
import com.example.conferenceroom.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByConferenceRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqual(ConferenceRoom conferenceRoom, LocalDateTime endDate, LocalDateTime startDate);

    List<Reservation> findByConferenceRoom(ConferenceRoom room);
}

