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
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="pagos")
@Getter
@Setter
public class Pagos {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="ID_TERCERO",nullable=false)
	@ManyToOne
    @JoinColumn(name = "id_tercero")
	private Long id_tercero;
	
	@Column(name="FECHA_PAGO",nullable=false)
	private Date fecha_pago;
	
	@Column(name="MONTO_PAGO")
	private Double monto_pago;
	
	@Column(name="MODO_PAGO",nullable=false)
	private String modo_pago;
	
	public Pagos() {
		
	}
	public Pagos(Long id_tercero,Date fecha_pago,Double monto_pago,String modo_pago) {
			this.id_tercero=id_tercero;
			this.fecha_pago=fecha_pago;
			this.monto_pago=monto_pago;
			this.modo_pago=modo_pago;
		}
}
