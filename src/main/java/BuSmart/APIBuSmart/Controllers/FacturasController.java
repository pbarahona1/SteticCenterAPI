package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepFacrturas.ExceptionFacturaNoEncotrada;
import BuSmart.APIBuSmart.Exceptions.ExcepFacrturas.ExceptionFacturaNoRegistrada;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExcepcionEncargadoDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionEncargadoNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.FacturasDTO;
import BuSmart.APIBuSmart.Models.DTO.UserDTO;
import BuSmart.APIBuSmart.Service.FacturasService;
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
@CrossOrigin

public class FacturasController {
    @Autowired
    FacturasService Service;

    @GetMapping("/GetFacturas")
    public List<FacturasDTO> getFacturas(){return Service.getFacturas(); }

    @PostMapping("/newFactura")
    public ResponseEntity<?> postFactura(@Valid @RequestBody FacturasDTO json, HttpServletRequest request){
        try{
            FacturasDTO response = Service.insertFactura(json);
            if(response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "insercion fallida",
                        "type", "VALIDATION_ERROR",
                        "message", "los datos no pudieron ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "succes",
                    "data", response
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "error no controlado al registrar la Factura",
                    "detail", e.getMessage()
            ));
        }
    }
    @PutMapping("/actualizarFactura/{id}")
    public ResponseEntity<?> ModificarFactura(
        @PathVariable Long id,
        @Valid @RequestBody FacturasDTO json,
        BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);

        }
        try {
            FacturasDTO dto = Service.ActualizarFactura(id, json);
            return ResponseEntity.ok(dto);
        }

        catch (ExceptionFacturaNoEncotrada e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteFactura/{id}")
    public ResponseEntity<?> EliminarFactura(@PathVariable Long id){
        try{
            if(!Service.EliminarFactura(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                        "Mensaje de error", "Factura no encontrada").body(Map.of(
                        "Error","NOT FOUND",
                        "Mensaje","la Factura no fue encontrada",
                        "Timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "Status","Proceso Completo",
                    "Message","Factura eliminada exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "Status","Error",
                    "Message", "Error al eliminar la Factura",
                    "Detail", e.getMessage()
            ));
        }

    }


}
