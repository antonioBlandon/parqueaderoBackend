package co.com.ceiba.parqueadero.service;

import java.util.List;

import co.com.ceiba.parqueadero.model.Vehiculo;

public interface VehiculoService {

	Vehiculo saveOrUpdate(Vehiculo vehiculo);
	
	void deleteVehiculo(Long id);
	
	List<Vehiculo> getVehicles();
	
}
