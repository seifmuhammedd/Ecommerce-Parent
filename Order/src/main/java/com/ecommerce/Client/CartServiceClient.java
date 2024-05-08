package com.ecommerce.Client;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.DTO.ProductListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;


@Service
public class CartServiceClient {

    private final WebClient webClient;

    public CartServiceClient(WebClient.Builder webClientBuilder,  @Value("${cart.service.url}") String cartServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(cartServiceUrl).build();
    }
    public Mono<Double> getTotalAmountFromCart(Long userId) {
        return this.webClient.get()
                .uri("/cart/totalAmount/{userID}", userId)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("API Error: " + clientResponse.statusCode() + " - " + errorBody)))
                )
                .bodyToMono(Double.class)  // Directly fetching the Double value from the response
                .doOnSuccess(amount -> System.out.println("Retrieved total amount: " + amount))
                .doOnError(error -> System.out.println("Error retrieving total amount: " + error.getMessage()))
                .onErrorResume(WebClientResponseException.class, ex -> {
                    System.out.println("Server Error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
                    return Mono.error(new RuntimeException("Server error encountered.", ex));
                })
                .onErrorResume(Exception.class, ex -> {
                    System.out.println("An unexpected error occurred: " + ex);
                    return Mono.error(new RuntimeException("An unexpected error occurred while retrieving the total amount.", ex));
                });
    }

    public Mono<List<ProductDTO>> getProductsFromCart(Long userId) {
        System.out.println("Attempting to retrieve Products by UserID: " + userId);
        return this.webClient.get()
                .uri("/cart/confirmOrder/{userID}", userId)
                .retrieve()
                .bodyToMono(ProductListResponse.class)
                .map(ProductListResponse::getProducts) // Ensure this method call is effective with correct class setup
                .doOnSuccess(products -> System.out.println("Retrieved products: " + products))
                .doOnError(error -> System.out.println("Error retrieving products: " + error.getMessage()))
                .onErrorResume(WebClientResponseException.class, ex -> {
                    System.out.println("Server Error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
                    return Mono.error(new RuntimeException("Server error encountered.", ex));
                })
                .onErrorResume(Exception.class, ex -> {
                    System.out.println("An unexpected error occurred: " + ex);
                    return Mono.error(new RuntimeException("An unexpected error occurred while retrieving products.", ex));
                });
    }




}
