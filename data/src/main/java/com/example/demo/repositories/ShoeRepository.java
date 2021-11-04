package com.example.demo.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.demo.entities.ShoeEntity;

/**
 * Repositorio del calzado
 * 
 * @author nosipova
 *
 */
public interface ShoeRepository extends JpaRepository<ShoeEntity, BigInteger> {

}
