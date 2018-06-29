package co.com.ceiba.parqueadero.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

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

import co.com.ceiba.parqueadero.domain.VigilanteImpl;
import co.com.ceiba.parqueadero.model.Parqueadero;
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
			this.vehiculoService.saveOrUpdate(vehiculo);
			return new RestResponse(HttpStatus.OK.value(), "El vehiculo ingreso exitosamente");
		}else {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), messageResult);	
		}
		
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
