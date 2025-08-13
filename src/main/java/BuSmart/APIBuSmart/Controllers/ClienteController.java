package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepCliente.ExcepcionClienteDuplicado;
import BuSmart.APIBuSmart.Exceptions.ExcepCliente.ExcepcionClienteNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.ClienteDTO;
import BuSmart.APIBuSmart.Service.ClienteService;
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
@RequestMapping("/ApiCliente")
public class ClienteController {

    private final ClienteService serviceCliente;

    @Autowired
    public ClienteController(ClienteService serviceCliente) {
        this.serviceCliente = serviceCliente;
    }

    @GetMapping("/GetClientes")
    public ResponseEntity<?> obtenerClientes() {
        try {
            List<ClienteDTO> clientes = serviceCliente.obtenerClientes();
            if (clientes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", "success",
                        "message", "No se encontraron clientes registrados",
                        "data", List.of()
                ));
            }
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "data", clientes
            ));
        } catch (ExcepcionClienteNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", e.getMessage(),
                    "timestamp", Instant.now().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Error interno al obtener clientes",
                    "detail", e.getMessage(),
                    "timestamp", Instant.now().toString()
            ));
        }
    }

    @PostMapping("/PostClientes")
    public ResponseEntity<?> nuevoCliente(@Valid @RequestBody ClienteDTO json,
                                          HttpServletRequest request,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "validation_error",
                    "errors", errores
            ));
        }

        try {
            ClienteDTO respuesta = serviceCliente.insertarCliente(json);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", respuesta,
                    "timestamp", Instant.now().toString()
            ));
        } catch (ExcepcionClienteDuplicado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "status", "error",
                    "message", e.getMessage(),
                    "campo_duplicado", e.getCampoDuplicado(),
                    "timestamp", Instant.now().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Error interno al registrar cliente",
                    "detail", e.getMessage(),
                    "timestamp", Instant.now().toString()
            ));
        }
    }

    @PutMapping("/PutClientes/{id}")
    public ResponseEntity<?> modificarCliente(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteDTO json,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "validation_error",
                    "errors", errores
            ));
        }

        try {
            ClienteDTO dto = serviceCliente.actualizarCliente(id, json);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "data", dto,
                    "timestamp", Instant.now().toString()
            ));
        } catch (ExcepcionClienteNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", e.getMessage(),
                    "timestamp", Instant.now().toString()
            ));
        } catch (ExcepcionClienteDuplicado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "status", "error",
                    "message", e.getMessage(),
                    "campo_duplicado", e.getCampoDuplicado(),
                    "timestamp", Instant.now().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Error interno al actualizar cliente",
                    "detail", e.getMessage(),
                    "timestamp", Instant.now().toString()
            ));
        }
    }

    @DeleteMapping("/DeleteClientes/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable int id) {
        try {
            boolean eliminado = serviceCliente.removerCliente(id);
            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", "error",
                        "message", "No se encontró el cliente con ID: " + id,
                        "timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Cliente eliminado correctamente",
                    "id_eliminado", id,
                    "timestamp", Instant.now().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Error interno al eliminar cliente",
                    "detail", e.getMessage(),
                    "timestamp", Instant.now().toString()
            ));
        }
    }

}