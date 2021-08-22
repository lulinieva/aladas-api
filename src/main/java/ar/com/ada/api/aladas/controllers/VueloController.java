package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.aladas.entities.Vuelo;
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
        if ( resultadoValidacion == ValidacionVueloDataEnum.OK) {

            service.crear(vuelo);

            respuesta.isOk = true;
            respuesta.id = vuelo.getVueloId();
            respuesta.message = "vuelo creado correctamente!";

            return ResponseEntity.ok(respuesta); // el "ok" es el 200 que confirma el vuelo creado.
        }
         else{
             respuesta.isOk = false;
             respuesta.message = "Error(" + resultadoValidacion.toString() + ")";


             return ResponseEntity.badRequest().body(respuesta);

         }

    }

}
