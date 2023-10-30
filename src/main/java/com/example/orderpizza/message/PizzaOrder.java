package com.example.orderpizza.message;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PizzaOrder {
    private UUID orderId;
    private String pizzaType;
}
