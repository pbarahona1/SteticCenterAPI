package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExcepcionDatosDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionRutaNoEncontrada;
import BuSmart.APIBuSmart.Models.DTO.RutaDTO;
import BuSmart.APIBuSmart.Service.RutaService;
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
@RequestMapping("/apiRuta")
public class RutaController {

    @Autowired
    RutaService service;

    @GetMapping("/ConsultarRutas")
    public List<RutaDTO> obtenerDatos(){return  service.obtenerRutas();}

    @PostMapping("/RegistrarRutas")
    public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody RutaDTO json, HttpServletRequest request){
        try{
            RutaDTO respuesta = service.InsertarRutas(json);
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

    @PutMapping("/EditarRuta/{id}")
    public ResponseEntity<?> modificarRuta(
            @PathVariable Long id,
            @Valid @RequestBody RutaDTO json,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            RutaDTO dto = service.ActualizarRuta(id, json);
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


    @DeleteMapping("/EliminarRutas/{id}")
    public  ResponseEntity<?> EliminarRuta(
            @PathVariable Long id)
    {
        try{
            if (!service.QuitarRuta(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje de error", "Ruta no Encontrada")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensaje", "La ruta no fue encontrada",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Ruta eliminada exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar la ruta",
                    "detail", e.getMessage()
            ));
        }
    }
}
