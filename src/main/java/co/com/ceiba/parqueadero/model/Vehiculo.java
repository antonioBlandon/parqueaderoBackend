package co.com.ceiba.parqueadero.model;

import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vehiculo")
@Access(AccessType.FIELD)
public class Vehiculo extends ParentEntity {

	private static final long serialVersionUID = 4316791769872991095L;
	
	@Column(name = "placa", nullable = false, length = 15)
	private String placa;
	
	@Column(name = "fecha_ingreso", nullable = false, length = 100)
    private Long fechaIngreso;
	
	@Column(name = "cilindraje", nullable = true, length = 10)
    private int cilindraje;
	
	public Vehiculo() {}

	public Vehiculo(String placa, Long fechaIngreso, int cilindraje) {
		super();
		this.placa = placa;
		this.fechaIngreso = fechaIngreso;
		this.cilindraje = cilindraje;
	}
	
	public int getCilindraje() {
		return cilindraje;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPlaca() {
		return placa;
	}

	public Long getFechaIngreso() {
		return fechaIngreso;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setFechaIngreso(Long fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}
    
}
