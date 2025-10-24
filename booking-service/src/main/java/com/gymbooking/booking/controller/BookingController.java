package com.gymbooking.booking.controller;


import com.gymbooking.booking.client.MemberClient;
import com.gymbooking.booking.client.ScheduledClassClient;
import com.gymbooking.booking.dto.BookingStatusUpdate;
import com.gymbooking.booking.entities.Booking;
import com.gymbooking.booking.exception.BusinessRuleException;
import com.gymbooking.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private MemberClient memberClient;

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.findAll();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/by-member")
    public ResponseEntity<List<Booking>> getBookingsByMemberId(
            @RequestParam("memberId") Long memberId) throws BusinessRuleException {
        List<Booking> bookings = bookingService.getBookingsByMemberId(memberId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/by-class")
    public ResponseEntity<List<Booking>> getBookingsByScheduledClassId(
            @RequestParam("scheduledClassId") Long scheduledClassId) throws BusinessRuleException {
        List<Booking> bookings = bookingService.getBookingsByScheduledClassId(scheduledClassId);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) throws BusinessRuleException {
        Booking savedBooking = bookingService.save(booking);
        return ResponseEntity.status(201).body(savedBooking);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable Long id,
            @RequestBody BookingStatusUpdate status) throws BusinessRuleException {
        Booking updated = bookingService.updateBookingStatus(id, status.getStatus());
        return ResponseEntity.ok(updated);
    }



}


