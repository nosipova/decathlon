package com.example.demo.dto.out;

import java.math.BigInteger;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.demo.dto.out.Stock.StockBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;

/**
 * dto del stock 
 * 
 * @author nosipova
 *
 */
@Value
@Builder
@JsonDeserialize(builder = StockBuilder.class)
public class Stock {

	private State state;

	private Shoes shoes;

	@NotEmpty
	@NotNull
	@Max(30)
	@Min(0)
	private BigInteger totalQuantity;


	public enum State {

		EMPTY, FULL, SOME;

	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class StockBuilder {

	}

}
