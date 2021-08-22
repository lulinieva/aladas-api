package ar.com.ada.api.aladas;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.services.AeropuertoService;
import ar.com.ada.api.aladas.services.VueloService;
import ar.com.ada.api.aladas.services.VueloService.ValidacionVueloDataEnum;

@SpringBootTest
class AladasApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	VueloService vueloService;

	@Autowired
	AeropuertoService aeropuertoService;

	@Test
	void vueloTestPrecioNegativo() {

		Vuelo vueloConPrecioNegativo = new Vuelo();
		vueloConPrecioNegativo.setPrecio(new BigDecimal(-100));

		assertFalse(vueloService.validarPrecio(vueloConPrecioNegativo));
	}

	@Test
	void vueloTestPrecioOk() {

		Vuelo vueloConPrecioOk = new Vuelo();
		vueloConPrecioOk.setPrecio(new BigDecimal(100));

		assertTrue(vueloService.validarPrecio(vueloConPrecioOk));
	}

	@Test
	void aeropuertoValidarCodigoIATAOk() {// funcion general

		String codigoIATAOk1 = "EZE";
		String codigoIATAOk2 = "AEP";
		String codigoIATAOk3 = "NQN";
		String codigoIATAOk4 = "N ";
		String codigoIATAOk5 = "N39";

		assertEquals(3, codigoIATAOk1.length());

		assertTrue(codigoIATAOk2.length() == 3);

		Aeropuerto aeropuerto1 = new Aeropuerto();
		aeropuerto1.setCodigoIATA(codigoIATAOk1);

		Aeropuerto aeropuerto2 = new Aeropuerto();
		aeropuerto2.setCodigoIATA(codigoIATAOk2);

		Aeropuerto aeropuerto3 = new Aeropuerto();
		aeropuerto3.setCodigoIATA(codigoIATAOk3);

		Aeropuerto aeropuerto4 = new Aeropuerto();
		aeropuerto4.setCodigoIATA(codigoIATAOk4);

		assertTrue(aeropuertoService.validarCodigoIATA(aeropuerto1));// testeamos este funcionamiento, IMPORTANTE.
		assertTrue(aeropuertoService.validarCodigoIATA(aeropuerto2)); //estos es TEST unitario, lo ultimo es lo que valida
		assertTrue(aeropuertoService.validarCodigoIATA(aeropuerto3));

		assertFalse(aeropuertoService.validarCodigoIATA(aeropuerto4));

	}

	@Test
	void aeropuertoValidarCodigoIATANoOk() {

	}

	@Test
	void vueloVerificarValidacionAeropuertoOrigenDestino() {

	}

	@Test
	void vueloChequearQeLosPendientesNoTenganVuelosViejos() {

	}

	@Test
	void vueloVerifiacarCapacidadMaxima() {

	}

	@Test
	void vueloVerificarCapacidadMinima() {

	}

	@Test
	void aeropuertoTestBuscadorIATA() {

	}

	@Test
	void vueloValidarVueloMismoDestinoUsandoGeneral(){
		Vuelo vuelo = new Vuelo();
		vuelo.setPrecio(new BigDecimal(1000));
		vuelo.setEstadoVueloId(EstadoVueloEnum.GENERADO);
		vuelo.setAeropuertoOrigen(116);
		vuelo.setAeropuertoDestino(116);

		assertTrue(vueloService.validar(vuelo) == ValidacionVueloDataEnum.ERROR_AEROPUERTOS_IGUALES);


	}

}
