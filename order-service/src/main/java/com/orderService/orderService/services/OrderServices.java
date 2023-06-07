package com.orderService.orderService.services;

import com.orderService.orderService.dto.InventoryResponse;
import com.orderService.orderService.dto.OrderLineItemsDto;
import com.orderService.orderService.dto.OrderRequest;
import com.orderService.orderService.models.OrderLineItems;
import com.orderService.orderService.models.Orders;
import com.orderService.orderService.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServices {
    private final OrderRepository orderRepository;
    private final  WebClient.Builder webClientBuilder;
    public void placeOrder(OrderRequest orderRequest){
        Orders order = new Orders();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList= orderRequest.getOrderLineItemsDtos().stream().map(this::mapToDto).toList();
        order.setOrderLineItemsList(orderLineItemsList);
        //Collect all the skuCode from the order request body
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
        //Call Inventory service synchronously and place if order is in stock
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        assert inventoryResponses != null;
        // We are checking if inventory has > 0 and user request is less than equal to max inventory count
        Map<String, Integer> invResponse = new HashMap<>();
        Map<String, Integer> userOrderRequest = new HashMap<>();
        for (OrderLineItems orderLineItems : orderLineItemsList) {
            userOrderRequest.put(orderLineItems.getSkuCode(), orderLineItems.getQuantity());
        }
        for (InventoryResponse inventoryResponse:inventoryResponses ){
            invResponse.put(inventoryResponse.getSkuCode(),inventoryResponse.getIsInStock());
        }
        int count =0;
        for (String i : userOrderRequest.keySet()){
            if(invResponse.containsKey(i)){
                if(invResponse.get(i)>0 && userOrderRequest.get(i)<= invResponse.get(i)){
                    count++;
                }
            }
        }
//        boolean allProductsInStock=Arrays.stream(inventoryResponses).map(in);
        if(count== userOrderRequest.size()) {
            //this interface will actually write the data from model class to database
            orderRepository.save(order);
        }else
            throw new IllegalArgumentException("Product is not in stock, please try later ");


    }
//    we are mapping request body to the model class OrderLineItems
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return  orderLineItems;
    }
}
