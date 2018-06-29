package co.com.ceiba.parqueadero.dao;

import org.springframework.data.repository.CrudRepository;
import co.com.ceiba.parqueadero.model.Vehiculo;

public interface VigilanteRepository extends CrudRepository<Vehiculo, Long>{

}
