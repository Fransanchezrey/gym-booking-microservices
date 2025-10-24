package com.gymbooking.booking.client;


import com.gymbooking.booking.dto.ScheduledClassResponse;
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


    public ScheduledClassResponse getScheduledClassById(Long id) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/api/v1/timetable/" + id)
                .retrieve()
                .bodyToMono(ScheduledClassResponse.class)
                .block();
    }

    // ScheduledClassClient.java

    public ScheduledClassResponse updateSpotsAvailable(Long id, int spots) {
        return webClientBuilder.build()
                .patch()
                .uri("http://localhost:8080/api/v1/timetable/{id}/spots?spots={spots}", id, spots)
                .retrieve()
                .bodyToMono(ScheduledClassResponse.class)
                .block();
    }

}
