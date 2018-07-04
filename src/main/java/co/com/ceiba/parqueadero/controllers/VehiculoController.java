package co.com.ceiba.parqueadero.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.model.Vehiculo;

@RestController
public class VehiculoController {
	
	@Autowired
	VehiculoRepository vehiculoRepository;
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/getAllVehicles", method = RequestMethod.GET)
	public List<Vehiculo> getAllVehicles() {
		List<Vehiculo> listVehicles = new ArrayList<>();
		vehiculoRepository.findAll().forEach(e -> listVehicles.add(e));
		return listVehicles;
	}

}
