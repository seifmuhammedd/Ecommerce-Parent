package com.ecommerce.Client;

import com.ecommerce.DTO.ProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductServiceClient {

    private final WebClient webClient;

    public ProductServiceClient(WebClient.Builder webClientBuilder,
                                @Value("${product.service.url}") String productServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(productServiceUrl).build();
    }

    public Mono<ProductDTO> getProductById(Long productId) {
        System.out.println("Attempting to retrieve product with ID: " + productId);
        return this.webClient.get()
                .uri("/products/{id}", productId)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .doOnSuccess(product -> System.out.println("Retrieved product: " + product))
                .doOnError(error -> System.out.println("Error retrieving product: " + error.getMessage()));
    }

    public Mono<Void> updateProductStock(Long id, int newStock) {

        Map<String , Integer> body = new HashMap<>();
        body.put("newStock", newStock);

        // Make a PUT request to update the stock
        return this.webClient.put()
                .uri("/products/{id}/update-stock", id)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(aVoid -> System.out.println("Stock updated successfully for product ID: " + id))
                .doOnError(error -> System.out.println("Error updating stock: " + error.getMessage()));
    }
}
