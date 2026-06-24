package com.utn.prog3.tp_Final.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="pagos_detalle")

public class Pagos_detalle {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="INSTRUMENTNUMBER", nullable=false, length=15)
	private String instrument_Number;
	
	@Column(name="INSTRUMENTDATE",nullable=false)
	private Date instrument_Date;
	
	@Column(name="BANCO")
	private String Banco;
	
	@Column(name="PAGOREALIZADO")
	private Boolean pago_Realizado=false;

	@ManyToOne
    @JoinColumn(name = "ID_PAGOS")
	private Pagos pago;
	
	
	
	public Pagos_detalle() {
		
	}
	public Pagos_detalle(String intrument_Number,Date instrument_Date,String Banco,Boolean pago_Ralizado,Pagos pago) {
	
		this.instrument_Number=intrument_Number;
		this.instrument_Date=instrument_Date;
		this.Banco=Banco;
		this.pago_Realizado=pago_Ralizado;
		this.pago=pago;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIntrument_Number() {
		return instrument_Number;
	}
	public void setIntrument_Number(String intrument_Number) {
		this.instrument_Number = intrument_Number;
	}
	public Date getInstrument_Date() {
		return instrument_Date;
	}
	public void setInstrument_Date(Date instrument_Date) {
		this.instrument_Date = instrument_Date;
	}
	public String getBanco() {
		return Banco;
	}
	public void setBanco(String banco) {
		Banco = banco;
	}
	public Boolean getPago_Ralizado() {
		return pago_Realizado;
	}
	public void setPago_Ralizado(Boolean pago_Ralizado) {
		this.pago_Realizado = pago_Ralizado;
	}
	public Pagos getPago() {
		return pago;
	}
	public void setPago(Pagos pago) {
		this.pago = pago;
	}
	

}
