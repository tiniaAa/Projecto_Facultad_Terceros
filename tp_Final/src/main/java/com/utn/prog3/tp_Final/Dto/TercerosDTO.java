package com.utn.prog3.tp_Final.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TercerosDTO {
	
	private Long id;
	private String nombre;
	private String cuilt;
	private String sitiva;
	private String direccion;
	private String localidad;
	private String provincia;
	private String telefonos;
	private Double saldo_apertura;
	private String tipo_saldo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCuilt() {
		return cuilt;
	}
	public void setCuilt(String cuilt) {
		this.cuilt = cuilt;
	}
	public String getSitiva() {
		return sitiva;
	}
	public void setSitiva(String sitiva) {
		this.sitiva = sitiva;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTelefonos() {
		return telefonos;
	}
	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}
	public Double getSaldo_apertura() {
		return saldo_apertura;
	}
	public void setSaldo_apertura(Double saldo_apertura) {
		this.saldo_apertura = saldo_apertura;
	}
	public String getTipo_saldo() {
		return tipo_saldo;
	}
	public void setTipo_saldo(String tipo_saldo) {
		this.tipo_saldo = tipo_saldo;
	}
	
	
}
