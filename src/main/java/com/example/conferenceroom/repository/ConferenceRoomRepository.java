package com.example.conferenceroom.repository;

import com.example.conferenceroom.model.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {
    List<ConferenceRoom> findByCapacityOrderByCapacity(int capacity);
    ConferenceRoom findByName(String conferenceRoomName);
    boolean existsByName(String name);

}
