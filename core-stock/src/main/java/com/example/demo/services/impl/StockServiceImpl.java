package com.example.demo.services.impl;

import java.math.BigInteger;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoes;
import com.example.demo.dto.out.Stock;
import com.example.demo.dto.out.Stock.State;
import com.example.demo.entities.ShoeEntity;
import com.example.demo.entities.StockEntity;
import com.example.demo.mapper.StockMapper;
import com.example.demo.repositories.ShoeRepository;
import com.example.demo.repositories.StockRepository;
import com.example.demo.services.IStockService;
import com.example.demo.services.exception.QuantityException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@PropertySource("classpath:messages.properties")
public class StockServiceImpl implements IStockService {

	private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

	@Value("${message.capacity_max_30}")
	public String messageCapacity;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private ShoeRepository shoeRepository;

	@Autowired
	private StockMapper stockMapper;

	@Value("${message.capacity_full}")
	private String messageCapacityFull;

	public Stock getStock() {

		StockEntity stockEntity = stockRepository.getCurrentStockWithShoes();

		return stockMapper.stockEntityToStock(stockEntity);
	}

	@Override
	public Stock updateStock(Stock stock) throws QuantityException {

		BigInteger totalQuantity = calculateQuantityShoes(stock.getShoes());

		if (totalQuantity.intValue() > 30) {

			logger.warn(" [StockServiceImpl.updateStock] " + messageCapacity + totalQuantity.intValue());

			throw new QuantityException(" [StockServiceImpl.updateStock] " + messageCapacity + totalQuantity.intValue(),
					1);
		}

		this.shoeRepository.deleteAll();
		this.stockRepository.deleteAll();
		StockEntity stockSaved = this.stockRepository.save(this.stockMapper.stockToStockEntity(stock));
		List<ShoeEntity> mergedShoes = new ArrayList<ShoeEntity>();
		for (ShoeEntity s : stockSaved.getShoesEntity()) {
			s.setStock(stockSaved);
			mergedShoes.add(s);
		}
		this.shoeRepository.saveAll(mergedShoes);

		return this.stockMapper.stockEntityToStock(stockSaved);
	}

	private BigInteger calculateQuantityShoes(Shoes shoes) {
		BigInteger somme = BigInteger.valueOf(0);
		for (Shoe shoe : shoes.getShoes()) {
			somme = somme.add(shoe.getQuantity());
		}
		return somme;
	}

	@Override
	public Shoe addShoeToStock(Shoe shoe) throws QuantityException {

		StockEntity stockEntity = stockRepository.getCurrentStockWithShoes();
		Stock stock = stockMapper.stockEntityToStock(stockEntity);
		ShoeEntity shoeEntity = new ShoeEntity();
		if (State.FULL.equals(stock.getState())) {

			logger.warn(" [StockServiceImpl.addShoeToStock] " + messageCapacityFull + stock.getTotalQuantity());
			throw new QuantityException(" [StockServiceImpl.addShoeToStock] " + messageCapacityFull, 3);

		}

		Example<ShoeEntity> example = prepareExampleMatcherShoeToSearch(shoe);

		if (shoeRepository.exists(example)) {

			shoeEntity = shoeRepository.findOne(example).get();
			shoeEntity = updateStockAddNewShoes(stockEntity, shoeEntity);

		} else {

			shoeEntity = stockMapper.shoeToShoeEntity(shoe);
			shoeEntity.setStock(stockEntity);

			shoeEntity = updateStockAddNewShoes(stockEntity, shoeEntity);

		}

		return stockMapper.shoeEntityToShoe(shoeEntity);
	}

	/**
	 * Actualiza el stock total del calzado
	 * 
	 * @param stokEntity
	 * @param currentStock
	 * @param shoeEntity
	 * @throws QuantityException
	 */
	private ShoeEntity updateStockAddNewShoes(StockEntity stockEntity, ShoeEntity shoeEntity) throws QuantityException {

		Integer newStock = Integer.sum(stockEntity.getTotalQuantity(), shoeEntity.getQuantity().intValue());
		if (newStock > 30) {
			logger.warn(" [StockServiceImpl.updateStockAddNewShoes] " + messageCapacity + newStock);
			throw new QuantityException(messageCapacity + newStock, 5);
		}
		stockEntity.setTotalQuantity(newStock);
		stockRepository.saveAndFlush(stockEntity);

		return shoeRepository.save(shoeEntity);
	}

	/**
	 * Setea los parámetros de búsqueda a la entidad del calzado
	 * 
	 * @param shoe
	 * @return Example<ShoeEntity>
	 */
	private Example<ShoeEntity> prepareExampleMatcherShoeToSearch(Shoe shoe) {

		ShoeEntity shoeToSearch = new ShoeEntity();
		shoeToSearch.setSize(shoe.getSize());
		shoeToSearch.setColor(shoe.getColor().toString());

		Example<ShoeEntity> example = Example.of(shoeToSearch);
		return example;
	}

	@Override
	public Shoe getShoeFromStock(@Valid Shoe shoe) {

		Optional<ShoeEntity> shoeOptionnal = this.shoeRepository.findOne(prepareExampleMatcherShoeToSearch(shoe));

		ShoeEntity shoeEntity = null;

		if (shoeOptionnal != null) {
			shoeEntity = shoeOptionnal.get();
		}

		return this.stockMapper.shoeEntityToShoe(shoeEntity);
	}

}
