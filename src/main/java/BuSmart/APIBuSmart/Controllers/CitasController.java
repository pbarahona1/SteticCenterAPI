package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExcepcionEncargadoDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionEncargadoNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.CitasDTO;
import BuSmart.APIBuSmart.Service.CitasService;
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
@RequestMapping("/ApiCitas")
public class CitasController {

    @Autowired
    CitasService servicecita;

    @GetMapping("/GetCitas")
    public List<CitasDTO> obtenerEncargado(){
        return servicecita.obtenerCitas();
    }

    @PostMapping("/PostCitas")
    public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody CitasDTO json, HttpServletRequest request){
        try{
            CitasDTO respuesta = servicecita.insertarCitas(json);
            if(respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "insercion fallida",
                        "type", "VALIDATION_ERROR",
                        "message", "los datos no pudieron ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "succes",
                    "data", respuesta
            ));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "error no controlado al registrar usuario",
                    "detail", "error no encontrado añ registrar usuario",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/PutCitas/{id}")
    public ResponseEntity<?> ModificarEncargado(
            @PathVariable Long id,
            @Valid @RequestBody CitasDTO json,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            Map<String,String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try{
            CitasDTO dto = servicecita.actualizarCita(id, json);
            return ResponseEntity.ok(dto);
        }

        catch (ExceptionEncargadoNoEncontrado e){
            return ResponseEntity.notFound().build();
        }

        catch (ExcepcionEncargadoDuplicados e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado())
            );
        }
    }

    @DeleteMapping("/DeleteCitas/{id}")
    public ResponseEntity<?> eliminarEncargado(@PathVariable long id){
        try{

            if (servicecita.removerCitas(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensage-Error", "Encargado no encontrado")
                        .body(Map.of(
                                "error", "Not found",
                                "Mensaje error", "El Encargado no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Encargado eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar Encargado",
                    "detail", e.getMessage()
            ));
        }
    }
}
