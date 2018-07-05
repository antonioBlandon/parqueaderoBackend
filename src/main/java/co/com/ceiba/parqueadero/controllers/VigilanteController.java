package co.com.ceiba.parqueadero.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.parqueadero.domain.Vigilante;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.util.RestResponse;

@RestController
public class VigilanteController {
	
	@Autowired
	protected Vigilante vigilante;
	
	protected ObjectMapper mapper;
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
	public RestResponse addVehicle(@RequestBody String vehicleJson) throws JsonMappingException, IOException {
		
		this.mapper = new ObjectMapper();
		return vigilante.addVehicle(this.mapper.readValue(vehicleJson, Vehiculo.class));
		
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/outVehicle", method = RequestMethod.POST)
	public RestResponse outVehicle(@RequestBody String vehicleJson) throws JsonMappingException, IOException {
		
		this.mapper = new ObjectMapper();
		return vigilante.outVehicle(this.mapper.readValue(vehicleJson, Vehiculo.class).getId());
			
	}

}
