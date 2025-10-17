package com.gymbooking.booking.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long scheduledClassId;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;


}
