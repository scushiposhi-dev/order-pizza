package com.example.orderpizza;

import com.example.orderpizza.message.PizzaOrder;
import org.openapitools.model.PizzaOrderDto;

import static java.util.UUID.randomUUID;

public class TestUtl {
    public static final String ORDER_CREATED_TOPIC = "order.created";
    public static final Integer ERR_2000_CODE = 2000;
    public static final String ERR_2000_MSG = "not retryable";
    public static final String ERR_2000_REASON = "producer failure";

    public static PizzaOrder getPizzaOrderMessageInstance() {
        return PizzaOrder.builder()
                .orderId(randomUUID())
                .pizzaType("SALAMINO")
                .build();

    }

    public static PizzaOrderDto getPizzaOrderDtoInstance() {
        PizzaOrderDto pizzaOrderDto = new PizzaOrderDto();
        pizzaOrderDto.setOrderId(randomUUID());
        pizzaOrderDto.setType(PizzaOrderDto.TypeEnum.DELIZIOSA);
        return pizzaOrderDto;
    }
}
