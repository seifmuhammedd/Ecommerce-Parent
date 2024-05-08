package com.ecommerce.Controller;

import com.ecommerce.Client.ProductServiceClient;
import com.ecommerce.Client.UserServiceClient;
import com.ecommerce.DTO.*;
import com.ecommerce.Model.Cart;
import com.ecommerce.Service.CartItemService;
import com.ecommerce.Service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Controller
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    private final CartItemService cartItemService;

    private final ProductServiceClient productServiceClient;

    private final UserServiceClient userServiceClient;


    public CartController(ProductServiceClient productServiceClient, CartService cartService, CartItemService cartItemService, UserServiceClient userServiceClient) {
        this.productServiceClient = productServiceClient;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.userServiceClient = userServiceClient;
    }
    public List<Double> cartTotalCost = new ArrayList<>();

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProductToCart(@RequestBody CartRequest cartRequest)
    {
        Map<String, Object> response = new HashMap<>();
        UserDTO user =userServiceClient.getUserById(cartRequest.getUserID()).block();
        ProductDTO product = productServiceClient.getProductById(cartRequest.getProductID()).block();

        if (user == null || product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User or product not found");
        }

        Cart cart = cartService.getOrCreateCart(user.getUserId()); //create a cart fot this user or if he has already a one return it

        //check if product available in store
        Boolean isProdAvailable = cartService.isProductAvailable(product.getStock());
        if(!isProdAvailable)
        {
            response.put("message", product.getProductName() + " is out of stock");
            return ResponseEntity.ok().body(response);
        }

            CartDTO cartDTO = cartService.addingProductToCurrentCart(cart,product);

            productServiceClient.updateProductStock(product.getProductId(), product.getStock()).block();


        response.put("message", product.getProductName() + " added successfully to Cart");
        response.put("cart", cartDTO);
        return  ResponseEntity.ok().body(response);
    }

    @GetMapping("/getProducts/{userID}")
    public ResponseEntity<?> getProductsFromCart(@PathVariable Long userID)
    {
        Map<String, Object> response = new HashMap<>();
        List<CartItemDTO> products = cartItemService.getCartItemsByUserId(userID);
        response.put("Products" , products);
        return  ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/deleteProducts/{userID}")
    public ResponseEntity<?> deleteProductsFromCart(@PathVariable Long userID)
    {
        Cart cart = cartService.findCartByUserId(userID);
        cartItemService.deleteProductsFromCartItem(cart.getCartId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/confirmOrder/{userID}")
    public ResponseEntity<?> coniformOrder(@PathVariable Long userID)
    {
        Map<String, Object> response = new HashMap<>();
        List<CartItemDTO> products = cartItemService.getCartItemsByUserId(userID);
        response.put("Products" , products);
        Cart cart = cartService.findCartByUserId(userID);
        cartItemService.deleteProductsFromCartItem(cart.getCartId());
        return  ResponseEntity.ok().body(response);

    }

    @GetMapping("/totalAmount/{userID}")
    public ResponseEntity<?> getTotalAmountOfCart(@PathVariable Long userID)
    {
        Cart cart = cartService.findCartByUserId(userID);
        Double totalCostResponse = cart.getTotalCost();
        cart.setTotalCost(0.0);
        cartService.updateCart(cart);
        return  ResponseEntity.ok().body(cart.getTotalCost());
    }
}


