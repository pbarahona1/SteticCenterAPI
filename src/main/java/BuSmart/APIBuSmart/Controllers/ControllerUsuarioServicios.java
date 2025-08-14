package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Models.DTO.UsuarioServiciosDTO;
import BuSmart.APIBuSmart.Service.ServicesUsuarioServicio;
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
@RequestMapping("/ApiUsuarioServicios")
public class ControllerUsuarioServicios {

    @Autowired
    ServicesUsuarioServicio service;

    @GetMapping("/ConsultarUsuarioServicios")
    public List<UsuarioServiciosDTO> obtenerDatos(){return service.ObtenerUsuariosServicios();}

    @PostMapping("/IngresarUsuarioServicios")
    public ResponseEntity<?> nuevoUsuarioServicio(@Valid @RequestBody UsuarioServiciosDTO json, HttpServletRequest request){

        try {
            UsuarioServiciosDTO respuesta = service.InsertarUsuarioServicios(json);
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

    @PutMapping("/EditarUsuarioServicios/{id}")
    public ResponseEntity<?> modificarUsuarioServicio(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioServiciosDTO json,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            UsuarioServiciosDTO dto = service.ActualizarUsuariosServicios(id, json);
            return ResponseEntity.ok(dto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("Error","Datos Duplicados",
                            "Campo",e.getMessage())
            );
        }
    }

    @DeleteMapping("/EliminarUsuarioServicios/{id}")
    public ResponseEntity<?> EliminarUsuarioServico(@PathVariable Long id){
        try {
            if (!service.EliminarUsuarioServicio(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                        "Mensaje de error", "UsuarioServucui no encontrado").body(Map.of(
                        "Error","NOT FOUND",
                        "Mensaje","El UsuarioServicio no fue encontrado",
                        "Timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "Status","Proceso Completo",
                    "Message","UsuarioServicio eliminado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "Status","Error",
                    "Message", "Error al eliminar el Usuarioservicio",
                    "Detail", e.getMessage()
            ));
        }

    }
}
