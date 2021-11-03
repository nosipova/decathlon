package com.example.demo.services;

import javax.validation.Valid;

import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Stock;
import com.example.demo.services.exception.QuantityException;

public interface IStockService {

	Stock getStock();

	Stock updateStock(Stock stock) throws QuantityException;

	Shoe addShoeToStock(Shoe shoe) throws QuantityException;

	Shoe getShoeFromStock(@Valid Shoe shoe);

}
