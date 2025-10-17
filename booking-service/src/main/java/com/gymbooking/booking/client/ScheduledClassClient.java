package com.gymbooking.booking.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class ScheduledClassClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<ScheduledClassResponse> getScheduledClassesByDateRange(String fromDate, String toDate) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/api/v1/timetable?fromDate=" + fromDate + "&toDate=" + toDate)
                .retrieve()
                .bodyToFlux(ScheduledClassResponse.class)
                .collectList()
                .block();
    }

    // DTO interno para la respuesta
    private static class ScheduledClassResponse {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
