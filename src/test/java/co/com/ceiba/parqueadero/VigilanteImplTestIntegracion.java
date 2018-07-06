package co.com.ceiba.parqueadero;

import static co.com.ceiba.parqueadero.ParqueaderoBuilder.aParking;
import static co.com.ceiba.parqueadero.VehiculoBuilder.aVehicle;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.parqueadero.domain.Vigilante;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Vehiculo;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VigilanteImplTestIntegracion {
	
	@Autowired
	Vigilante vigilante;
    
    @Test
    public void testValidatePlacaNoValida() {
    	//Arrange
    	Parqueadero parqueadero = aParking().build();
    	Vehiculo vehiculo = aVehicle().build();
    	//Act
    	String message = vigilante.validate(vehiculo, parqueadero);
    	//Assert
    	Assert.assertEquals("La placa que esta intentando ingresar no es valida", message);
    }
    
}
