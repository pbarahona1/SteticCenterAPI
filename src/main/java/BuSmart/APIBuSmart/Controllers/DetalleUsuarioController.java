package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExcepcionDatosDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionRutaNoEncontrada;
import BuSmart.APIBuSmart.Models.DTO.DetalleUsuarioDTO;
import BuSmart.APIBuSmart.Service.DetalleUsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ApiDetalleUsuario")
public class DetalleUsuarioController {

    @Autowired
    DetalleUsuarioService service;

    @GetMapping("/GetDetalleUsuario")
    public List<DetalleUsuarioDTO> obtenerDatos(){return  service.obtenerDetalleUsuario();}


    @PostMapping("/PostDetalleUsuario")
    public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody DetalleUsuarioDTO json, HttpServletRequest request){
        try{
            DetalleUsuarioDTO respuesta = service.InsertarDetalleUsuario(json);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserccion fallida",
                        "errorType", "VALIDACION_ERROR",
                        "message", "Las rutas no pudieron ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "Success",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "Error",
                            "message", "Error no controlado al regitrar usuario",
                            "detail", e.getMessage()
                    ));
        }

    }

    @PutMapping("/PutDetalleUsuario/{id}")
    public ResponseEntity<?> modificarRuta(
            @PathVariable Long id,
            @Valid @RequestBody DetalleUsuarioDTO json,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            DetalleUsuarioDTO dto = service.ActualizarDetalleUsuario(id, json);
            return ResponseEntity.ok(dto);
        }catch (ExceptionRutaNoEncontrada e){
            return ResponseEntity.notFound().build();
        }catch (ExcepcionDatosDuplicados e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("Error", "Datos duplicados",
                            "Campo", e.getCampoDuplicado())
            );
        }
    }


    @DeleteMapping("/DeleteDetalleUsuario/{id}")
    public  ResponseEntity<?> EliminarRuta(
            @PathVariable Long id)
    {
        try{
            if (!service.EliminarDetalleUsuario(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje de error", "Detalle de Usuario no Encontrada")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensaje", "el Detalle de Usuario no fue encontrada",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Detalle de Usuario eliminada exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar el Detalle de Usuario",
                    "detail", e.getMessage()
            ));
        }
    }
}
