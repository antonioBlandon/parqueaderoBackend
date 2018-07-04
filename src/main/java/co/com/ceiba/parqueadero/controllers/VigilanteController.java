package co.com.ceiba.parqueadero.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.domain.Vigilante;
import co.com.ceiba.parqueadero.domain.VigilanteImpl;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.service.ParqueaderoService;
import co.com.ceiba.parqueadero.service.VehiculoService;
import co.com.ceiba.parqueadero.util.RestResponse;

@RestController
public class VigilanteController {
	
	@Autowired
	protected ParqueaderoService parqueaderoService;
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected RegistroRepository registroRepository;
	
	protected ObjectMapper mapper;
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
	public RestResponse addVehicle(@RequestBody String vehicleJson) throws JsonParseException, JsonMappingException, IOException {
		
		this.mapper = new ObjectMapper();
		
		Vehiculo vehiculo = this.mapper.readValue(vehicleJson, Vehiculo.class);
		vehiculo.setFechaIngreso(Calendar.getInstance().getTimeInMillis());
		Parqueadero parqueadero = this.parqueaderoService.findById();
		List<Vehiculo> listVehicles = this.vehiculoService.getVehicles();
		
		String messageResult = validate(vehiculo, listVehicles, parqueadero);
		if( messageResult == null) {
			parqueadero = actualizarParqueadero(vehiculo.getCilindraje() == 0, true, parqueadero);
			this.vehiculoService.saveOrUpdate(vehiculo);
			this.parqueaderoService.save(parqueadero);
			RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "El vehiculo ingreso exitosamente", vehiculo);
			return restResponse;
		}else {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), messageResult);	
		}
		
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/outVehicle", method = RequestMethod.POST)
	public RestResponse outVehicle(@RequestBody String vehicleJson) throws JsonParseException, JsonMappingException, IOException {
		
		this.mapper = new ObjectMapper();
		
		Parqueadero parqueadero = this.parqueaderoService.findById();
		Vehiculo vehiculo = this.mapper.readValue(vehicleJson, Vehiculo.class);
		
		if(vehiculo != null) {
			
			Optional<Vehiculo> optionalVehicle = this.vehiculoService.getVehicleById(vehiculo.getId());
			if( optionalVehicle.isPresent() ) {
				Registro registro = calcularCobro(vehiculo, parqueadero);
				parqueadero = actualizarParqueadero(vehiculo.getCilindraje() == 0, false, parqueadero);
				this.vehiculoService.deleteVehiculo(vehiculo.getId());
				vehiculo.setId(null);
				this.registroRepository.save(registro);
				this.parqueaderoService.save(parqueadero);
				RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "El vehiculo salio exitosamente", registro);
				return restResponse;
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
        Vigilante vigilante = VigilanteImpl.getInstance();
        Registro registro = new Registro(vehiculo.getPlaca(), vehiculo.getFechaIngreso(), vehiculo.getCilindraje());
        registro.setFechaSalida(Calendar.getInstance().getTimeInMillis());
        long tiempoParqueadero = vigilante.calcularTiempoVehiculoParqueadero(vehiculo.getFechaIngreso(), registro.getFechaSalida());
        long[] diasHoras = vigilante.calcularDiasHoras(tiempoParqueadero);
        registro.setDiasEnParqueadero(diasHoras[0]);
        registro.setHorasEnParqueadero(diasHoras[1]);
        registro.setValorPagado(vigilante.cobrarParqueadero(registro, parqueadero));
        return registro;
    }
	
	String validate(Vehiculo vehiculo, List<Vehiculo> listVehicles, Parqueadero parqueadero) {
		
		if(!VigilanteImpl.getInstance().validarPlaca(vehiculo.getPlaca(), vehiculo.getFechaIngreso())) {
			return "La placa que esta intentando ingresar no es valida";
		}else if (!validarPlacaExiste(listVehicles, vehiculo)) {
			return "Existe un vehiculo con la misma placa dentro del parqueadero";
		}else if (!validarCupo(vehiculo, parqueadero)){
			return "No hay cupo para el vehiculo. Intente mas tarde por favor";
		}
		return null;
	}
	
	public boolean validarCupo(Vehiculo vehiculo, Parqueadero parqueadero) {
		if (vehiculo.getCilindraje() == 0) {
			return VigilanteImpl.getInstance().validarCantidadCarros(parqueadero.getCantidadActualCarro());
		}else {
			return VigilanteImpl.getInstance().validarCantidadMotos(parqueadero.getCantidadActualMoto());
		}
	}
	
	public boolean validarPlacaExiste(List<Vehiculo> listVehiculo, Vehiculo newVehicle) {
        for (Vehiculo vehiculoItem : listVehiculo) {
            if (vehiculoItem.getPlaca().contains(newVehicle.getPlaca())) {
                return false;
            }
        }
        return true;
    }

}
