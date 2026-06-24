package com.utn.prog3.tp_Final.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="terceros")
@Getter
@Setter
public class Terceros {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="ID_TERCEROS", nullable= false)
	private Long id;
	
	@Column(name="NOMBRE", nullable=false)
	private String nombre;
	
	@Column(name="CUITL", nullable=false)
	private String cuilt;
	
	@Column(name="SITIVA", nullable=false)
	private String sitiva;
	
	@Column(name="DIRECCION", nullable=false)
	private String direccion;
	
	@Column(name="LOCALIDAD")
	private String localidad;
	
	@Column(name="PROVINCIA")
	private String provincia;
	
	@Column(name="TELEFONOS")
	private String telefonos;
	
	@Column(name="SALDO_APERTURA")
	private Double saldo_apertura;
	
	@Column(name="TIPO_SALDO")
	private String tipo_saldo;
	
	
	public Terceros() {
		
	}
	public Terceros(String nombre,String cuilt,String sitiva,String direccion,String localidad,String provincia,String telefonos,Double saldo_apertura,String tipo_saldo) {
			this.nombre=nombre;
			this.cuilt=cuilt;
			this.sitiva=sitiva;
			this.direccion=direccion;
			this.localidad=localidad;
			this.provincia=provincia;
			this.telefonos=telefonos;
			this.saldo_apertura=saldo_apertura;
			this.tipo_saldo=tipo_saldo;

		}
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
