package com.example.demo.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.entities.StockEntity;

/**
 * Repositorio del stock del calzado
 * 
 * @author nosipova
 *
 */
public interface StockRepository extends JpaRepository<StockEntity, BigInteger> {

	@Query("SELECT s FROM StockEntity s")
	StockEntity getCurrentStock();
	
	@Query("SELECT s FROM StockEntity s " + "INNER JOIN s.shoesEntity shoes order by shoes.id")
	StockEntity getCurrentStockWithShoes();
}
