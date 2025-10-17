package com.gymbooking.booking.dto;

import lombok.Data;

@Data
public class MemberResponse {
    private Long id;
    private String name;
    private String membershipStatus;
}
