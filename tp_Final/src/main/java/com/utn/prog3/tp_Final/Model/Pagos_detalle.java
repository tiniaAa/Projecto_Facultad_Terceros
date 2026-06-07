package com.utn.prog3.tp_Final.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="pagos_detalle")
@Getter
@Setter
public class Pagos_detalle {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="INSTRUMENTNUMBER", nullable=false, length=15)
	private String intrument_Number;
	
	@Column(name="INSTRUMENTDATE",nullable=false)
	private Date instrument_Date;
	
	@Column(name="BANCO")
	private String Banco;
	
	@Column(name="PAGOREALIZADO")
	private Boolean pago_Ralizado=false;
	
	@ManyToOne
	@Column(name="ID_PAGOS")
	private long id_pagos;
	
	
	
	public Pagos_detalle() {
		
	}
	public Pagos_detalle(String intrument_Number,Date instrument_Date,String Banco,Boolean pago_Ralizado) {
	
		this.intrument_Number=intrument_Number;
		this.instrument_Date=instrument_Date;
		this.Banco=Banco;
		this.pago_Ralizado=pago_Ralizado;
	}

}
