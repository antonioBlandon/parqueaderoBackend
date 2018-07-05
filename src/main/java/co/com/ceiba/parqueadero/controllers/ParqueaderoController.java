package co.com.ceiba.parqueadero.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.parqueadero.dao.ParqueaderoRepository;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.util.RestResponse;

@RestController
public class ParqueaderoController {
	
	@Autowired
	protected ParqueaderoRepository parqueaderoRepository;
	
	protected ObjectMapper mapper;
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/addConfigParking", method = RequestMethod.POST)
	public RestResponse saveOrUpdate(@RequestBody String userJson)
		throws JsonMappingException, IOException {
		
		this.mapper = new ObjectMapper();
		this.parqueaderoRepository.save(this.mapper.readValue(userJson, Parqueadero.class));
		return new RestResponse(HttpStatus.OK.value(), "Operacion exitosa");
		
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/getDataParking", method = RequestMethod.GET)
	public Parqueadero getDataParking() {
		return this.parqueaderoRepository.findById((long)1).get();
	}

}
