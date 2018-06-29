package co.com.ceiba.parqueadero;

import co.com.ceiba.parqueadero.model.Parqueadero;

public class ParqueaderoBuilder {

    private Long adicionCilindraje;
    private int topeCarros;
    private int topeMotos;
    private Long valorDiaCarro;
    private Long valorDiaMoto;
    private Long valorHoraCarro;
    private Long valorHoraMoto;
    private int topeCilindraje;
    private int cantidadActualCarro;
    private int cantidadActualMoto;

    public ParqueaderoBuilder() {
        this.adicionCilindraje = (long) 2000;
        this.topeCarros = 20;
        this.topeMotos = 10;
        this.valorDiaCarro = (long) 8000;
        this.valorDiaMoto = (long) 4000;
        this.valorHoraCarro = (long) 1000;
        this.valorHoraMoto = (long) 500;
        this.topeCilindraje = 500;
        this.cantidadActualCarro = 0;
        this.cantidadActualMoto = 0;
    }

    public Parqueadero build(){
        return new Parqueadero( valorHoraCarro, valorHoraMoto, valorDiaCarro, valorDiaMoto,
        		adicionCilindraje, topeCilindraje, topeCarros, topeMotos, cantidadActualCarro, cantidadActualMoto);
    }

    public static ParqueaderoBuilder aParking(){
        return new ParqueaderoBuilder();
    }

}
