package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Models.DTO.ServiciosDTO;
import BuSmart.APIBuSmart.Service.ServiceServicios;
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
@RequestMapping("/ApiServicios")
public class ControllerServicios {

    @Autowired
    ServiceServicios service;

    @GetMapping("/ConsultarServicios")
    public List<ServiciosDTO> obtenerDatos(){return service.ObtenerServicios();}

    @PostMapping("/IngresarServicios")
    public ResponseEntity<?> nuevoServicio(@Valid @RequestBody ServiciosDTO json, HttpServletRequest request){

        try {
            ServiciosDTO respuesta = service.InsertarServicios(json);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "Status","Inserccion Fallida",
                        "ErrorType","VALIDACION_ERROR",
                        "Message","El servicio no pudo ser registrado"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "Status","Succses",
                    "Data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "Status","Error",
                    "Message","Error no controlado al registrar",
                    "Detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/EditarServicios/{id}")
    public ResponseEntity<?> modificarServicio(
            @PathVariable Long id,
            @Valid @RequestBody ServiciosDTO json,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            ServiciosDTO dto = service.ActualizarServicios(id, json);
            return ResponseEntity.ok(dto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("Error","Datos Duplicados",
                            "Campo",e.getMessage())
            );
        }
    }

    @DeleteMapping("/EliminarServicios/{id}")
    public ResponseEntity<?> EliminarServico(@PathVariable Long id){
        try {
            if (!service.EliminarServicio(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                        "Mensaje de error", "Servicio no encontrado").body(Map.of(
                        "Error","NOT FOUND",
                        "Mensaje","El servicio no fue encontrado",
                        "Timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "Status","Proceso Completo",
                    "Message","Servicio eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "Status","Error",
                    "Message", "Error al eliminar el servicio",
                    "Detail", e.getMessage()
            ));
        }

    }
}
