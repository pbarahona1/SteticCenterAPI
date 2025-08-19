package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepFacrturas.ExceptionFacturaNoEncotrada;
import BuSmart.APIBuSmart.Models.DTO.DetalleFacturaDTO;
import BuSmart.APIBuSmart.Service.DetalleFacturaService;
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
public class DetalleFacturaController {
    @Autowired
    DetalleFacturaService Service;

    @GetMapping("/GetDetalleFacturas")
    public List<Object> getFacturas(){return Service.getDetalleFacturas(); }

    @PostMapping("/newDetalleFactura")
    public ResponseEntity<?> postFactura(@Valid @RequestBody DetalleFacturaDTO json, HttpServletRequest request){
        try{
            DetalleFacturaDTO response = Service.insertDetalleFactura(json);
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
                    "message", "error no controlado al registrar Detalle Factura",
                    "detail", "error no encontrado añ registrar Detalle Factura",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/actualizarDetalleFactura/{id}")
    public ResponseEntity<?> ModificarDetalleFactura(
            @PathVariable Long id,
            @Valid @RequestBody DetalleFacturaDTO json,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);

        }
        try {
            DetalleFacturaDTO dto = Service.ActualizarDetalleFactura(id, json);
            return ResponseEntity.ok(dto);
        }

        catch (ExceptionFacturaNoEncotrada e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/DeleteDetalleFactura/{id}")
    public ResponseEntity<?> EliminarFactura(@PathVariable Long id){
        try{
            if(!Service.EliminarDetalleFactura(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                        "Mensaje de error", "Factura no encontrada").body(Map.of(
                        "Error","NOT FOUND",
                        "Mensaje","Detalle Factura no fue encontrada",
                        "Timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "Status","Proceso Completo",
                    "Message","Detalle Factura eliminada exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "Status","Error",
                    "Message", "Error al eliminar Detalle Factura",
                    "Detail", e.getMessage()
            ));
        }

    }
}
