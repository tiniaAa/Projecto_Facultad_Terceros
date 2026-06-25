package com.utn.prog3.tp_Final.Dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PagosDTO {
	
	private Long id;
	private TercerosDTO tercero;
	private Date fecha_pago;
	private Double monto_pago;
	private String modo_pago;	
	

	private List<Pagos_detalleDTO> detalles = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TercerosDTO getTercero() {
		return tercero;
	}

	public void setTercero(TercerosDTO tercero) {
		this.tercero = tercero;
	}

	public Date getFecha_pago() {
		return fecha_pago;
	}

	public void setFecha_pago(Date fecha_pago) {
		this.fecha_pago = fecha_pago;
	}

	public Double getMonto_pago() {
		return monto_pago;
	}

	public void setMonto_pago(Double monto_pago) {
		this.monto_pago = monto_pago;
	}

	public String getModo_pago() {
		return modo_pago;
	}

	public void setModo_pago(String modo_pago) {
		this.modo_pago = modo_pago;
	}

	public List<Pagos_detalleDTO> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<Pagos_detalleDTO> detalles) {
		this.detalles = detalles;
	}

	
}
