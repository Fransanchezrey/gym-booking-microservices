package com.gymbooking.booking.repository;

import com.gymbooking.booking.entities.WaitingListEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface WaitingListEntryRepository extends JpaRepository<WaitingListEntry, Long> {

    //Metodo para coger al primero de la cola de espera de una clase concreta
    Optional<WaitingListEntry> findFirstByScheduledClassIdOrderByCreatedAtAsc(Long scheduledClassId);

    boolean existsByMemberIdAndScheduledClassId(Long memberId, Long scheduledClassId);

    List<WaitingListEntry> findAllByScheduledClassIdOrderByCreatedAtAsc(Long scheduledClassId);




}
