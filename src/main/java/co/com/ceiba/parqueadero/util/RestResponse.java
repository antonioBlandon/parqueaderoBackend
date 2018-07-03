package co.com.ceiba.parqueadero.util;

import co.com.ceiba.parqueadero.model.Vehiculo;

public class RestResponse {
	
	private Integer responseCode;
	private String message;
	private Vehiculo vehiculo;
	
	public RestResponse(Integer responseCode) {
		super();
		this.responseCode = responseCode;
	}
	
	public RestResponse(Integer responseCode, String message) {
		super();
		this.responseCode = responseCode;
		this.message = message;
	}
	
	public RestResponse(Integer responseCode, String message, Vehiculo vehiculo) {
		super();
		this.responseCode = responseCode;
		this.message = message;
		this.vehiculo = vehiculo;
	}
	
	public Integer getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

}
