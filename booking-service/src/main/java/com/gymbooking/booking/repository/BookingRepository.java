package com.gymbooking.booking.repository;

import com.gymbooking.booking.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByMemberId(Long memberId);
    List<Booking> findByScheduledClassId(Long scheduledClassId);

    boolean existsByMemberIdAndScheduledClassId(Long memberId, Long scheduledClassId);
}
