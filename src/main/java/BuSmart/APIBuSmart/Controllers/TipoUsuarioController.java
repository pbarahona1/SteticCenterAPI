package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Models.DTO.UnidadesDTO;
import BuSmart.APIBuSmart.Service.UnidadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiUnidades")
public class UnidadesController {

    @Autowired
    private UnidadesService service;

    // Obtener todas las unidades
    @GetMapping("/ObtenerUnidades")
    public ResponseEntity<List<UnidadesDTO>> obtenerTodasUnidades() {
        List<UnidadesDTO> unidades = service.obtenerTodasUnidades();
        return ResponseEntity.ok(unidades);
    }

    // Crear nueva unidad
    @PostMapping("/RegistrarUnidad")
    public ResponseEntity<?> crearUnidad(@RequestBody UnidadesDTO dto) {
        try {
            UnidadesDTO nuevaUnidad = service.crearUnidad(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaUnidad);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Error al crear unidad",
                    "message", e.getMessage()
            ));
        }
    }

    @PutMapping("/ActualizarUnidad/{id}")
    public ResponseEntity<?> actualizarUnidad(
            @PathVariable Long id,
            @RequestBody UnidadesDTO dto) {
        try {
            dto.setIdUnidad(id);
            UnidadesDTO actualizada = service.actualizarUnidad(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Error al actualizar unidad",
                    "message", e.getMessage()
            ));
        }
    }

    // Eliminar unidad
    @DeleteMapping("/EliminarUnidad/{id}")
    public ResponseEntity<?> eliminarUnidad(@PathVariable Long id) {
        try {
            service.eliminarUnidad(id);
            return ResponseEntity.ok().body(Map.of(
                    "status", "success",
                    "message", "Unidad eliminada correctamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Error al eliminar unidad",
                    "message", e.getMessage()
            ));
        }
    }
}