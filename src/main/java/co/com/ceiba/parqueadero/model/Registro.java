package co.com.ceiba.parqueadero.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "registro")
@Access(AccessType.FIELD)
public class Registro extends ParentEntity {
	
	private static final long serialVersionUID = -6913999300281753414L;

	@Column(name = "placa", nullable = false, length = 15)
	private String placa;
	
	@Column(name = "fecha_ingreso", nullable = false, length = 100)
    private Long fechaIngreso;
	
	@Column(name = "cilindraje", nullable = true, length = 10)
    private int cilindraje;
	
	@Column(name = "fecha_salida", nullable = false, length = 100)
	private Long fechaSalida;
	
	@Column(name = "valor_pagado", nullable = false, length = 100)
	private Long valorPagado;
	
	@Column(name = "dias_en_parqueadero", nullable = false, length = 100)
	private Long diasEnParqueadero;
	
	@Column(name = "horas_en_parqueadero", nullable = false, length = 100)
	private Long horasEnParqueadero;
	
	public Registro() {}
	
	public Registro(String placa, Long fechaIngreso, int cilindraje) {
		super();
		this.placa = placa;
		this.fechaIngreso = fechaIngreso;
		this.cilindraje = cilindraje;
	}

	public Registro(String placa, Long fechaIngreso, int cilindraje, Long fechaSalida, Long valorPagado,
			Long diasEnParqueadero, Long horasEnParqueadero) {
		super();
		this.placa = placa;
		this.fechaIngreso = fechaIngreso;
		this.cilindraje = cilindraje;
		this.fechaSalida = fechaSalida;
		this.valorPagado = valorPagado;
		this.diasEnParqueadero = diasEnParqueadero;
		this.horasEnParqueadero = horasEnParqueadero;
	}
	
	public int getCilindraje() {
		return cilindraje;
	}
	
	public Long getHorasEnParqueadero() {
		return horasEnParqueadero;
	}
	
	public Long getDiasEnParqueadero() {
		return diasEnParqueadero;
	}

	public String getPlaca() {
		return placa;
	}

	public Long getFechaIngreso() {
		return fechaIngreso;
	}

	public Long getFechaSalida() {
		return fechaSalida;
	}

	public Long getValorPagado() {
		return valorPagado;
	}

	public void setFechaSalida(Long fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public void setValorPagado(Long valorPagado) {
		this.valorPagado = valorPagado;
	}

	public void setDiasEnParqueadero(Long diasEnParqueadero) {
		this.diasEnParqueadero = diasEnParqueadero;
	}

	public void setHorasEnParqueadero(Long horasEnParqueadero) {
		this.horasEnParqueadero = horasEnParqueadero;
	}
    
}
