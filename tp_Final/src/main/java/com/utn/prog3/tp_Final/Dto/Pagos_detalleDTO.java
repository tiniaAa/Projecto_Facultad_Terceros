package com.utn.prog3.tp_Final.Dto;

import java.util.Date;

public class Pagos_detalleDTO {
	
	private long id;
	private String instrument_Number;
	private Date instrument_Date;
	private String Banco;
	private Boolean pago_Realizado = false;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getInstrument_Number() {
		return instrument_Number;
	}
	public void setInstrument_Number(String instrument_Number) {
		this.instrument_Number = instrument_Number;
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
	public Boolean getPago_Realizado() {
		return pago_Realizado;
	}
	public void setPago_Realizado(Boolean pago_Realizado) {
		this.pago_Realizado = pago_Realizado;
	}
	
	
}
