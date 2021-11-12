package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Stock;
import com.example.demo.services.IStockService;
import com.example.demo.services.exception.QuantityException;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 *
 * @author nosipova
 *
 */
@RestController
@RequestMapping(path = "/shoes")
@RequiredArgsConstructor
public class StockController {

	@Autowired
	private IStockService stockService;

	@GetMapping(path = "/stock")
	@ApiOperation(value = "Return all stock shoes and status")
	public Mono<Stock> getStock() {

		return stockService.getStock();

	}

	@PatchMapping(path = "/stock")
	@ApiOperation(value = "Update the stock (Two color accepted: BLACK and BLUE) and max capacity is 30")
	public ResponseEntity<Mono<?>> updateStock(@Valid @RequestBody Stock stock) throws QuantityException {

		return ResponseEntity.ok(stockService.updateStock(stock));

	}

	@PutMapping(path = "/stock/shoe")
	@ApiOperation(value = "Add shoe to the stock")
	public ResponseEntity<Mono<?>> addShoeToStock(@Valid Shoe shoe) throws QuantityException {

		return ResponseEntity.ok(stockService.addShoeToStock(shoe));

	}

}
