package com.example.spring_boot.Controllers;


import com.example.spring_boot.Dto.CreateOrderDTO;
import com.example.spring_boot.Entity.Order;
import com.example.spring_boot.Entity.OrderContent;
import com.example.spring_boot.Entity.Product;
import com.example.spring_boot.Entity.User;
import com.example.spring_boot.Services.OrderService;
import com.example.spring_boot.Services.ProductService;
import com.example.spring_boot.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class HomeController {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @PostMapping("/cancel-order/{id}")
    public String CancelOrder(@PathVariable Long id){
      try {
          return orderService.cancelOrder(id);
      } catch (Exception e) {
          throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
      }
    }

    @GetMapping("/order/{id}")
    public List<Order> GetOrder(@PathVariable Long id){
        return orderService.getAllOrders();
    }

    @GetMapping("/order/total-payment")
    public float GetTotalAmount(@RequestParam int paymentMethod){
        return orderService.GetTotalAmount(paymentMethod);
    }

    @PostMapping("/order/create")
    public ResponseEntity<Order> CreateOrder(@RequestBody CreateOrderDTO orderDto){
        Order order = orderService.SaveOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
