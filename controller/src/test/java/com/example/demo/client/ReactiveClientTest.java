package com.example.demo.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.example.demo.MultipleCoreImplemSampleApplication;
import com.example.demo.dto.out.Stock;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= MultipleCoreImplemSampleApplication.class)
public class ReactiveClientTest {

    @LocalServerPort
    private int port;

    @Test
    public void testReactiveCall(){
        WebClient webClient = WebClient.create("http://localhost:" + port);
        Mono<Stock> thingMono = webClient.get()
                .uri("/shoes/stock/")
                .retrieve()
                .bodyToMono(Stock.class);
        thingMono.subscribe(System.out::println);
        Stock t = thingMono.block();
        System.out.println("This is actual stock: " + t);
        Assertions.assertNotNull(t);
    }

}