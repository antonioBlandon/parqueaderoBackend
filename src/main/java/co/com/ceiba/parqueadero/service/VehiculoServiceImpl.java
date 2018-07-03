package co.com.ceiba.parqueadero.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.model.Vehiculo;

@Service
public class VehiculoServiceImpl implements VehiculoService {

	@Autowired
	VehiculoRepository vehiculoRepository;
	
	@Override
	public Vehiculo saveOrUpdate(Vehiculo vehiculo) {
		return this.vehiculoRepository.save(vehiculo);
	}

	@Override
	public void deleteVehiculo(Long id) {
		this.vehiculoRepository.deleteById(id);
	}

	@Override
	public List<Vehiculo> getVehicles() {
		List<Vehiculo> listVehicles = new ArrayList<>();
		this.vehiculoRepository.findAll().forEach(vehicle -> listVehicles.add(vehicle));
		return listVehicles;
	}

	@Override
	public Optional<Vehiculo> getVehicleById(long vehicleId) {
		Optional<Vehiculo> optional = vehiculoRepository.findById(vehicleId);
		return optional;
	}

}
