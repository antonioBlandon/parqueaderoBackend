package co.com.ceiba.parqueadero;

import co.com.ceiba.parqueadero.model.Registro;

public class RegistroBuilder {

    private String placa;
    private int cilindraje;
    private Long fechaIngreso;
    private Long fechaSalida;
    private Long horasEnParqueadero;
    private Long diasEnParqueadero;
    private Long valorPagado;

    public RegistroBuilder() {
        placa = "ABC123";
        cilindraje = 0;
        fechaIngreso = (long) 1353934000; //Fri Jan 16 1970 11:05:34 GMT-0500
        fechaSalida = (long) 1451134000; //Sat Jan 17 1970 14:05:34 GMT-0500
        horasEnParqueadero = (long) 0;
        diasEnParqueadero = (long) 1;
        valorPagado = (long) 11000;
    }

    public RegistroBuilder withPlacaWithoutAinit(String placa){
        this.placa = placa;
        return this;
    }

    public RegistroBuilder withFechaValida(long fechaIngreso){
        this.fechaIngreso = fechaIngreso;
        return this;
    }

    public RegistroBuilder withFechaDeSalida(long fechaSalida){
        this.fechaSalida = fechaSalida;
        return this;
    }

    public RegistroBuilder withTiempoEnParqueadero(long horasEnParqueadero,long diasEnParqueadero){
        this.horasEnParqueadero = horasEnParqueadero;
        this.diasEnParqueadero = diasEnParqueadero;
        return this;
    }

    public RegistroBuilder buildMotoWithAltoCilindraje(){
        this.cilindraje = 650;
        return this;
    }

    public RegistroBuilder buildMotoWithBajoCilindraje(){
        this.cilindraje = 250;
        return this;
    }

    public Registro build(){
        return new Registro(placa, fechaIngreso, cilindraje, fechaSalida, valorPagado,
    			diasEnParqueadero, horasEnParqueadero);
    }

    public static RegistroBuilder aRegister(){
        return new RegistroBuilder();
    }

}

