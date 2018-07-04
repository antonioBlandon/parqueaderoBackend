package co.com.ceiba.parqueadero.util;

public class RestResponse {
	
	private Integer responseCode;
	private String message;
	private Object objectReturn;
	
	public RestResponse(Integer responseCode) {
		super();
		this.responseCode = responseCode;
	}
	
	public RestResponse(Integer responseCode, String message) {
		super();
		this.responseCode = responseCode;
		this.message = message;
	}
	
	public RestResponse(Integer responseCode, String message, Object objectReturn) {
		super();
		this.responseCode = responseCode;
		this.message = message;
		this.objectReturn = objectReturn;
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

	public Object getObjectReturn() {
		return objectReturn;
	}

	public void setVehiculo(Object objectReturn) {
		this.objectReturn = objectReturn;
	}

}
