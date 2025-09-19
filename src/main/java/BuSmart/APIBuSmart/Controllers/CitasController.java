package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Entities.CitasEntity;
import BuSmart.APIBuSmart.Entities.UserEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepCitas.ExceptionCitaNoEncontrada;
import BuSmart.APIBuSmart.Exceptions.ExcepCliente.ExcepcionClienteNoEncontrado;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExcepcionEncargadoDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionEncargadoNoEncontrado;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionsUsuarioNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.CitasDTO;
import BuSmart.APIBuSmart.Models.DTO.UserDTO;
import BuSmart.APIBuSmart.Service.CitasService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@CrossOrigin
public class    CitasController {

    @Autowired
    CitasService servicecita;

    @GetMapping("/GetCitas")
    public List<CitasDTO> obtenerEncargado(){
        return servicecita.obtenerCitas();
    }

    @GetMapping("/GetCitasPaginadas")
    public ResponseEntity<?> getCitasPaginadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        if (size <= 0 || size > 50) {
            return ResponseEntity.badRequest().body(Map.of(
                    "Status", "El tamaño de la página debe estar entre 1 y 50"
            ));
        }

        Page<CitasDTO> cita = servicecita.getAllUsers(page, size);

        if (cita.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "Status", "No hay citas registrados"
            ));
        }

        return ResponseEntity.ok(cita);
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
    public ResponseEntity<?> deleteCitasArreglado(@PathVariable int id) {
        try {
            boolean eliminado = servicecita.removerCitas(id);
            if (eliminado) {
                return ResponseEntity.ok().body(Map.of(
                        "status", "success",
                        "message", "Cita eliminada exitosamente"
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", "error",
                        "message", "No se pudo eliminar la cita"
                ));
            }
        } catch (ExceptionCitaNoEncontrada e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", e.getMessage(),
                    "timestamp", Instant.now().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Error al eliminar cita",
                    "detail", e.getMessage()
            ));
        }
    }

}
