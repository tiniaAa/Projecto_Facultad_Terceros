package com.utn.prog3.tp_Final.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="pagos")
public class Pagos {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "id_tercero")
    private Terceros tercero;
	
	@Column(name="FECHA_PAGO",nullable=false)
	private Date fecha_pago;
	
	@Column(name="MONTO_PAGO")
	private Double monto_pago;
	
	@Column(name="MODO_PAGO",nullable=false)
	private String modo_pago;
	
	@OneToMany(mappedBy = "pago", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pagos_detalle> detalles = new ArrayList<>();
	public Pagos() {
		
	}
	public Pagos(Terceros tercero,Date fecha_pago,Double monto_pago,String modo_pago) {
			this.tercero=tercero;
			this.fecha_pago=fecha_pago;
			this.monto_pago=monto_pago;
			this.modo_pago=modo_pago;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Terceros getTercero() {
		return tercero;
	}
	public void setTercero(Terceros tercero) {
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
	public List<Pagos_detalle> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<Pagos_detalle> detalles) {
		this.detalles = detalles;
	}
	
}
