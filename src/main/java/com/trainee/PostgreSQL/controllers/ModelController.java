package com.trainee.PostgreSQL.controllers;

import com.trainee.PostgreSQL.models.Model;
import com.trainee.PostgreSQL.service.ModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ModelController {

    @Autowired
    private ModelService service;

    @PostMapping(value = "/model")
    public ResponseEntity<?> createModel(@Valid @RequestBody Model model, BindingResult result) {
        Model modelo = null;
        Map<String, Object> response = new HashMap<>();

        if( result.hasErrors() ) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            modelo = service.createModel(model);
        } catch( DataAccessException e ) {
            response.put("mensaje", "Error al insertar el modelo en la base de datos.");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Modelo creado con exito.");
        response.put("modelo", modelo);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/models")
    public ResponseEntity<?> listAllModels() {
        List<Model> listado = null;
        Map<String, Object> response = new HashMap<>();
        try {
            listado = service.listModels();
        } catch( DataAccessException e ) {
            response.put("mensaje", "Error al buscar la lista de modelos.");
            response.put("Error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("listado", listado);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/models/{id}")
    public ResponseEntity<?> findModelById(@PathVariable("id") Integer id) {
        Model modelo = null;
        Map<String, Object> response = new HashMap<>();
        try {
            modelo = service.oneModel(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if ( modelo == null) {
            response.put("mensaje", "El modelo ID: ".concat(id.toString().concat(" no existe en la base de datos.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("modelo", modelo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/model/{id}")
    public ResponseEntity<?> updateModel(@Valid @RequestBody Model modelo,BindingResult result, @PathVariable Integer id) {
        Model modeloActual = service.oneModel(id);
        Model modeloUpdated = null;

        Map<String, Object> response = new HashMap<>();

        //TODO: Hacer el manejo de errores.
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(
                            err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if ( modeloActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el modelo ID: ".concat(id.toString().concat(" no existe en la base de datos.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            modeloActual.setInstalacion(modelo.getInstalacion());
            modeloActual.setPago(modelo.getPago());
            modeloActual.setFecha(modelo.getFecha());
            modeloActual.setComprobante(modelo.getComprobante());

            modeloUpdated = service.createModel(modeloActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El modelo ha sido actualizado con exito");
        response.put("modelo", modeloUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/model/{id}")
    public ResponseEntity<?> deleteModelById(@PathVariable("id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            service.deleteModel(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El modelo eliminado con exito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


