package com.example.demo.services.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Generador de excepciones referentes a la cantidad del stock de calzado
 * 
 * @author nosipova
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class QuantityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	private Integer code;

}
