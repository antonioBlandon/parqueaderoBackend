package co.com.ceiba.parqueadero.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dao.VigilanteRepository;
import co.com.ceiba.parqueadero.model.Vehiculo;

@Service
public class VehiculoServiceImpl implements VehiculoService {

	@Autowired
	VigilanteRepository vigilanteRepository;
	
	@Override
	public Vehiculo saveOrUpdate(Vehiculo vehiculo) {
		return this.vigilanteRepository.save(vehiculo);
	}

	@Override
	public void deleteVehiculo(Long id) {
		this.vigilanteRepository.deleteById(id);
	}

	@Override
	public List<Vehiculo> getVehicles() {
		List<Vehiculo> listVehicles = new ArrayList<>();
		this.vigilanteRepository.findAll().forEach(vehicle -> listVehicles.add(vehicle));
		return listVehicles;
	}

}
