package com.example.orderpizza.service;

import com.example.orderpizza.exception.NotRetryableException;
import com.example.orderpizza.message.PizzaOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.PizzaOrderDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.orderpizza.util.KafkaOrderUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PizzaOrderService {


    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void process(PizzaOrderDto pizzaOrderDto) {
        PizzaOrder orderPizza = PizzaOrder.builder()
                .orderId(pizzaOrderDto.getOrderId())
                .pizzaType(pizzaOrderDto.getType().toString()).build();

        log.info("Sending to {} topic : {}", ORDER_CREATED_TOPIC, orderPizza);
        try {
            kafkaTemplate.send(ORDER_CREATED_TOPIC, orderPizza).get();
        } catch (InterruptedException e) {
            log.error("Kafka Server is not working and Producer is not able to send a message", e.getMessage());
            Thread.currentThread().interrupt();
            throw new NotRetryableException(getNotRetryableHttpError());
        } catch (Exception e){
            log.error(e.getMessage());
            throw new NotRetryableException(getNotRetryableHttpError());
        }
    }


}
