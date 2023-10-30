package com.example.orderpizza.service;

import com.example.orderpizza.exception.HttpError;
import com.example.orderpizza.exception.NotRetryableException;
import com.example.orderpizza.message.PizzaOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

import static com.example.orderpizza.TestUtl.ORDER_CREATED_TOPIC;
import static com.example.orderpizza.TestUtl.getPizzaOrderDtoInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PizzaOrderServiceTest {

    PizzaOrderService pizzaOrderService;
    KafkaTemplate<String,Object> kafkaTemplateProducerMock;

    @BeforeEach
    void setUp() {
        kafkaTemplateProducerMock = mock(KafkaTemplate.class);
        pizzaOrderService =new PizzaOrderService(kafkaTemplateProducerMock);
    }

    @Test
    void process_Success() throws Exception {
        when(kafkaTemplateProducerMock.send(anyString(),any(PizzaOrder.class)))
                .thenReturn(mock(CompletableFuture.class));

        pizzaOrderService.process(getPizzaOrderDtoInstance());

        verify(kafkaTemplateProducerMock, times(1))
                .send(eq(ORDER_CREATED_TOPIC), any(PizzaOrder.class));
    }

    @Test
    void process_ProducerThrowsException() {
        doThrow(new NotRetryableException(HttpError.builder().build()))
                .when(kafkaTemplateProducerMock)
                .send(eq(ORDER_CREATED_TOPIC),any(PizzaOrder.class));

        assertThrows(NotRetryableException.class, () -> pizzaOrderService.process(getPizzaOrderDtoInstance()));

        verify(kafkaTemplateProducerMock,times(1))
                .send(eq(ORDER_CREATED_TOPIC),any(PizzaOrder.class));

    }
}