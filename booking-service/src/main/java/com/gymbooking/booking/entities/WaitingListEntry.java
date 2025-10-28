package com.gymbooking.booking.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class WaitingListEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private Long scheduledClassId;

    //IMPORTANTE: Esto nos crea la marca de fecha y hora automaticamente al crear la entrada en la lista de espera
    @CreationTimestamp
    private LocalDateTime createdAt;
}
