package com.ndmkcn.ecommerce.controller;

import com.ndmkcn.ecommerce.dto.Purchase;
import com.ndmkcn.ecommerce.dto.PurchaseResponse;
import com.ndmkcn.ecommerce.service.CheckoutService;
import lombok.AllArgsConstructor;
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
}
