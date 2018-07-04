package co.com.ceiba.parqueadero.domain;

import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Registro;

public interface Vigilante {

    boolean validarCantidadCarros(int cantidadCarrosActual);

    boolean validarCantidadMotos(int cantidadMotosActual);

    boolean validarPlaca(String placa, long fechaIngreso);

    long calcularTiempoVehiculoParqueadero(long fechaIngreso, long fechaSalida);

    long cobrarParqueadero(Registro vehiculo, Parqueadero parqueadero);

    long[] calcularDiasHoras(long horas);

}
