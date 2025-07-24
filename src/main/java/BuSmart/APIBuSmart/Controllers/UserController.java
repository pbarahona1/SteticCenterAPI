package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExcepcionDatosDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionsUsuarioNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.RutaDTO;
import BuSmart.APIBuSmart.Models.DTO.UserDTO;
import BuSmart.APIBuSmart.Service.UserService;
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
@RequestMapping("/api")

public class UserController {

    @Autowired
    UserService acceso;

    @GetMapping("/usuarios")
    public List<UserDTO> datosUsuarios() {return  acceso.getAllUsers();}

    @PostMapping("/ingresarUsuario")
    public ResponseEntity<?>registrarUsuario(@Valid @RequestBody UserDTO json, HttpServletRequest request) {
        try {
            //Intento de guardar al usuario
            UserDTO respuesta = acceso.insertUser(json);
            if (respuesta == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "Datos del usuario inválidos"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "succes",
                    "data", respuesta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error al registrar usuario",
                            "detail", e.getMessage()
                    ));
        }
    }

    @PutMapping("ModificarUsuario/{id}")
    public ResponseEntity<?> modificarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO usuario,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            UserDTO usuarioActualizado = acceso.actualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        }
        catch (ExceptionsUsuarioNoEncontrado e){
            return ResponseEntity.notFound().build();
        }
        catch (ExcepcionDatosDuplicados e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados", "campo", e.getCampoDuplicado())
            );
        }
    }

    @DeleteMapping("/eliminarusuario/{id}")
    public ResponseEntity<Map<String, Object>>eliminarUsuario(@PathVariable long id){
        try {
            if (!acceso.removerUsuario(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("X-Mensage-Error", "Usuario no encontrado")
                        .body(Map.of(
                                "error", "Not found",
                                    "Mensaje error", "El usuario no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Usuario eliminado exitosamente"
            ));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                "status", "Error",
                    "message", "Error al eliminar Encargado",
                    "detail", e.getMessage()
            ));
        }
    }


}
