package com.example.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.example.demo.entities.ShoeEntity;
import com.example.demo.entities.StockEntity;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class RepositoryTest {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ShoeRepository shoeRepository;

	@BeforeEach
	public void initDB() {

	}

	@Test
	public void getAllStock() {
		List<StockEntity> stocks = this.stockRepository.findAll();
		assertThat(stocks).isNotEmpty();
		assertThat(stocks.size()).isEqualTo(1);
	}

	@Test
	public void getAllShoesFromStock() {
		assertThat(stockRepository.count()).isEqualTo(1);
		StockEntity stockEntity = this.stockRepository.findAll().get(0);
		assertThat(stockEntity.getShoesEntity()).isNotEmpty();

	}

	@Test
	public void getExistsShoe() {

		ShoeEntity shoeEntity = new ShoeEntity();
		shoeEntity.setColor("BLACK");
		shoeEntity.setSize(BigInteger.valueOf(42));

		Example<ShoeEntity> example = Example.of(shoeEntity);

		assertThat(this.shoeRepository.exists(example)).isTrue();

		Optional<ShoeEntity> optionalShoeEntity = this.shoeRepository.findOne(example);
		assertThat(optionalShoeEntity.get()).isNotNull();

	}

	@Test
	public void getNotExistsShoe() {

		ShoeEntity shoeEntity = new ShoeEntity();
		shoeEntity.setColor("BLACK");
		shoeEntity.setId_Shoe(BigInteger.TWO);
		shoeEntity.setQuantity(BigInteger.valueOf(210));
		shoeEntity.setSize(BigInteger.valueOf(20));
		Example<ShoeEntity> example = Example.of(shoeEntity);

		assertThat(this.shoeRepository.exists(example)).isFalse();

	}

}
