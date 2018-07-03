package co.com.ceiba.parqueadero.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.service.VehiculoService;

@RestController
public class VehiculoController {
	
	@Autowired
	VehiculoService vehiculoService;
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/getAllVehicles", method = RequestMethod.GET)
	public List<Vehiculo> getAllVehicles() {
		return this.vehiculoService.getVehicles();
	}

}
