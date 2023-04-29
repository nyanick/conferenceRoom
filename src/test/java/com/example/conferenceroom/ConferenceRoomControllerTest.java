package com.example.conferenceroom;

import com.example.conferenceroom.controller.ConferenceRoomController;
import com.example.conferenceroom.model.ConferenceRoom;
import com.example.conferenceroom.payload.SearchCriteria;
import com.example.conferenceroom.service.ConferenceRoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(ConferenceRoomController.class)
@ActiveProfiles("test")
public class ConferenceRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConferenceRoomService conferenceRoomService;

    @Test
    public void testGetConferenceRooms() throws Exception {
        ConferenceRoom conferenceRoom1 = new ConferenceRoom("Room1", 40);
        ConferenceRoom conferenceRoom2 = new ConferenceRoom("Room2", 100);
        List<ConferenceRoom> conferenceRooms = Arrays.asList(conferenceRoom1, conferenceRoom2);

        given(conferenceRoomService.getConferenceRooms()).willReturn(conferenceRooms);

        mockMvc.perform(get("/conference-rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Room1")))
                .andExpect(jsonPath("$[0].capacity", is(40)))
                .andExpect(jsonPath("$[1].name", is("Room2")))
                .andExpect(jsonPath("$[1].capacity", is(100)));
    }

    @Test
    public void testSearchConferenceRooms() throws Exception {
        ConferenceRoom conferenceRoom1 = new ConferenceRoom("Room1", 40);
        ConferenceRoom conferenceRoom2 = new ConferenceRoom("Room2", 100);
        List<ConferenceRoom> availableConferenceRooms = Collections.singletonList(conferenceRoom1);

        given(conferenceRoomService.searchAvailableConferenceRooms(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
                .willReturn(availableConferenceRooms);

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setStartDate(LocalDateTime.now());
        searchCriteria.setEndDate(LocalDateTime.now().plusHours(2));
        searchCriteria.setCapacity(40);

        mockMvc.perform(post("/conference-rooms/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(searchCriteria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Room1")))
                .andExpect(jsonPath("$[0].capacity", is(40)));
    }

}
