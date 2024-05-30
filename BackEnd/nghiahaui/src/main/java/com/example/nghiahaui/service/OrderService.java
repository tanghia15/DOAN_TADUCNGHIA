package com.example.nghiahaui.service;

import java.util.List;

import com.example.nghiahaui.entity.Order;
import com.example.nghiahaui.entity.OrderDetail;
import com.example.nghiahaui.model.request.CreateOrderRequest;

public interface OrderService {
    
    void placeOrder(CreateOrderRequest request);

    List<Order> getList();
    
    List<Order> getOrderByUser(String username);

//    OrderDetail getOrderDetail(Long orderId);
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
    Order findOrderById(Long orderId);
}
