package com.utn.prog3.tp_Final.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="FACTURAS_ITEMS")
public class Facturas_items {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ID_ITEMS")
	private Long id;
	
	@Column(name="MONTO", nullable=false)
	private Double monto;
	
	@Column(name="CANTIDAD",nullable=false)
	private Double cantidad;
	
	@ManyToOne
    @JoinColumn(name = "id_factura")
	private Facturas factura;
	
	@Column(name="DETALLE")
	private String detalle;
	

	public Facturas_items() {
		
	}
	public Facturas_items(Double monto,Double cantidad,Facturas factura,String detalle) {
		this.monto=monto;
		this.cantidad=cantidad;
		this.factura=factura;
		this.detalle=detalle;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Facturas getFactura() {
		return factura;
	}
	public void setFactura(Facturas factura) {
		this.factura = factura;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
}
