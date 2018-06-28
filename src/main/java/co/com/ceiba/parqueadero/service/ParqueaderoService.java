package co.com.ceiba.parqueadero.service;

import co.com.ceiba.parqueadero.model.Parqueadero;

public interface ParqueaderoService {
	
	Parqueadero save(Parqueadero parqueadero);

	Parqueadero findById();

	void deleteParqueadero(Long id);

}
