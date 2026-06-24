package com.utn.prog3.tp_Final.Dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.utn.prog3.tp_Final.Model.Facturas_items;

public class FacturasDTO {

	private long id;
	private Date fecha_factura;
	private int numero;
	private TercerosDTO tercero;
	private List<Facturas_ItemsDTO> items = new ArrayList<>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getFecha_factura() {
		return fecha_factura;
	}
	public void setFecha_factura(Date fecha_factura) {
		this.fecha_factura = fecha_factura;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public TercerosDTO getTercero() {
		return tercero;
	}
	public void setTercero(TercerosDTO tercero) {
		this.tercero = tercero;
	}
	public List<Facturas_ItemsDTO> getItems() {
		return items;
	}
	public void setItems(List<Facturas_ItemsDTO> items) {
		this.items = items;
	}
	
	
}
