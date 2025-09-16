package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepCliente.*;
import BuSmart.APIBuSmart.Models.DTO.ClienteDTO;
import BuSmart.APIBuSmart.Models.DTO.UserDTO;
import BuSmart.APIBuSmart.Service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/GetClientes")
    public ResponseEntity<?> getAllClientes() {
        try {
            List<ClienteDTO> clientes = clienteService.getAllClientes();
            return ResponseEntity.ok(clientes);
        } catch (ExcepcionClienteNoRegistrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "info",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al obtener clientes",
                    "detail", e.getMessage()
            ));
        }
    }

    @GetMapping("/GetClientesPaginados")
    public ResponseEntity<?> getClientesPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        if (size <= 0 || size > 50) {
            return ResponseEntity.badRequest().body(Map.of(
                    "Status", "El tamaño de la página debe estar entre 1 y 50"
            ));
        }

        Page<ClienteDTO> clientes = clienteService.getAllClientesPaginados(page, size);

        if (clientes.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "Status", "No hay clientes registrados"
            ));
        }

        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/PostClientes")
    public ResponseEntity<?> createCliente(@Valid @RequestBody ClienteDTO clienteDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            ClienteDTO clienteCreado = clienteService.insertCliente(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", clienteCreado));
        } catch (ExcepcionClienteDuplicado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos duplicados",
                    "campo", e.getCampoDuplicado(),
                    "message", e.getMessage()
            ));
        } catch (ExcepcionClienteNoRegistrado e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }

    @PutMapping("/PutClientes/{id}")
    public ResponseEntity<?> updateCliente(
            @PathVariable int id,
            @Valid @RequestBody ClienteDTO clienteDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            ClienteDTO clienteActualizado = clienteService.updateCliente(id, clienteDTO);
            return ResponseEntity.ok(clienteActualizado);
        } catch (ExcepcionClienteNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        } catch (ExcepcionClienteDuplicado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos duplicados",
                    "campo", e.getCampoDuplicado(),
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/DeleteClientes/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable int id) {
        try {
            boolean eliminado = clienteService.deleteCliente(id);
            if (eliminado) {
                return ResponseEntity.ok().body(Map.of(
                        "status", "success",
                        "message", "Cliente eliminado exitosamente"
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", "error",
                        "message", "No se pudo eliminar el cliente"
                ));
            }
        } catch (ExcepcionClienteNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", e.getMessage(),
                    "timestamp", Instant.now().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Error al eliminar cliente",
                    "detail", e.getMessage()
            ));
        }
    }
}