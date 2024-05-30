package com.example.nghiahaui.controller;

import com.example.nghiahaui.entity.Order;
import com.example.nghiahaui.entity.OrderDetail;
import com.example.nghiahaui.entity.Product;
import com.example.nghiahaui.service.OrderService;
import com.example.nghiahaui.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*",maxAge = 3600)
public class OrderDetailController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

//    @GetMapping("/{orderId}")
//    @Operation(summary = "Lấy ra order_detail cua mot don hang cu the")
//    public ResponseEntity<OrderDetail> getOrderDetail(@PathVariable Long orderId) {
//        OrderDetail orderDetail = orderService.getOrderDetail(orderId);
//
//        if (orderDetail != null) {
//            return ResponseEntity.ok(orderDetail);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @GetMapping("/{orderId}")
    @Operation(summary = "Lấy ra các chi tiết đơn hàng của một đơn hàng cụ thể")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        List<OrderDetail> orderDetails = orderService.getOrderDetailsByOrderId(orderId);

        if (!orderDetails.isEmpty()) {
            return ResponseEntity.ok(orderDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//@GetMapping("/{orderId}")
//@Operation(summary = "Lấy ra các chi tiết đơn hàng của một đơn hàng cụ thể")
//public ResponseEntity<List<OrderDetail>> getOrderDetailsByOrderId(@PathVariable Long orderId) {
//    List<OrderDetail> orderDetails = orderService.getOrderDetailsByOrderId(orderId);
//
//    for (OrderDetail orderDetail : orderDetails) {
//        Product product = orderDetail.getProduct();
//        if (product == null) {
//            // Nếu sản phẩm trong orderDetail là null, bạn cần tải thông tin sản phẩm từ ProductService
//            Long productId = orderDetail.getProductId(); // Giả sử có một phương thức để lấy productId từ orderDetail
//            product = productService.getProductById(productId);
//            orderDetail.setProduct(product);
//        }
//    }
//
//    if (!orderDetails.isEmpty()) {
//        return ResponseEntity.ok(orderDetails);
//    } else {
//        return ResponseEntity.notFound().build();
//    }
}





