package com.ndmkcn.ecommerce.controller;

import com.ndmkcn.ecommerce.dto.PaymentInfo;
import com.ndmkcn.ecommerce.dto.Purchase;
import com.ndmkcn.ecommerce.dto.PurchaseResponse;
import com.ndmkcn.ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@AllArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;
    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase){
        return checkoutService.placeOrder(purchase);
    }
    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        PaymentIntent paymentIntent=checkoutService.createPaymentIntent(paymentInfo);
        String paymentStr=paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
}
