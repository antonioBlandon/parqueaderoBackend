package co.com.ceiba.parqueadero.dao;

import org.springframework.data.repository.CrudRepository;

import co.com.ceiba.parqueadero.model.Registro;

public interface RegistroRepository extends CrudRepository<Registro, Long> {
	
	

}
