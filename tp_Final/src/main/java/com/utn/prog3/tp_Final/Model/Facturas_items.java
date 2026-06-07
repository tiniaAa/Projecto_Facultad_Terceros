package com.utn.prog3.tp_Final.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="FACTURAS_ITEMS")
@Getter
@Setter
public class Facturas_items {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ID_ITEMS")
	private Long id;
	
	@Column(name="MONTO", nullable=false)
	private Double monto;
	
	@Column(name="CANTIDAD",nullable=false)
	private Double cantidad;
	
	@Column(name="ID_FACTURA",nullable=false)
	@ManyToOne
    @JoinColumn(name = "id_factura")
	private Long id_factura;
	
	@Column(name="DETALLE")
	private String detalle;
	

	public Facturas_items() {
		
	}
	public Facturas_items(Double monto,Double cantidad,Long id_factura,String detalle) {
		this.monto=monto;
		this.cantidad=cantidad;
		this.id_factura=id_factura;
		this.detalle=detalle;
	}
}
