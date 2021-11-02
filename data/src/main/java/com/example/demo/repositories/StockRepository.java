package com.example.demo.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.demo.entities.StockEntity;

/**
 * Repositorio del stock del calzado
 * 
 * @author nosipova
 *
 */
public interface StockRepository extends JpaRepository<StockEntity, BigInteger> {


}
