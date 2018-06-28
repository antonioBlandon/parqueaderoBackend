package co.com.ceiba.parqueadero.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.service.ParqueaderoService;
import co.com.ceiba.parqueadero.util.RestResponse;

@RestController
public class ParqueaderoController {
	
	@Autowired
	protected ParqueaderoService parqueaderoService;
	
	protected ObjectMapper mapper;
	
	@RequestMapping(value = "/addConfigParking", method = RequestMethod.POST)
	public RestResponse saveOrUpdate(@RequestBody String userJson)
		throws JsonParseException, JsonMappingException, IOException {
		
		this.mapper = new ObjectMapper();
		Parqueadero parqueadero = this.mapper.readValue(userJson, Parqueadero.class);
		if(!this.validate(parqueadero)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "El camop obligatorio no fue diligenciado");
		}
		this.parqueaderoService.save(parqueadero);
		return new RestResponse(HttpStatus.OK.value(), "Operacion exitosa");
	}
	
	@RequestMapping(value = "/getDataParking", method = RequestMethod.GET)
	public Parqueadero getDataParking() {
		return this.parqueaderoService.findById();
	}
	
	private boolean validate ( Parqueadero parqueadero) {
		if (parqueadero.getAdicionCilindraje() == null) {
			return false;
		}else if (parqueadero.getTopeCarros() == null) {
			return false;
		}else if (parqueadero.getTopeCilindraje() == null) {
			return false;
		}else if (parqueadero.getTopeMotos() == null) {
			return false;
		}else if (parqueadero.getValorDiaCarro() == null) {
			return false;
		}else if (parqueadero.getValorDiaMoto() == null) {
			return false;
		}else if (parqueadero.getValorHoraCarros() == null) {
			return false;
		}else if (parqueadero.getValorHoraMotos() == null) {
			return false;
		}else {
			return true;
		}
	}

}
