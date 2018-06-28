package co.com.ceiba.parqueadero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dao.ParqueaderoRepository;
import co.com.ceiba.parqueadero.model.Parqueadero;

@Service
public class ParqueaderoServiceImpl implements ParqueaderoService {
	
	@Autowired
	protected ParqueaderoRepository parqueaderoRepository;

	@Override
	public Parqueadero save(Parqueadero parqueadero) {
		return this.parqueaderoRepository.save(parqueadero);
	}

	@Override
	public void deleteParqueadero(Long id) {
		this.parqueaderoRepository.deleteById(id);		
	}

	@Override
	public Parqueadero findById() {
		Long UNIQUE_ID = (long) 1;
		return this.parqueaderoRepository.findById(UNIQUE_ID).get();
	}

}
