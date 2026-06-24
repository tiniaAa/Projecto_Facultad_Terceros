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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="facturas")

public class Facturas {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ID_FACTURA")
	private long id;
	
	@Column(name="FECHA_FACTURA",nullable=false)
	private Date fecha_factura;
	
	@ManyToOne
    @JoinColumn(name = "ID_TERCERO")
	private Terceros tercero;
	
	@Column(name="NUMERO")
	private int numero;
	
	@OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Facturas_items> items = new ArrayList<>();
	public Facturas() {
		
	}
	public Facturas(Date fecha_factura,Terceros id_tercero,int numero) {
			this.fecha_factura=fecha_factura;
			this.tercero=id_tercero;
			this.numero=numero;
	}
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
	public Terceros getTercero() {
		return tercero;
	}
	public void setTercero(Terceros tercero) {
		this.tercero = tercero;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public List<Facturas_items> getItems() {
		return items;
	}
	public void setItems(List<Facturas_items> items) {
		this.items = items;
	}
	
}
