package com.gymbooking.member.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String age;
    private String email;
    @Enumerated(EnumType.STRING)
    private MembershipStatus membershipStatus;
}
