package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExcepcionDatosDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionRutaNoEncontrada;
import BuSmart.APIBuSmart.Models.DTO.RutaDTO;
import BuSmart.APIBuSmart.Models.DTO.TipoEncargadoDTO;
import BuSmart.APIBuSmart.Service.RutaService;
import BuSmart.APIBuSmart.Service.TipoEncargadoService;
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
@RequestMapping("/apiTipoEncargado")
public class TipoEncargadoController {

    @Autowired
    TipoEncargadoService TService;

    @GetMapping("/ConsultarTipoEncargado")
    public List<TipoEncargadoDTO> obtenerDatos(){return  TService.obtenerTipoEncargado();}


    @PostMapping("/RegistrarTipoEncargado")
    public ResponseEntity<?> nuevoTipoEncargado(@Valid @RequestBody TipoEncargadoDTO json, HttpServletRequest request){
        try{
            TipoEncargadoDTO respuesta = TService.InsertarTipoEncargado(json);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserccion fallida",
                        "errorType", "VALIDACION_ERROR",
                        "message", "el tipo de encargado no pudo ser registrados"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "Success",
                    "data", respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "Error",
                            "message", "Error no controlado al regitrar el tipo de encargado",
                            "detail", e.getMessage()
                    ));
        }

    }

    @PutMapping("/EditarTipoEncargado{id}")
    public ResponseEntity<?> modificarTipoEncargado(
            @PathVariable Long id,
            @Valid @RequestBody TipoEncargadoDTO json,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try{
            TipoEncargadoDTO dto = TService.ActualizarTipoDeEncargado(id, json);
            return ResponseEntity.ok(dto);
        }catch (ExceptionRutaNoEncontrada e){
            return ResponseEntity.notFound().build();
        }catch (ExcepcionDatosDuplicados e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("Error", "Datos duplicados",
                            "Campo", e.getCampoDuplicado())
            );
        }
    }


    @DeleteMapping("/EliminarTipoEncargado/{id}")
    public  ResponseEntity<?> EliminarTipoEncargado(
            @PathVariable Long id)
    {
        try{
            if (!TService.EliminarTipoEncargado(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje de error", "Tipo Encargado no Encontrada")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensaje", "El Ti poEncargado no fue encontrada",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "message", "Tipo Encargado eliminada exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar el TipoEncargado",
                    "detail", e.getMessage()
            ));
        }
    }
}
