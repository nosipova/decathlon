package com.example.demo.entities;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad ShoeEntity correspondiente a un par de zapatos
 * 
 * @author nosipova
 *
 */
@Entity
@Table(name = "shoe")
@ToString
@Getter
@Setter
public class ShoeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3914580891294342868L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id_Shoe;

	@Column(name = "name")
	private String name;

	@Column(name = "size")
	private BigInteger size;

	@Column(name = "color")
	private String color;

	@Column(name = "quantity")
	private BigInteger quantity;

	@ManyToOne
	@JoinColumn(name = "id_stock", referencedColumnName = "id_stock")
	private StockEntity stock;
}
