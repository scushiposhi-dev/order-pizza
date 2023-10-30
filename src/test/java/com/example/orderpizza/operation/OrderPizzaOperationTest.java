package com.example.orderpizza.operation;

import com.example.orderpizza.exception.HttpError;
import com.example.orderpizza.exception.NotRetryableException;
import com.example.orderpizza.service.PizzaOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.PizzaOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.orderpizza.TestUtl.*;
import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({OrderPizzaOperation.class})
class OrderPizzaOperationTest {
    @MockBean
    PizzaOrderService pizzaOrderService;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    MockMvc mockMvc;

    PizzaOrderDto pizzaOrderDto;
    HttpError httpErrorNotRetryable;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        pizzaOrderDto = new PizzaOrderDto();
        pizzaOrderDto.setType(PizzaOrderDto.TypeEnum.DELIZIOSA);
        pizzaOrderDto.setOrderId(randomUUID());

        httpErrorNotRetryable = HttpError.builder()
                .code(ERR_2000_CODE)
                .message(ERR_2000_MSG)
                .reason(ERR_2000_REASON).build();
    }

    @AfterEach
    void tearDown() {
        reset(pizzaOrderService);
    }

    @Test
    void pizzaOrder_ThrowNotRetryableException() throws Exception {


        NotRetryableException notRetryableException = new NotRetryableException(HttpStatus.SERVICE_UNAVAILABLE, httpErrorNotRetryable);
        doThrow(notRetryableException).when(pizzaOrderService).process(any());

        mockMvc.perform(post("/v1/pizzaorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pizzaOrderDto)))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.code").value(ERR_2000_CODE))
                .andExpect(jsonPath("$.message").value(ERR_2000_MSG))
                .andExpect(jsonPath("$.reason").value(ERR_2000_REASON));
    }
}