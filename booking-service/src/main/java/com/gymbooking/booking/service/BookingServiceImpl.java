package com.gymbooking.booking.service;

import com.gymbooking.booking.client.MemberClient;
import com.gymbooking.booking.client.ScheduledClassClient;
import com.gymbooking.booking.dto.BookingResponse;
import com.gymbooking.booking.dto.ScheduledClassResponse;
import com.gymbooking.booking.entities.Booking;
import com.gymbooking.booking.entities.BookingStatus;
import com.gymbooking.booking.entities.WaitingListEntry;
import com.gymbooking.booking.exception.BusinessRuleException;
import com.gymbooking.booking.repository.BookingRepository;
import com.gymbooking.booking.repository.WaitingListEntryRepository;
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

    @Autowired
    private WaitingListEntryRepository waitingListEntryRepository;


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
    public BookingResponse save(Booking booking) throws BusinessRuleException {
        // Paso 1: Validar todas las condiciones previas.
        validatePreconditions(booking);

        // Paso 2: Obtener el estado actual de la clase.
        ScheduledClassResponse classResponse = scheduledClassClient.getScheduledClassById(booking.getScheduledClassId());
        int spotsAvailable = classResponse.getSpotsAvailable();

        // Paso 3: Decidir el flujo basado en las plazas disponibles.
        if (spotsAvailable > 0) {
            return handleAvailableClass(booking, spotsAvailable);
        } else {
            return handleFullClass(booking);
        }
    }

    /**
     * Valida las reglas antes de intentar una reserva o lista de espera.
     */
    private void validatePreconditions(Booking booking) throws BusinessRuleException {
        String membershipStatus = memberClient.getMembershipStatusById(booking.getMemberId());
        if (!"ACTIVE".equalsIgnoreCase(membershipStatus)) {
            throw new BusinessRuleException("1001", "Membership is not ACTIVE", HttpStatus.BAD_REQUEST);
        }

        if (bookingRepository.existsByMemberIdAndScheduledClassId(booking.getMemberId(), booking.getScheduledClassId())) {
            throw new BusinessRuleException("1005", "Member already have a booking.", HttpStatus.CONFLICT);
        }

        if (waitingListEntryRepository.existsByMemberIdAndScheduledClassId(booking.getMemberId(), booking.getScheduledClassId())) {
            throw new BusinessRuleException("1006", "Member is already in the waiting list..", HttpStatus.CONFLICT);
        }
    }

    /**
     * Gestiona la lógica cuando hay plazas disponibles.
     */
    private BookingResponse handleAvailableClass(Booking booking, int spotsAvailable) {
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        // Actualizar plazas
        scheduledClassClient.updateSpotsAvailable(booking.getScheduledClassId(), spotsAvailable - 1);

        return new BookingResponse("RESERVA_CONFIRMADA", "Tu plaza ha sido confirmada.");
    }

    /**
     * Gestiona la lógica cuando la clase está llena.
     */
    private BookingResponse handleFullClass(Booking booking) {
        WaitingListEntry newEntry = new WaitingListEntry();
        newEntry.setMemberId(booking.getMemberId());
        newEntry.setScheduledClassId(booking.getScheduledClassId());
        waitingListEntryRepository.save(newEntry);

        return new BookingResponse("AÑADIDO_A_LISTA_DE_ESPERA", "La clase está llena. Te hemos añadido a la lista de espera.");
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
