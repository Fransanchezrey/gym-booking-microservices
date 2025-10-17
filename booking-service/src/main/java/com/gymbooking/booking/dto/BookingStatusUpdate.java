package com.gymbooking.booking.dto;

import com.gymbooking.booking.entities.BookingStatus;
import lombok.Data;

@Data
public class BookingStatusUpdate {
    private BookingStatus status;
}
