package com.gymbooking.booking.client;

import com.gymbooking.booking.dto.MemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MemberClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public String getMembershipStatusById(Long memberId) {
        MemberResponse response = webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/api/v1/members/" + memberId)
                .retrieve()
                .bodyToMono(MemberResponse.class)
                .block();
        return response != null ? response.getMembershipStatus() : null;
    }
}
