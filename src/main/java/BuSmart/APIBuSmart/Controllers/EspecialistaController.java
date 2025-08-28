package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepEspecialista.ExceptionEspecialidadDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepEspecialista.ExceptionEspecialistaNoEncontrado;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExcepcionDatosDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionRutaNoEncontrada;
import BuSmart.APIBuSmart.Models.DTO.EspecialistaDTO;
import BuSmart.APIBuSmart.Service.EspecialistaService;
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
@RequestMapping("/ApiEspecialista")
@CrossOrigin
public class EspecialistaController {

    @Autowired
    EspecialistaService TService;

    @GetMapping("/GetEspecialista")
    public List<EspecialistaDTO> obtenerDatos(){return  TService.obtenerEspecialista();}


    @PostMapping("/PostEspecialista")
    public ResponseEntity<?> nuevoEspecialista(@Valid @RequestBody EspecialistaDTO json, HttpServletRequest request){
        try{
            EspecialistaDTO respuesta = TService.InsertarTipoEspecialista(json);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserccion fallida",
                        "errorType", "VALIDACION_ERROR",
                        "message", "el tipo de especialista no pudo ser registrados"
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
                            "message", "Error no controlado al regitrar el tipo de especialista",
                            "detail", e.getMessage()
                    ));
        }

    }

    @PutMapping("/PutEspecialista/{id}")
    public ResponseEntity<?> modificarTipoEncargado(
            @PathVariable Long id,
            @Valid @RequestBody EspecialistaDTO json,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            EspecialistaDTO dto = TService.ActualizarTipoDeEncargado(id, json);
            return ResponseEntity.ok(dto);
        } catch (ExceptionEspecialistaNoEncontrado e) {
            return ResponseEntity.notFound().build();
        } catch (ExceptionEspecialidadDuplicados e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados",
                            "campo", e.getCampoDuplicado())
            );
        }
    }


    @DeleteMapping("/DeleteEspecialista/{id}")
    public  ResponseEntity<?> EliminarTipoEncargado(
            @PathVariable Long id)
    {
        try{
            if (!TService.EliminarTipoEncargado(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje de error", "Especialidad no Encontrada")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensaje", "La Especialidad no fue encontrada",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Especialidad eliminada exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar la Especialidad",
                    "detail", e.getMessage()
            ));
        }
    }
}
