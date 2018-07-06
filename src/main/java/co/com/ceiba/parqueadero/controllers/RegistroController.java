package co.com.ceiba.parqueadero.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.model.Registro;

@RestController
public class RegistroController {
	
	@Autowired
	protected RegistroRepository registroRepository;
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/getAllRegisters", method = RequestMethod.GET)
	public List<Registro> getAllVehicles() {
		List<Registro> listRegister = new ArrayList<>();
		registroRepository.findAll().forEach(listRegister::add);
		return listRegister;
	}

}
