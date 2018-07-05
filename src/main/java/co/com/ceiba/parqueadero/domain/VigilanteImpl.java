package co.com.ceiba.parqueadero.domain;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.dao.ParqueaderoRepository;
import co.com.ceiba.parqueadero.dao.RegistroRepository;
import co.com.ceiba.parqueadero.dao.VehiculoRepository;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.util.RestResponse;

@Service
public class VigilanteImpl implements Vigilante {
	
	@Autowired
	protected ParqueaderoRepository parqueaderoRepository;
	
	@Autowired
	protected VehiculoRepository vehiculoRepository;
	
	@Autowired
	protected RegistroRepository registroRepository;
	
	private long UNIQUE_ID_PARKING = 1;

    private VigilanteImpl() {
    }
    
    @Override
	public RestResponse addVehicle(Vehiculo vehiculo) {
    	
    	vehiculo.setFechaIngreso(Calendar.getInstance().getTimeInMillis());
		Parqueadero parqueadero = this.parqueaderoRepository.findById(UNIQUE_ID_PARKING).get();
		
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

    @Override
    public boolean validarCantidadCarros(int cantidadCarrosActual, int topeCarros) {
        return (cantidadCarrosActual + 1) <= topeCarros;
    }

    @Override
    public boolean validarCantidadMotos(int cantidadMotosActual, int topeMotos) {
        return (cantidadMotosActual + 1) <= topeMotos;
    }

    @Override
    public boolean validarPlaca(String placa, long fechaIngreso) {

        String INIT_LETTER_RESTRICTION = "A";
    	String primeraLetra = placa.substring(0, 1);
    	
        if (primeraLetra.equals(INIT_LETTER_RESTRICTION)) {
            // Dias validos
            int lunes = 2;
            int domingo = 1;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(fechaIngreso);
            int diaIngresoDeLaSemana = calendar.get(Calendar.DAY_OF_WEEK);
            return ((diaIngresoDeLaSemana == domingo) || (diaIngresoDeLaSemana == lunes));

        }
        
        return true;

    }

    @Override
    public long calcularTiempoVehiculoParqueadero(long fechaIngreso, long fechaSalida) {
    	int SECONDS_IN_HOUR = 3600;
        long tiempo = fechaSalida - fechaIngreso;
        double tiempoEnSegundos = (float) tiempo / 1_000;
        return (Double.valueOf(Math.ceil(tiempoEnSegundos / SECONDS_IN_HOUR))).longValue();
    }

    @Override
    public long cobrarParqueadero(Registro registro, Parqueadero parqueadero) {

        long valor = 0;
        if (registro.getCilindraje() != 0) {
            valor = (registro.getDiasEnParqueadero() * parqueadero.getValorDiaMoto())
                    + (registro.getHorasEnParqueadero() * parqueadero.getValorHoraMotos());
            if (registro.getCilindraje() > parqueadero.getTopeCilindraje()) {
                valor = valor + parqueadero.getAdicionCilindraje();
            }
        } else {
            valor = (registro.getDiasEnParqueadero() * parqueadero.getValorDiaCarro())
                    + (registro.getHorasEnParqueadero() * parqueadero.getValorHoraCarros());
        }
        return valor;

    }

    @Override
    public long[] calcularDiasHoras(long horas) {
    	
    	int MAX_HOURS_TO_DAY = 9;
    	int HOURS_IN_ONE_DAY = 24;
        long dias = 0;
        long[] diasHoras = new long[2];
        
        while (horas >= 0) {

            if (horas > MAX_HOURS_TO_DAY) {
                dias++;
                horas = horas - HOURS_IN_ONE_DAY;
            } else {
                break;
            }

        }
        
        if (horas < 0) {
            horas = 0;
        }
        
        diasHoras[0] = dias;
        diasHoras[1] = horas;
        
        return diasHoras;
    
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
        long tiempoParqueadero = calcularTiempoVehiculoParqueadero(vehiculo.getFechaIngreso(), registro.getFechaSalida());
        long[] diasHoras = calcularDiasHoras(tiempoParqueadero);
        registro.setDiasEnParqueadero(diasHoras[0]);
        registro.setHorasEnParqueadero(diasHoras[1]);
        registro.setValorPagado(cobrarParqueadero(registro, parqueadero));
        return registro;
    }
	
	String validate(Vehiculo vehiculo, Parqueadero parqueadero) {
		
		if(!validarPlaca(vehiculo.getPlaca(), vehiculo.getFechaIngreso())) {
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
			return validarCantidadCarros(parqueadero.getCantidadActualCarro(), parqueadero.getTopeCarros());
		}else {
			return validarCantidadMotos(parqueadero.getCantidadActualMoto(), parqueadero.getTopeMotos());
		}
	}

	@Override
	public RestResponse outVehicle(Long id) {
		
		if(id != null) {
			
			Optional<Vehiculo> optionalVehicle = this.vehiculoRepository.findById(id);
			
			if( optionalVehicle.isPresent() ) {
				
				Parqueadero parqueadero = this.parqueaderoRepository.findById(UNIQUE_ID_PARKING).get();
				Vehiculo vehiculo = optionalVehicle.get();
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
    
}
