package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExcepcionEncargadoDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionEncargadoNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.CitasDTO;
import BuSmart.APIBuSmart.Models.DTO.DetallesCitasDTO;
import BuSmart.APIBuSmart.Service.CitasService;
import BuSmart.APIBuSmart.Service.DetallesCitasService;
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
@RequestMapping("/ApiDetalleCitas")
@CrossOrigin
public class DetallesCitasController {
    @Autowired
    DetallesCitasService service;

    @GetMapping("/GetDetalleCitas")
    public List<DetallesCitasDTO> obtenerEncargado(){
        return service.obtenerCitas();
    }

    @PostMapping("/PostCitas")
    public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody DetallesCitasDTO json, HttpServletRequest request){
        try{
            DetallesCitasDTO respuesta = service.insertarCitas(json);
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
                    "message", "error no controlado al registrar el detalle de cita",
                    "detail", "error no encontrado al registrar el detalle de cita",
                    "detail", e.getMessage()
            ));
        }
    }


    @PutMapping("/PutDetallesCitas/{id}")
    public ResponseEntity<?> ModificarEncargado(
            @PathVariable Long id,
            @Valid @RequestBody DetallesCitasDTO json,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            Map<String,String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try{
            DetallesCitasDTO dto = service.actualizarCita(id, json);
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


    @DeleteMapping("/DeleteDetallesCitas/{id}")
    public ResponseEntity<?> eliminarEncargado(@PathVariable long id){
        try{

            if (service.removerCitas(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensage-Error", "Detalles de citas no encontrado")
                        .body(Map.of(
                                "error", "Not found",
                                "Mensaje error", "El Detalles de citas no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Detalles de cita eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar Detalles de cita",
                    "detail", e.getMessage()
            ));
        }
    }
}
