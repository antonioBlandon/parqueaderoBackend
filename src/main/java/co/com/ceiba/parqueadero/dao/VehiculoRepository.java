package co.com.ceiba.parqueadero.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import co.com.ceiba.parqueadero.model.Vehiculo;

public interface VehiculoRepository extends CrudRepository<Vehiculo, Long>{
	
	Optional<Vehiculo> findByPlaca(String placa);

}
