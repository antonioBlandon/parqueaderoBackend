package co.com.ceiba.parqueadero.domain;

import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.util.RestResponse;

/**
 * @author jose.blandon
 * @since 21/06/2018
 * Se declaran los metodos del dominio*/

public interface Vigilante {
	
	/**
	 * Si se cumplen todas las validaciones se ingresa el vehiculo al parqueadero
	 * 
	 * @param vehiculo, vehiculo que se va ingresar al parqueadero, debe tener cilindraje y placa
	 * 
	 * @return Restresponse, para indicar al controlador que debe retornar*/
	RestResponse addVehicle(Vehiculo vehiculo);
	
	/**
	 * Se realizan los calculos de la estadía del vehículo en el parqueadero y por ultimo saca el vehiculo
	 * del parqueadero
	 * 
	 * @param id, identificador del vehiculo que se desea sacar del parqueadero
	 * 
	 * @return Restresponse, para indicar al controlador que debe retornar*/
	RestResponse outVehicle(Long id);

	/**
	 * Valida que el parqueadero aun tenga cupos para carros
	 * 
	 * @param cantidadCarrosActual, entero con la cantidad de carros que tiene el parqueadero
	 * @param topeCarros, maximo de carros que recibe el parqueadero
	 * 
	 * @return boolean, true si hay cupo y false en caso contrario
	 */
    boolean validarCantidadCarros(int cantidadCarrosActual, int topeCarros);

    /**
	 * Valida que el parqueadero aun tenga cupos para motos
	 * 
	 * @param cantidadMotosActual, entero con la cantidad de motos que tiene el parqueadero
	 * @param topeMotos, maximo de motos que recibe el parqueadero
	 * 
	 * @return boolean, true si hay cupo y false en caso contrario
	 */
    boolean validarCantidadMotos(int cantidadMotosActual, int topeMotos);

    /**
     *Valida mediante la placa del vehiculo, si este puede ingresar o no. Las placas que inician por la letra "A" solo pueden ingresar 
     *al parqueadero los días Domingo y Lunes.
     *
     *@param placa, String con la placa del vehiculo que esta intentando ingresar.
     *@param fechaIngreso, long con la fecha actual para validar día de la semana
     *
     *@return boolean, true si puede ingresar y false en caso contrario
     */
    boolean validarPlaca(String placa, long fechaIngreso);

    /**
     * Calcula cuanto se demoro el vehiculo en el parqueadero, en horas.
     * 
     * @param fechaIngreso, long con la fecha en la que ingreso el vehiculo al parqueadero
     * @param, fechaSalida, long con la fecha en la que esta intentando salir el vehiculo del parqueadero
     * 
     * @return long, con las horas que se demoro el vehiculo en el parqueadero*/
    long calcularTiempoVehiculoParqueadero(long fechaIngreso, long fechaSalida);

    /**
     * Calcula cuanto se le debe cobrar al vehiculo que intenta salir del parqueadero
     * 
     * @param Registro, objeto que tiene los datos de la estadía del vehiculo en el parqueadero
     * @param Parqueadero, objeto que tiene los datos del parqueadero como las tarifas de cobro
     * 
     * @return long, con el valor que se le debe cobrar al vehiculo
     * */
    long cobrarParqueadero(Registro registro, Parqueadero parqueadero);

    /**
     * Calcula la cantidad de días y horas que estuvo en el parqueadero, basado en el siguiente criterio:
     * Horas si permaneció menos de 9 horas en el parqueadero, de lo contrario se cobra por días.
     * 
     * @param horas, horas que duro el vehiculo en el parqueadero
     * 
     * @return array de long que contiene los dias y las horas que se van a cobrar de parqueadero*/
    long[] calcularDiasHoras(long horas);

}
