package co.com.ceiba.parqueadero.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import co.com.ceiba.parqueadero.model.Vehiculo;

public interface VehiculoService {

	Vehiculo saveOrUpdate(Vehiculo vehiculo);
	
	void deleteVehiculo(Long id);
	
	List<Vehiculo> getVehicles();
	
	Optional<Vehiculo> getVehicleById(long vehicleId);
	
	@Query(value = "SELECT * FROM parqueaderodb.vehiculo WHERE fecha_salida IS NULL",nativeQuery = true)
    List<Vehiculo> getvehiclesInParking();
	
}
