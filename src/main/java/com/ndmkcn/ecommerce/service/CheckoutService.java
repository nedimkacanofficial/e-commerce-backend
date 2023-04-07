package com.ndmkcn.ecommerce.service;

import com.ndmkcn.ecommerce.dto.Purchase;
import com.ndmkcn.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
