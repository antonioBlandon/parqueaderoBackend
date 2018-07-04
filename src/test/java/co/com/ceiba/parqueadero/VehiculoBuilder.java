package co.com.ceiba.parqueadero;

import co.com.ceiba.parqueadero.model.Vehiculo;

public class VehiculoBuilder {

    private String placa;
    private int cilindraje;
    private Long fechaIngreso;

    public VehiculoBuilder() {
        placa = "ABC123";
        cilindraje = 0;
        fechaIngreso = (long) 1353934000; //Fri Jan 16 1970 11:05:34 GMT-0500
    }

    public VehiculoBuilder withPlacaWithoutAinit(String placa){
        this.placa = placa;
        return this;
    }

    public VehiculoBuilder withFechaValida(long fechaIngreso){
        this.fechaIngreso = fechaIngreso;
        return this;
    }

    public VehiculoBuilder buildMotoWithAltoCilindraje(){
        this.cilindraje = 650;
        return this;
    }

    public VehiculoBuilder buildMotoWithBajoCilindraje(){
        this.cilindraje = 250;
        return this;
    }

    public Vehiculo build(){
        return new Vehiculo(placa, fechaIngreso, cilindraje);
    }

    public static VehiculoBuilder aVehicle(){
        return new VehiculoBuilder();
    }

}
