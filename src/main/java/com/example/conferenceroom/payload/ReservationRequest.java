package com.example.conferenceroom.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {
    private String conferenceRoomName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
