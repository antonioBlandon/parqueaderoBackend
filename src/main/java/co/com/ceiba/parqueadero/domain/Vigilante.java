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
	 * Se valida si se esta ingresando o saliendo del parqueadero, si el vehiculo que lo hace es moto o carro
	 * para saber si debe agregar o sacar un carro del parqueadero y finalmente se actualiza la entidad Parqueadero
	 * 
	 * @param isCar Indica el vehiculo es carro, sino lo es entonces se asume que es moto
	 * @param ingresaVehiculo Indica si el vehiculo esta ingresando(true) o saliendo del parqueadero(false)
	 * @param parqueadero Entidad con los datos actuales del parqueadero
	 * 
	 * @return Una entidad tipo Parqueadero con la actualizacion del ingreso o salida del parqueadero*/
	Parqueadero actualizarParqueadero(boolean isCar,boolean ingresaVehiculo, Parqueadero parqueadero);
	
	/**
	 * Si se cumplen todas las validaciones se ingresa el vehiculo al parqueadero
	 * 
	 * @param vehiculo, vehiculo que se va ingresar al parqueadero, debe tener cilindraje y placa
	 * 
	 * @return Restresponse, para indicar al controlador que debe retornar*/
	RestResponse addVehicle(Vehiculo vehiculo);
	
    
    /**
     * Calcula la cantidad de días y horas que estuvo en el parqueadero, basado en el siguiente criterio:
     * Horas si permaneció menos de 9 horas en el parqueadero, de lo contrario se cobra por días.
     * 
     * @param horas, horas que duro el vehiculo en el parqueadero
     * 
     * @return array de long que contiene los dias y las horas que se van a cobrar de parqueadero*/
    long[] calcularDiasHoras(long horas);
    
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
     * Se crea na entidad tipo registro donde se almacenan todos los datos de la estadía del vehiculo
     * en el parqueadero
     * 
     * @param vehiculo, entidad con los datos del vehiculo que ingreso en el parqueadero
     * @param parqueadero, entidad con los datos actuales del parqueadero
     * 
     * @return objeto tipo Registro con los datos de la estadia del vehiculo en el parqueadero*/
    Registro crearRegistro(Vehiculo vehiculo, Parqueadero parqueadero);
	
	/**
	 * Se realizan los calculos de la estadía del vehículo en el parqueadero y por ultimo saca el vehiculo
	 * del parqueadero
	 * 
	 * @param id, identificador del vehiculo que se desea sacar del parqueadero
	 * 
	 * @return Restresponse, para indicar al controlador que debe retornar*/
	RestResponse outVehicle(Long id);
	
	/**
	 * Se válida que se tenga cupo para el vehiculo que esta intentando ingresar
	 * 
	 * @param isCar, booleano que indica si el vehiculo que esta intentando ingresar es un carro o no.
	 * 			Sino es un carro entonces es una moto.
	 * @param parqueadero, es una entidad donde vienen los datos actuales del parqueadero
	 * 
	 * @return boolean, indica si en el parqueadero hay cupo o no para el vehiculo, true si hay cupo y false 
	 * en caso contrario */
	boolean validarCupo(boolean isCar, Parqueadero parqueadero);

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
     * Reune todas las validaciones que se deben realizar al tratar de ingresar el vehiculo
     * 
     * @param vehiculo Vehiculo que esta intentando ingresar
     * @param parqueadero Datos actuales del parqueadero
     * 
     * @return retorna el mensaje del porque no pudo ingresar el vehiculo o null en caso de que no tenga
     * problemas para ingresar.*/
    String validate(Vehiculo vehiculo, Parqueadero parqueadero);

}
