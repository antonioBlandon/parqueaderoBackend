package co.com.ceiba.parqueadero;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Registro;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.domain.Vigilante;
import co.com.ceiba.parqueadero.domain.VigilanteImpl;

import static co.com.ceiba.parqueadero.ParqueaderoBuilder.aParking;
import static co.com.ceiba.parqueadero.VehiculoBuilder.aVehicle;
import static co.com.ceiba.parqueadero.RegistroBuilder.aRegister;

public class VigilanteImplTest {

    private Vigilante vigilante;

    @Before
    public void preparaData() {
        vigilante = VigilanteImpl.getInstance();
    }

    @Test
    public void testValidarCantidadCarrosMenor() {
        //Arrange
        int cantidadCarros = 19;
        //Act
        boolean puedeIngresar = vigilante.validarCantidadCarros(cantidadCarros);
        //Assert
        Assert.assertEquals(true, puedeIngresar);
    }

    @Test
    public void testValidarCantidadCarrosMayor() {
        //Arrange
        int cantidadCarros = 20;
        //Act
        boolean puedeIngresar = vigilante.validarCantidadCarros(cantidadCarros);
        //Assert
        Assert.assertEquals(false, puedeIngresar);
    }

    @Test
    public void testValidarCantidadMotosMenor() {
        //Arrange
        int cantidadMotos = 9;
        //Act
        boolean puedeIngresar = vigilante.validarCantidadMotos(cantidadMotos);
        //Assert
        Assert.assertEquals(true, puedeIngresar);
    }

    @Test
    public void testValidarCantidadMotosMayor() {
        //Arrange
        int cantidadMotos = 10;
        //Act
        boolean puedeIngresar = vigilante.validarCantidadMotos(cantidadMotos);
        //Assert
        Assert.assertEquals(false, puedeIngresar);
    }

    @Test
    public void testValidarPlacaSinAInicial() {
        //Arrange
        Vehiculo vehiculo = aVehicle().withPlacaWithoutAinit("BTA234").build();
        //Act
        boolean puedeIngresar = vigilante.validarPlaca(vehiculo.getPlaca(), vehiculo.getFechaIngreso());
        //Assert
        Assert.assertEquals(true, puedeIngresar);
    }

    @Test
    public void testValidarPlacaConAInicialDiasValidos() {
        //Arrange
        Vehiculo vehiculo = aVehicle().withFechaValida(1515934000).build();//Sun Jan 18 1970 08:05:34 GMT-0500
        //Act
        boolean puedeIngresar = vigilante.validarPlaca(vehiculo.getPlaca(), vehiculo.getFechaIngreso());
        //Assert
        Assert.assertEquals(true, puedeIngresar);
    }

    @Test
    public void testValidarPlacaConAInicialDiasNoValidos() {
        //Arrange
        Vehiculo vehiculo = aVehicle().build();
        //Act
        boolean puedeIngresar = vigilante.validarPlaca(vehiculo.getPlaca(), vehiculo.getFechaIngreso());
        //Assert
        Assert.assertEquals(false, puedeIngresar);
    }

    @Test
    public void testCalcularTiempoVehiculoParqueadero() {
        //Arrange
        Registro registro = aRegister().build();
        //Act
        long horasEnParqueadero = vigilante.calcularTiempoVehiculoParqueadero(registro.getFechaIngreso(), registro.getFechaSalida());
        //Assert
        Assert.assertEquals(27, horasEnParqueadero);
    }

    //Si el carro permaneció un día y 3 horas se debe cobrar 11.000
    @Test
    public void testCobrarParqueaderoCarro() {
        //Arrange
        Registro registro = aRegister()
                .withTiempoEnParqueadero(3, 1).build();
        Parqueadero parqueadero = aParking().build();
        //Act
        long cobro = vigilante.cobrarParqueadero(registro, parqueadero);
        //Assert
        Assert.assertEquals(11000, cobro);
    }

    //Si la moto permaneció un 10 horas y es de 650CC se cobra 6.000
    @Test
    public void testCobrarParqueaderoMotoConAltoCilindraje() {
        //Arrange
    	Registro registro = aRegister()
                .withFechaDeSalida(1389934000) //Fri Jan 16 1970 21:05:34 GMT-0500
                .withTiempoEnParqueadero(0, 1)
                .buildMotoWithAltoCilindraje().build();
        Parqueadero parqueadero = aParking().build();
        //Act
        long cobro = vigilante.cobrarParqueadero(registro, parqueadero);
        //Assert
        Assert.assertEquals(6000, cobro);
    }

    //Si la moto permaneció un 10 horas se cobra 4.000
    @Test
    public void testCobrarParqueaderoMotoConBajoCilindraje() {
        //Arrange
    	Registro registro = aRegister()
                .withFechaDeSalida(1389934000) //Fri Jan 16 1970 21:05:34 GMT-0500
                .withTiempoEnParqueadero(0, 1)
                .buildMotoWithBajoCilindraje().build();
        Parqueadero parqueadero = aParking().build();
        //Act
        System.out.println(registro.toString());
        long cobro = vigilante.cobrarParqueadero(registro, parqueadero);
        //Assert
        Assert.assertEquals(4000, cobro);
    }

    @Test
    public void testCalcularDiasHoras() {
        //Arrange
    	int horas = 35;
        //Act
        long[] diasHoras = vigilante.calcularDiasHoras(horas);
        //Assert
        Assert.assertArrayEquals(new long[]{2, 0}, diasHoras);
    }

}