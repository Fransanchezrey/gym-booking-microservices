package com.gymbooking.booking.service;

import com.gymbooking.booking.entities.Booking;
import com.gymbooking.booking.entities.BookingStatus;
import com.gymbooking.booking.exception.BusinessRuleException;
import com.gymbooking.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingsByMemberId(Long id) throws BusinessRuleException {
        List<Booking> bookings = bookingRepository.findByMemberId(id);
        if (bookings.isEmpty()) {
            throw new BusinessRuleException("1002", "Bookings not found for member id: " + id, null);
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByScheduledClassId(Long id) throws BusinessRuleException {
        List<Booking> bookings = bookingRepository.findByScheduledClassId(id);
        if (bookings.isEmpty()) {
            throw new BusinessRuleException("1002", "Bookings not found for scheduled class id: " + id, null);
        }
        return bookings;
    }

    @Override
    public Booking getBookingById(Long id) throws BusinessRuleException {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("1002", "Booking not found for id: " + id, null));
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBookingById(Long id) throws BusinessRuleException {
        if (!bookingRepository.existsById(id)) {
            throw new BusinessRuleException("1002", "Booking not found for id: " + id, null);
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking updateBookingStatus(Long id, BookingStatus status) throws BusinessRuleException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("404", "Reserva no encontrada", null));
        booking.setBookingStatus(status);
        return bookingRepository.save(booking);
    }



}
