package com.example.orderpizza.operation;

import com.example.orderpizza.service.PizzaOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.PizzaOrderApi;
import org.openapitools.model.PizzaOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderPizzaOperation implements PizzaOrderApi {
    private final PizzaOrderService pizzaOrderService;

    @Override
    public ResponseEntity<UUID> pizzaOrder(PizzaOrderDto pizzaOrder) {
        UUID orderId = randomUUID();
        pizzaOrder.setOrderId(orderId);

        pizzaOrderService.process(pizzaOrder);

        return ResponseEntity.ok(orderId);

    }
}
