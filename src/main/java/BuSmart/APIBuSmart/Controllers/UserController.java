package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExcepcionDatosDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionsUsuarioNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.UserDTO;
import BuSmart.APIBuSmart.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/Usuario")
@CrossOrigin//Le faltaba el crossOrigin y el requestMaping solo decia "api"
public class UserController {

    @Autowired
    UserService acceso;

    @GetMapping("/GetUsuarios")
    public ResponseEntity<?> getUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        if (size <= 0 || size > 50) {
            return ResponseEntity.badRequest().body(Map.of(
                    "Status", "El tamaño de la página debe estar entre 1 y 50"
            ));
        }

        Page<UserDTO> usuarios = acceso.getAllUsers(page, size);

        if (usuarios.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "Status", "No hay usuarios registrados"
            ));
        }

        return ResponseEntity.ok(usuarios);
    }


    @PostMapping("/PostUsuarios")
    public ResponseEntity<?> postUsuarios(@Valid @RequestBody UserDTO json, HttpServletRequest request) {
        try {
            LocalDate fechaNacimiento = json.getNacimiento()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Validacion incorrecta",
                        "errorType", "VALIDATION_ERROR",
                        "message", "El usuario debe tener al menos 18 años"
                ));
            }

            UserDTO respuesta = acceso.insertUser(json);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", respuesta));

        } catch (ExcepcionDatosDuplicados e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "status", "Duplicado",
                    "campo", e.getCampoDuplicado(),
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar usuario",
                    "detail", e.getMessage()
            ));
        }
    }


    @PutMapping("/PutUsuario/{id}")//Le faltaba una pleca
    public ResponseEntity<?> modificarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO json,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            UserDTO dto = acceso.actualizarUsuario(id, json);
            return ResponseEntity.ok(dto);
        } catch (ExcepcionDatosDuplicados e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of(
                            "Error", "Dato duplicado",
                            "Campo", e.getMessage()
                    )
            );
        } catch (ExceptionsUsuarioNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "Error", "Usuario no encontrado",
                            "Mensaje", e.getMessage()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(
                            "Error", "Error inesperado",
                            "Detalle", e.getMessage()
                    )
            );
        }
    }



    @DeleteMapping("/DeleteUsuario/{id}")
    public ResponseEntity<Map<String, Object>>deleteUsuario(@PathVariable long id){
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
