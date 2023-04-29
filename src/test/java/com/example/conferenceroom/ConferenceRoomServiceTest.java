package com.example.conferenceroom;

import com.example.conferenceroom.model.ConferenceRoom;
import com.example.conferenceroom.model.Reservation;
import com.example.conferenceroom.repository.ConferenceRoomRepository;
import com.example.conferenceroom.repository.ReservationRepository;
import com.example.conferenceroom.service.ConferenceRoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
public class ConferenceRoomServiceTest {

    @MockBean
    private DbInitializer dbInitializer;

    @Autowired
    private ConferenceRoomService conferenceRoomService;

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testGetConferenceRooms() {
        ConferenceRoom room1 = new ConferenceRoom("Room A", 40);
        ConferenceRoom room2 = new ConferenceRoom("Room B", 100);
        conferenceRoomRepository.save(room1);
        conferenceRoomRepository.save(room2);

        List<ConferenceRoom> conferenceRooms = conferenceRoomService.getConferenceRooms();

        assertThat(conferenceRooms.size()).isEqualTo(2);
        assertThat(conferenceRooms).extracting("name").contains("Room A", "Room B");
    }

    @Test
    public void testSearchAvailableConferenceRooms() {
        LocalDateTime startDate = LocalDateTime.of(2023, 5, 1, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 5, 1, 12, 0);

        ConferenceRoom room1 = new ConferenceRoom("Room A", 40);
        ConferenceRoom room2 = new ConferenceRoom("Room B", 100);
        conferenceRoomRepository.save(room1);
        conferenceRoomRepository.save(room2);

        Reservation reservation1 = new Reservation(room1, startDate.minusHours(1), endDate.minusHours(1));
        Reservation reservation2 = new Reservation(room2, startDate.plusHours(1), endDate.plusHours(1));
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        List<ConferenceRoom> availableRooms = conferenceRoomService.searchAvailableConferenceRooms(startDate, endDate, 40);

        assertThat(availableRooms.size()).isEqualTo(1);
        assertThat(availableRooms.get(0).getName()).isEqualTo("Room A");
    }

    @Test
    public void testReserveConferenceRoom() {
        LocalDateTime startDate = LocalDateTime.of(2023, 5, 1, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 5, 1, 12, 0);

        ConferenceRoom room = new ConferenceRoom("Room A", 40);
        conferenceRoomRepository.save(room);

        boolean isReserved = conferenceRoomService.reserveConferenceRoom("Room A", startDate, endDate);

        assertThat(isReserved).isTrue();
        assertThat(reservationRepository.count()).isEqualTo(1);
        assertThat(reservationRepository.findByConferenceRoom(room)).isNotEmpty();
    }

    @Test
    public void testAddConferenceRoom() {
        ConferenceRoom room = new ConferenceRoom("Room A", 40);
        conferenceRoomService.addConferenceRoom(room);

        assertThat(conferenceRoomRepository.existsByName("Room A")).isTrue();
    }

}
