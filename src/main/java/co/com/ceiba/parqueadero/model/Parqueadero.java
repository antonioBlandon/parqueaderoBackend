package co.com.ceiba.parqueadero.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "parqueadero")
public class Parqueadero extends ParentEntity {
	
	private static final long serialVersionUID = 1787413510822598842L;
	
	@Column(name = "valor_hora_carros")
	private Long valorHoraCarros;
	
	@Column(name = "valor_hora_motos")
    private Long valorHoraMotos;
	
	@Column(name = "valor_dia_carro")
    private Long valorDiaCarro;
	
	@Column(name = "valor_dia_moto")
    private Long valorDiaMoto;
    
	@Column(name = "adicion_cilindraje")
	private Long adicionCilindraje;
    
	@Column(name = "tope_cilindraje")
	private Integer topeCilindraje;
    
	@Column(name = "tope_carros")
	private Integer topeCarros;
    
	@Column(name = "tope_motos")
	private Integer topeMotos;
	
	@Column(name = "cantidad_actual_carro")
	private Integer cantidadActualCarro;
    
	@Column(name = "cantidad_actual_moto")
	private Integer cantidadActualMoto;
    
    public Parqueadero() {}

	public Parqueadero(Long valorHoraCarros, Long valorHoraMotos, Long valorDiaCarro, Long valorDiaMoto,
			Long adicionCilindraje, Integer topeCilindraje, Integer topeCarros, Integer topeMotos,
			Integer cantidadActualCarro, Integer cantidadActualMoto) {
		super();
		this.valorHoraCarros = valorHoraCarros;
		this.valorHoraMotos = valorHoraMotos;
		this.valorDiaCarro = valorDiaCarro;
		this.valorDiaMoto = valorDiaMoto;
		this.adicionCilindraje = adicionCilindraje;
		this.topeCilindraje = topeCilindraje;
		this.topeCarros = topeCarros;
		this.topeMotos = topeMotos;
		this.cantidadActualCarro = cantidadActualCarro;
		this.cantidadActualMoto = cantidadActualMoto;
	}

	public Long getValorHoraCarros() {
		return valorHoraCarros;
	}

	public Long getValorHoraMotos() {
		return valorHoraMotos;
	}

	public Long getValorDiaCarro() {
		return valorDiaCarro;
	}

	public Long getValorDiaMoto() {
		return valorDiaMoto;
	}

	public Long getAdicionCilindraje() {
		return adicionCilindraje;
	}

	public Integer getTopeCilindraje() {
		return topeCilindraje;
	}

	public Integer getTopeCarros() {
		return topeCarros;
	}

	public Integer getTopeMotos() {
		return topeMotos;
	}

	public void setValorHoraCarros(Long valorHoraCarros) {
		this.valorHoraCarros = valorHoraCarros;
	}

	public void setValorHoraMotos(Long valorHoraMotos) {
		this.valorHoraMotos = valorHoraMotos;
	}

	public void setValorDiaCarro(Long valorDiaCarro) {
		this.valorDiaCarro = valorDiaCarro;
	}

	public void setValorDiaMoto(Long valorDiaMoto) {
		this.valorDiaMoto = valorDiaMoto;
	}

	public void setAdicionCilindraje(Long adicionCilindraje) {
		this.adicionCilindraje = adicionCilindraje;
	}

	public void setTopeCilindraje(Integer topeCilindraje) {
		this.topeCilindraje = topeCilindraje;
	}

	public void setTopeCarros(Integer topeCarros) {
		this.topeCarros = topeCarros;
	}

	public void setTopeMotos(Integer topeMotos) {
		this.topeMotos = topeMotos;
	}

	public Integer getCantidadActualCarro() {
		return cantidadActualCarro;
	}

	public Integer getCantidadActualMoto() {
		return cantidadActualMoto;
	}

	public void setCantidadActualCarro(Integer cantidadActualCarro) {
		this.cantidadActualCarro = cantidadActualCarro;
	}

	public void setCantidadActualMoto(Integer cantidadActualMoto) {
		this.cantidadActualMoto = cantidadActualMoto;
	}
	
}
