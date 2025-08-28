package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepPaquetes.*;
import BuSmart.APIBuSmart.Models.DTO.PaquetesDTO;
import BuSmart.APIBuSmart.Service.PaquetesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/paquetes")
@CrossOrigin(origins = "http://localhost")
public class PaquetesController {

    @Autowired
    private PaquetesService paquetesService;

    @GetMapping("/GetPaquetes")
    public ResponseEntity<?> getAllPaquetes() {
        try {
            List<PaquetesDTO> paquetes = paquetesService.getAllPaquetes();
            return ResponseEntity.ok(paquetes);
        } catch (ExcepcionPaquetesNoRegistrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Error al obtener paquetes")
            );
        }
    }

    @PostMapping("/PostPaquetes")
    public ResponseEntity<?> createPaquetes(@Valid @RequestBody PaquetesDTO paquetesDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            PaquetesDTO nuevoPaquetes = paquetesService.createPaquetes(paquetesDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaquetes);
        } catch (ExcepcionPaquetesDuplicado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", e.getMessage(), "campo", e.getCampoDuplicado())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Error al crear el paquete")
            );
        }
    }

    @PutMapping("/PutPaquetes/{id}")
    public ResponseEntity<?> updatePaquetes(
            @PathVariable Integer id,
            @Valid @RequestBody PaquetesDTO paquetesDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            PaquetesDTO paquetesActualizado = paquetesService.updatePaquetes(id, paquetesDTO);
            return ResponseEntity.ok(paquetesActualizado);
        } catch (ExcepcionPaquetesNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", e.getMessage())
            );
        } catch (ExcepcionPaquetesDuplicado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", e.getMessage(), "campo", e.getCampoDuplicado())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Error al actualizar el paquete")
            );
        }
    }

    @DeleteMapping("/DeletePaquetes/{id}")
    public ResponseEntity<?> deletePaquetes(@PathVariable Integer id) {
        try {
            paquetesService.deletePaquetes(id);
            return ResponseEntity.noContent().build();
        } catch (ExcepcionPaquetesNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Error al eliminar el paquete")
            );
        }
    }
}