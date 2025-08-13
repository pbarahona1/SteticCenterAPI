package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Models.DTO.TipoUsuarioDTO;
import BuSmart.APIBuSmart.Service.TipoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ApiTipoUsuario")
public class TipoUsuarioController {

    @Autowired
    TipoUsuarioService service;

    // Obtener todas las unidades
    @GetMapping("/GetTipoUsuario")
    public ResponseEntity<List<TipoUsuarioDTO>> obtenerTodasUnidades() {
        List<TipoUsuarioDTO> unidades = service.ObtenerTiposUsuario();
        return ResponseEntity.ok(unidades);
    }

    // Crear nueva unidad
    @PostMapping("/PostTipoUsuario")
    public ResponseEntity<?> crearUnidad(@RequestBody TipoUsuarioDTO dto) {
        try {
            TipoUsuarioDTO nuevaUnidad = service.CrearTipoUsuario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaUnidad);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Error al crear el tipo de usuario",
                    "message", e.getMessage()
            ));
        }
    }

    @PutMapping("/PutTipoUsuario/{id}")
    public ResponseEntity<?> actualizarUnidad(
            @PathVariable Long id,
            @RequestBody TipoUsuarioDTO dto) {
        try {
            TipoUsuarioDTO actualizada = service.actualizarTipoUsuario(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Error al actualizar el tipo de usuario",
                    "message", e.getMessage()
            ));
        }
    }

    // Eliminar unidad
    @DeleteMapping("/DeleteTipoUsuario/{id}")
    public ResponseEntity<?> eliminarUnidad(@PathVariable Long id) {
        try {
            service.eliminarTipoUnidad(id);
            return ResponseEntity.ok().body(Map.of(
                    "status", "success",
                    "message", "tipo de usuario eliminado correctamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Error al eliminar el tipo de usuario",
                    "message", e.getMessage()
            ));
        }
    }
}