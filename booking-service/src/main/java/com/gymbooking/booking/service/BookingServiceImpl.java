package com.gymbooking.booking.service;

import com.gymbooking.booking.client.MemberClient;
import com.gymbooking.booking.client.ScheduledClassClient;
import com.gymbooking.booking.dto.ScheduledClassResponse;
import com.gymbooking.booking.entities.Booking;
import com.gymbooking.booking.entities.BookingStatus;
import com.gymbooking.booking.exception.BusinessRuleException;
import com.gymbooking.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ScheduledClassClient scheduledClassClient;

    @Autowired
    private MemberClient memberClient;


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
    public Booking save(Booking booking) throws BusinessRuleException {
        String membershipStatus = memberClient.getMembershipStatusById(booking.getMemberId());
        if (!"ACTIVE".equalsIgnoreCase(membershipStatus)) {
            throw new BusinessRuleException("1001", "Member's membership is not active", HttpStatus.BAD_REQUEST);
        }

        // 1. Obtener la clase agendada
        ScheduledClassResponse classResponse = scheduledClassClient.getScheduledClassById(booking.getScheduledClassId());
        int spotsAvailable = classResponse.getSpotsAvailable();

        // 2. Verificar plazas disponibles
        if (spotsAvailable <= 0) {
            throw new BusinessRuleException("1004", "No hay plazas disponibles para esta clase, se te pondrá en cola.", org.springframework.http.HttpStatus.BAD_REQUEST);
        }

        // 3. Guardar la reserva
        Booking savedBooking = bookingRepository.save(booking);

        // 4. Actualizar plazas disponibles
        scheduledClassClient.updateSpotsAvailable(booking.getScheduledClassId(), spotsAvailable - 1);

        return savedBooking;
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
