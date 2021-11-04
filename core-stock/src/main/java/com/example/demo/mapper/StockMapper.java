package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Stock;
import com.example.demo.dto.out.Stock.State;
import com.example.demo.entities.ShoeEntity;
import com.example.demo.entities.StockEntity;

import reactor.core.publisher.Mono;

/**
 * Mapper para transformar entidades en DTO's y viceversa
 * 
 * @author nosipova
 *
 */
@Mapper(componentModel = "spring")
public interface StockMapper {

	public static final int EMPTY = 0;
	public static final int CAPACITY_MAX = 30;

	/**
	 * Transforma un objeto Stock en StockEntity
	 * 
	 * @param stock
	 * @return StockEntity
	 */
	@Mappings({ @Mapping(target = "shoesEntity", source = "stock.shoes.shoes"), })
	StockEntity stockToStockEntity(Stock stock);

	/**
	 * Transforma un objeto StockEntity en Stock
	 * 
	 * @param stockEntity
	 * @return Stock
	 */
	@Mappings({ @Mapping(target = "shoes.shoes", source = "stockEntity.shoesEntity"),
			@Mapping(target = "state", source = "stockEntity", qualifiedByName = "stateEnum") })
	Stock stockEntityToStock(StockEntity stockEntity);

	/**
	 * Transforma un objeto ShoeEntity en Shoe
	 * 
	 * @param shoeEntity
	 * @return Shoe
	 */
	Shoe shoeEntityToShoe(ShoeEntity shoeEntity);

	List<Shoe> shoeEntityListToShoeList(List<ShoeEntity> shoeEntity);

	/**
	 * Transforma un objeto Shoe en ShoeEntity
	 * 
	 * @param shoe
	 * @return ShoeEntity
	 */
	ShoeEntity shoeToShoeEntity(Shoe shoe);

	List<ShoeEntity> shoeListToShoeEntityList(List<Shoe> shoe);

	/**
	 * Devuelve el estado del stock en función de la cantidad total
	 * 
	 * @param stockEntity
	 * @return State
	 */
	@Named("stateEnum")
	default State setStateStock(StockEntity stockEntity) {

		State state = switch (stockEntity.getTotalQuantity()) {
		case EMPTY -> State.EMPTY;
		case CAPACITY_MAX -> State.FULL;
		default -> State.SOME;
		};

		return state;

	}

}
