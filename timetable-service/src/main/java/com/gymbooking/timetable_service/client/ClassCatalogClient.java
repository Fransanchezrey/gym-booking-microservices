package com.gymbooking.timetable_service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ClassCatalogClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public String getClassNameById(Long classId) {

        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/api/v1/classes/" + classId)
                .retrieve()
                .bodyToMono(ClassResponse.class)
                .block()
                .getName();
    }

    // DTO interno para la respuesta
    private static class ClassResponse {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
