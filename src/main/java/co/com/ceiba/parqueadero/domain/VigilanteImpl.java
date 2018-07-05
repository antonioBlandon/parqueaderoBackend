package co.com.ceiba.parqueadero.domain;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Registro;

@Service
public class VigilanteImpl implements Vigilante {

    private VigilanteImpl() {
    }

    @Override
    public boolean validarCantidadCarros(int cantidadCarrosActual) {

        int cantidadCarros = cantidadCarrosActual + 1;
        return cantidadCarros <= 20;

    }

    @Override
    public boolean validarCantidadMotos(int cantidadMotosActual) {
        int cantidadMotos = cantidadMotosActual + 1;
        return cantidadMotos <= 10;
    }

    @Override
    public boolean validarPlaca(String placa, long fechaIngreso) {

        String primeraLetra = placa.substring(0, 1);
        if (primeraLetra.equals("A")) {
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
        long tiempo = fechaSalida - fechaIngreso;
        double tiempoEnSegundos = (float) tiempo / 1_000;
        return (Double.valueOf(Math.ceil(tiempoEnSegundos / 3600))).longValue();
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
        long dias = 0;
        long[] diasHoras = new long[2];
        while (horas >= 0) {

            if (horas > 9) {
                dias++;
                horas = horas - 24;
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

}
