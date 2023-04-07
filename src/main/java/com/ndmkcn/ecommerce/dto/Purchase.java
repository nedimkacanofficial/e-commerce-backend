package com.ndmkcn.ecommerce.dto;

import com.ndmkcn.ecommerce.entity.Address;
import com.ndmkcn.ecommerce.entity.Customer;
import com.ndmkcn.ecommerce.entity.Order;
import com.ndmkcn.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
