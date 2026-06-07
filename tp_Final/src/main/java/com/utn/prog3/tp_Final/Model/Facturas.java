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
@Table(name="facturas")
@Getter
@Setter
public class Facturas {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ID_FACTURA")
	private long id;
	
	@Column(name="FECHA_FACTURA",nullable=false)
	private Date fecha_factura;
	
	@ManyToOne
	@Column(name="ID_TERCERO", nullable=false)
	private Long id_tercero;
	
	@Column(name="NUMERO")
	private int numero;
	
	public Facturas() {
		
	}
	public Facturas(Date fecha_factura,Long id_tercero,int numero) {
			this.fecha_factura=fecha_factura;
			this.id_tercero=id_tercero;
			this.numero=numero;
		}
}
