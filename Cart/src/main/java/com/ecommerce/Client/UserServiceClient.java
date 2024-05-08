package com.ecommerce.Client;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.DTO.UserDTO;
import io.netty.channel.ConnectTimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class UserServiceClient {

    private final WebClient webClient;

    public UserServiceClient(WebClient.Builder webClientBuilder,  @Value("${user.service.url}") String userServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(userServiceUrl).build();
    }

    public Mono<UserDTO> getUserById(Long userId) {
        System.out.println("Attempting to retrieve User with ID: " + userId);
        return this.webClient.get()
                .uri("/auth/find/{id}", userId)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .doOnSuccess(user -> System.out.println("Retrieved user: " + user))
                .doOnError(error -> System.out.println("Error retrieving user: " + error.getMessage()))
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE) || ex.getStatusCode().equals(HttpStatus.GATEWAY_TIMEOUT)) {
                        return Mono.error(new RuntimeException("User service is not running."));
                    }
                    return Mono.error(ex);
                })
                .onErrorResume(Exception.class, ex -> {
                    if (ex instanceof ConnectTimeoutException) {
                        return Mono.error(new RuntimeException("User service is not running."));
                    }
                    return Mono.error(new RuntimeException("An unexpected error occurred while retrieving user."));
                });
    }
}
