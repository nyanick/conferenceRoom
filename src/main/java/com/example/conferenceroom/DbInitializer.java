package com.example.conferenceroom;

import com.example.conferenceroom.model.ConferenceRoom;
import com.example.conferenceroom.model.Reservation;
import com.example.conferenceroom.service.ConferenceRoomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("!test")
public class DbInitializer {

    @Bean
    @Profile("!test")
    CommandLineRunner init (ConferenceRoomService conferenceRoomService){
        return args -> {
			/*
			we would create some conference room and reservations here if DB is empty
			 */
            if(conferenceRoomService.getConferenceRooms().isEmpty()){
                ConferenceRoom room1 = new ConferenceRoom("Room 1", 40);
                ConferenceRoom room2 = new ConferenceRoom("Room 2", 100);
                //we would reserve room1, for the month of may 1st - 31st May 2023
                LocalDateTime startDate = LocalDateTime.of(2023, 5, 1, 0, 0);
                LocalDateTime endDate = LocalDateTime.of(2023, 5, 31, 23, 59);
                Reservation reservation = new Reservation(room1, startDate,endDate);
                List<Reservation> reservations = new ArrayList<>();
                reservations.add(reservation);
                room1.setReservations(reservations);
                conferenceRoomService.addConferenceRoom(room1);
                conferenceRoomService.addConferenceRoom(room2);
            }
        };
    }
}
