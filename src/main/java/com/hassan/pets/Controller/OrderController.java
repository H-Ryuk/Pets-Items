package com.hassan.pets.Controller;

import com.hassan.pets.Records.OrderDetailsRecord;
import com.hassan.pets.Model.Users;
import com.hassan.pets.Service.OrderService;
import org.hibernate.query.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("user/{userId}")
    public ResponseEntity<String> addOrderDetails(@PathVariable Long userId) {
        orderService.addOrderDetails("cart", userId);
        return new ResponseEntity<>("Items purchased successfully", HttpStatus.OK);
    }


    @GetMapping
    public List<OrderDetailsRecord> getOrderDetails(){
        return orderService.getOrderDetails();
    }


    @GetMapping("{orderId}")
    public ResponseEntity<List<OrderDetailsRecord>> getOrderDetailsByOrderId(@PathVariable Long orderId){
        List<OrderDetailsRecord> orderDetailsList =  orderService.getOrderDetailsByOrderId(orderId);
        return new ResponseEntity<>(orderDetailsList, HttpStatus.OK);
    }



}
