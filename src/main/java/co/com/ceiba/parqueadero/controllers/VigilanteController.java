package co.com.ceiba.parqueadero.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.parqueadero.dao.ParqueaderoRepository;
import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.domain.Vigilante;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.util.RestResponse;

@RestController
public class VigilanteController {
	
	@Autowired
	protected ParqueaderoRepository parqueaderoRepository;
	
	@Autowired
	protected VehiculoRepository vehiculoRepository;
	
	@Autowired
	protected RegistroRepository registroRepository;
	
	@Autowired
	protected Vigilante vigilante;
	
	protected ObjectMapper mapper;
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
	public RestResponse addVehicle(@RequestBody String vehicleJson) throws JsonMappingException, IOException {
		
		this.mapper = new ObjectMapper();
		
		Vehiculo vehiculo = this.mapper.readValue(vehicleJson, Vehiculo.class);
		vehiculo.setFechaIngreso(Calendar.getInstance().getTimeInMillis());
		Parqueadero parqueadero = this.parqueaderoRepository.findById((long) 1).get();
		
		String messageResult = validate(vehiculo, parqueadero);
		if( messageResult == null) {
			parqueadero = actualizarParqueadero(vehiculo.getCilindraje() == 0, true, parqueadero);
			this.vehiculoRepository.save(vehiculo);
			this.parqueaderoRepository.save(parqueadero);
			return new RestResponse(HttpStatus.OK.value(), "El vehiculo ingreso exitosamente", vehiculo);
		}else {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), messageResult);	
		}
		
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/outVehicle", method = RequestMethod.POST)
	public RestResponse outVehicle(@RequestBody String vehicleJson) throws JsonMappingException, IOException {
		
		this.mapper = new ObjectMapper();
		
		Parqueadero parqueadero = this.parqueaderoRepository.findById((long) 1).get();
		Vehiculo vehiculo = this.mapper.readValue(vehicleJson, Vehiculo.class);
		
		if(vehiculo != null) {
			
			Optional<Vehiculo> optionalVehicle = this.vehiculoRepository.findById(vehiculo.getId());
			if( optionalVehicle.isPresent() ) {
				Registro registro = calcularCobro(vehiculo, parqueadero);
				parqueadero = actualizarParqueadero(vehiculo.getCilindraje() == 0, false, parqueadero);
				this.vehiculoRepository.deleteById(vehiculo.getId());
				vehiculo.setId(null);
				this.registroRepository.save(registro);
				this.parqueaderoRepository.save(parqueadero);
				return new RestResponse(HttpStatus.OK.value(), "El vehiculo salio exitosamente", registro);
			}else {
				return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "La placa no existe o el vehiculo ya salio del parqueadero");
			}
			
		}else {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Error en el vehiculo seleccionado");	
		}
		
	}
	
	public Parqueadero actualizarParqueadero(boolean isCar,boolean ingresaVehiculo, Parqueadero parqueadero) {
		if(isCar) {
			if(ingresaVehiculo) {
				parqueadero.setCantidadActualCarro(parqueadero.getCantidadActualCarro() + 1);
			}else {
				parqueadero.setCantidadActualCarro(parqueadero.getCantidadActualCarro() - 1);
			}
		}else {
			if(ingresaVehiculo) {
				parqueadero.setCantidadActualMoto(parqueadero.getCantidadActualMoto() + 1 );
			}else {
				parqueadero.setCantidadActualMoto(parqueadero.getCantidadActualMoto() - 1 );
			}
		}
		return parqueadero;
	}
	
	public Registro calcularCobro(Vehiculo vehiculo, Parqueadero parqueadero) {
        Registro registro = new Registro(vehiculo.getPlaca(), vehiculo.getFechaIngreso(), vehiculo.getCilindraje());
        registro.setFechaSalida(Calendar.getInstance().getTimeInMillis());
        long tiempoParqueadero = vigilante.calcularTiempoVehiculoParqueadero(vehiculo.getFechaIngreso(), registro.getFechaSalida());
        long[] diasHoras = vigilante.calcularDiasHoras(tiempoParqueadero);
        registro.setDiasEnParqueadero(diasHoras[0]);
        registro.setHorasEnParqueadero(diasHoras[1]);
        registro.setValorPagado(vigilante.cobrarParqueadero(registro, parqueadero));
        return registro;
    }
	
	String validate(Vehiculo vehiculo, Parqueadero parqueadero) {
		
		if(!vigilante.validarPlaca(vehiculo.getPlaca(), vehiculo.getFechaIngreso())) {
			return "La placa que esta intentando ingresar no es valida";
		}else if (this.vehiculoRepository.findByPlaca(vehiculo.getPlaca()).isPresent()) {
			return "Existe un vehiculo con la misma placa dentro del parqueadero";
		}else if (!validarCupo(vehiculo, parqueadero)){
			return "No hay cupo para el vehiculo. Intente mas tarde por favor";
		}
		return null;
	}
	
	public boolean validarCupo(Vehiculo vehiculo, Parqueadero parqueadero) {
		if (vehiculo.getCilindraje() == 0) {
			return vigilante.validarCantidadCarros(parqueadero.getCantidadActualCarro());
		}else {
			return vigilante.validarCantidadMotos(parqueadero.getCantidadActualMoto());
		}
	}

}
