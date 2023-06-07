package com.orderService.orderService.controllers;

import com.orderService.orderService.dto.OrderRequest;
import com.orderService.orderService.services.OrderServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderControllers {
    private final OrderServices orderServices;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderServices.placeOrder(orderRequest);
        return "Order placed successfully";
    }
}
