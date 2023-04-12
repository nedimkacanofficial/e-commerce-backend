package com.ndmkcn.ecommerce.service.impl;

import com.ndmkcn.ecommerce.dao.CustomerRepository;
import com.ndmkcn.ecommerce.dto.PaymentInfo;
import com.ndmkcn.ecommerce.dto.Purchase;
import com.ndmkcn.ecommerce.dto.PurchaseResponse;
import com.ndmkcn.ecommerce.entity.Customer;
import com.ndmkcn.ecommerce.entity.Order;
import com.ndmkcn.ecommerce.entity.OrderItem;
import com.ndmkcn.ecommerce.service.CheckoutService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final CustomerRepository customerRepository;
    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository, @Value("${stripe.key.secret}") String secretKey){
        this.customerRepository=customerRepository;
        // Initialize Stripe Api With Secret key
        Stripe.apiKey=secretKey;
    }
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order=purchase.getOrder();
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        Set<OrderItem> orderItems=purchase.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            order.add(orderItem);
        }
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());
        Customer customer=purchase.getCustomer();
        String theEmail=customer.getEmail();
        Customer customerFromDB=customerRepository.findByEmail(theEmail);
        if (customerFromDB != null) {
            customer=customerFromDB;
        }
        customer.add(order);
        customerRepository.save(customer);
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes=new ArrayList<>();
        paymentMethodTypes.add("card");
        Map<String,Object> params=new HashMap<>();
        params.put("amount",paymentInfo.getAmount());
        params.put("currency",paymentInfo.getCurrency());
        params.put("payment_method_types",paymentMethodTypes);
        params.put("description","ndmkcn purchase.");
        params.put("receipt_email",paymentInfo.getReceiptEmail());
        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }


}
