package com.ndmkcn.ecommerce.service.impl;

import com.ndmkcn.ecommerce.dao.CustomerRepository;
import com.ndmkcn.ecommerce.dto.Purchase;
import com.ndmkcn.ecommerce.dto.PurchaseResponse;
import com.ndmkcn.ecommerce.entity.Customer;
import com.ndmkcn.ecommerce.entity.Order;
import com.ndmkcn.ecommerce.entity.OrderItem;
import com.ndmkcn.ecommerce.service.CheckoutService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final CustomerRepository customerRepository;
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
        customer.add(order);
        customerRepository.save(customer);
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
