package com.gymbooking.booking.controller;


import com.gymbooking.booking.client.MemberClient;
import com.gymbooking.booking.client.ScheduledClassClient;
import com.gymbooking.booking.dto.BookingResponse;
import com.gymbooking.booking.dto.BookingStatusUpdate;
import com.gymbooking.booking.entities.Booking;
import com.gymbooking.booking.entities.WaitingListEntry;
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
    public ResponseEntity<BookingResponse> createBooking(@RequestBody Booking booking) throws BusinessRuleException {

        // 1. Llama al servicio, que ahora devuelve un BookingResponse
        BookingResponse response = bookingService.save(booking);

        // 2. Decide qué código HTTP devolver basado en el estado de la respuesta
        if ("RESERVA_CONFIRMADA".equals(response.getStatus())) {
            // Si la reserva se creó, devolvemos 201 Created.
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else { // "AÑADIDO_A_LISTA_DE_ESPERA"
            // Si se añadió a la lista de espera, la operación fue exitosa, pero no se creó
            // una nueva reserva. Devolvemos 200 OK.
            return ResponseEntity.ok(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable Long id,
            @RequestBody BookingStatusUpdate status) throws BusinessRuleException {
        Booking updated = bookingService.updateBookingStatus(id, status.getStatus());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") Long id) throws BusinessRuleException {
        bookingService.deleteBookingById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/waiting-list/{scheduledClassId}")
    public ResponseEntity<List<?>> getWaitingListForClass(@PathVariable Long scheduledClassId) {
        List<WaitingListEntry> waitingList = bookingService.getWaitingListForClass(scheduledClassId);
        return ResponseEntity.ok(waitingList);
    }



}


