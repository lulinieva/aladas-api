package ar.com.ada.api.aladas.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.repos.VueloRepository;

@Service
public class VueloService {

    @Autowired
    private VueloRepository repo;

    @Autowired
    private AeropuertoService aeropService; // funcionalidad

    public void crear(Vuelo vuelo) { // CRUD

        this.validar(vuelo);

        vuelo.setEstadoVueloId(EstadoVueloEnum.GENERADO);
        repo.save(vuelo);
    }

    public Vuelo crear(Date fecha, Integer capacidad, String aeropuertoOrigenIATA, String aeropuertoDestinoIATA,
            BigDecimal precio, String codigoMoneda) {

        Vuelo vuelo = new Vuelo();

        vuelo.setFecha(fecha);
        vuelo.setCapacidad(capacidad);

        Aeropuerto aeropuertoOrigen = aeropService.buscarPorCodigoIATA(aeropuertoOrigenIATA);
        Aeropuerto aeropuertoDestino = aeropService.buscarPorCodigoIATA(aeropuertoDestinoIATA);

        vuelo.setAeropuertoOrigen(aeropuertoOrigen.getAeropuertoId());
        vuelo.setAeropuertoDestino(aeropuertoDestino.getAeropuertoId());

        vuelo.setPrecio(precio);
        vuelo.setCodigoMoneda(codigoMoneda);

        return repo.save(vuelo);

    }

    public ValidacionVueloDataEnum validar(Vuelo vuelo) {

        if (!validarPrecio(vuelo))
            return ValidacionVueloDataEnum.ERROR_PRECIO;

        if (!validarAeropuertOrigenDiffDestino(vuelo))
            return ValidacionVueloDataEnum.ERROR_AEROPUERTOS_IGUALES;

        return ValidacionVueloDataEnum.OK;

    }

    public boolean validarPrecio(Vuelo vuelo) {

        if (vuelo.getPrecio() == null)
            return false;

        if (vuelo.getPrecio().doubleValue() > 0)
            return true;

        return false;

    }

    public boolean validarAeropuertOrigenDiffDestino(Vuelo vuelo) {
        /*
         * if(vuelo.getAeropuertoDestino() != vuelo.getAeropuertoOrigen()) return true;
         * else return false;
         */
        return vuelo.getAeropuertoDestino() != vuelo.getAeropuertoOrigen();
    }

    public enum ValidacionVueloDataEnum {

        OK, ERROR_PRECIO, ERROR_AEROPUERTO_ORIGEN, ERROR_AEROPUERTO_DESTINO, ERROR_FECHA, ERROR_MONEDA,
        ERROR_CAPACIDAD_MINIMA, ERROR_CAPACIDAD_MAXIMA, ERROR_AEROPUERTOS_IGUALES, ERROR_GENERAL,
    }

    public Vuelo buscarPorId(Integer id) {
        return repo.findByVueloId(id);
    }

    public void actualizar(Vuelo vuelo) {
        repo.save(vuelo);// graba todo.
    }

    public List<Vuelo> traerVuelosAbiertos() {// siempre con una lista devolvemos con un OK, no es necesario el
                                              // GerericResponse
        return repo.findByEstadoVueloId(EstadoVueloEnum.ABIERTO.getValue());
    }

}
