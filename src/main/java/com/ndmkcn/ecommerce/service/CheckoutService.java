package com.ndmkcn.ecommerce.service;

import com.ndmkcn.ecommerce.dto.PaymentInfo;
import com.ndmkcn.ecommerce.dto.Purchase;
import com.ndmkcn.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
