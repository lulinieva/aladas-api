package ar.com.ada.api.aladas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.models.request.EstadoVueloRequest;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.VueloService;
import ar.com.ada.api.aladas.services.VueloService.ValidacionVueloDataEnum;

@RestController
public class VueloController {

    /* @Autowired //anotacion que permite inyectar una dependencia dentro de otra */
    private VueloService service; // variable

    public VueloController(VueloService service) {
        this.service = service;
    }

    @PostMapping("api/vuelos")
    public ResponseEntity<GenericResponse> postcrearVuelo(@RequestBody Vuelo vuelo) { // variable vuelo de tipo vuelo
        GenericResponse respuesta = new GenericResponse();

        ValidacionVueloDataEnum resultadoValidacion = service.validar(vuelo);
        if (resultadoValidacion == ValidacionVueloDataEnum.OK) {

            service.crear(vuelo);

            respuesta.isOk = true;
            respuesta.id = vuelo.getVueloId();
            respuesta.message = "vuelo creado correctamente!";

            return ResponseEntity.ok(respuesta); // el "ok" es el 200 que confirma el vuelo creado.
        } else {
            respuesta.isOk = false;
            respuesta.message = "Error(" + resultadoValidacion.toString() + ")";

            return ResponseEntity.badRequest().body(respuesta);

        }

    }

    @PutMapping("api/vuelos/{id}/estados") // PUT+vuelo=modifica el vuelo.
    public ResponseEntity<GenericResponse> putActualizarEstadoVuelo(@PathVariable Integer id,
            @RequestBody EstadoVueloRequest estadoVuelo) {
        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        // Pasos:
        // 1 buscar vuelo por Id y lo asignamos a una variable(vuelo).
        Vuelo vuelo = service.buscarPorId(id);
        // 2 setearle el nuevo estado, que vino en estadoVuelo al vuelo.
        vuelo.setEstadoVueloId(estadoVuelo.estado);
        // 3 grabar el vuelo en la base de datos.
        service.actualizar(vuelo);
        // 4 que devuelva el status final.
        respuesta.message = "actualizado";
        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/api/vuelos/abiertos")

    public ResponseEntity<List<Vuelo>> getVuelosAbiertos() {

        return ResponseEntity.ok(service.traerVuelosAbiertos());
    }

}
