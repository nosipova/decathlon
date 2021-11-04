package com.example.demo.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad que recoge el stock completo de todo el calzado
 * 
 * @author nosipova
 *
 */
@Entity
@Table(name = "stock")
@ToString
@Getter
@Setter
public class StockEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1851343383797616783L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id_Stock;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "stock")
	@OrderBy
	private Set<ShoeEntity> shoesEntity;

	private Integer totalQuantity;
}
