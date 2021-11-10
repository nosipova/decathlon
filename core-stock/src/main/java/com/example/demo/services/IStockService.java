package com.example.demo.services;

import javax.validation.Valid;

import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Stock;
import com.example.demo.services.exception.QuantityException;

import reactor.core.publisher.Mono;

public interface IStockService {

	Mono<Stock> getStock();

	Mono<Stock> updateStock(Stock stock) throws QuantityException;

	Mono<Shoe> addShoeToStock(Shoe shoe) throws QuantityException;

	Shoe getShoeFromStock(@Valid Shoe shoe);

}
