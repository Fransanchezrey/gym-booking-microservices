package com.gymbooking.booking.service;

import com.gymbooking.booking.entities.Booking;
import com.gymbooking.booking.entities.BookingStatus;
import com.gymbooking.booking.exception.BusinessRuleException;

import java.util.List;

public interface BookingService {

    List<Booking> findAll();

    Booking getBookingById(Long id) throws BusinessRuleException;

    Booking save(Booking booking) throws  BusinessRuleException;

    void deleteBookingById(Long id) throws BusinessRuleException;

    List<Booking> getBookingsByMemberId(Long memberId) throws BusinessRuleException;

    List<Booking> getBookingsByScheduledClassId(Long scheduledClassId) throws BusinessRuleException;

    Booking updateBookingStatus(Long id, BookingStatus status) throws BusinessRuleException;
}
