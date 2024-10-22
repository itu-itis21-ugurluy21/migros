package com.example.spring_boot.Services;

import com.example.spring_boot.Dto.CreateOrderDTO;
import com.example.spring_boot.Entity.Order;
import com.example.spring_boot.Entity.OrderContent;
import com.example.spring_boot.Entity.Product;
import com.example.spring_boot.Entity.User;
import com.example.spring_boot.Repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }


    // Cancels the order if by changing the status of the order
    // 0 --> Waiting for delivery
    // 1 --> Order completed   --> After this maybe we can check order data etc. to decide whether or not to cancel it.
    // 2 --> Order is cancelled
    @Transactional()
    public String cancelOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        System.out.println("Request is Coming");
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.getStatus() == 2) {
                System.out.println("Already cancelled");
                return "Order is already canceled";
            }
            System.out.println("Now cancelled");
            order.setStatus(2);
            orderRepository.save(order);
            return "Order canceled successfully";
        } else {
            throw new EntityNotFoundException("Order not found");
        }
    }

    public float GetTotalAmount(int paymentMethod){
        float  result = 0;
        int amount = 0;
        List<Order> orders = orderRepository.findByPaymentMethod(paymentMethod);
        for(Order order : orders){
            System.out.println(order);
            for(OrderContent orderContent : order.getContents()){
                int prodAmount = orderContent.getAmount();
                float totalPrice = orderContent.getProduct().getPrice() * prodAmount;
                result += totalPrice;
                amount += prodAmount;
            }
        }
        System.out.println(result);
        System.out.println(amount);
        return result/amount;
    }


    @Transactional
    public Order SaveOrder(CreateOrderDTO orderDto){
        // Maybe check for ids
        Order order = new Order();

        ArrayList<OrderContent> orderContents = new ArrayList<>();
        for(Map.Entry<String,Integer> ent :  orderDto.getProducts().entrySet()){
            Integer productId = Integer.parseInt(ent.getKey()) ;
            Integer amount = ent.getValue();

            Product prod = productService.getProduct(productId.longValue());

            OrderContent orderContent = new OrderContent();
            orderContent.setProduct(prod);
            orderContent.setOrder(order);
            orderContent.setAmount(amount);
            orderContents.add(orderContent);
        }

        order.setContents(orderContents);
        order.setStatus(0);
        User user = userService.GetUserById(orderDto.getUserId());
        order.setUser(user);
        order.setPaymentMethod(0);  // Kapıda ödeme

       orderRepository.save(order);
       return order;
    }

}
