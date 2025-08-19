package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Exceptions.ExcepProducto.*;
import BuSmart.APIBuSmart.Models.DTO.ProductoDTO;
import BuSmart.APIBuSmart.Service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/GetProductos")
    public ResponseEntity<?> getAllProductos() {
        try {
            List<ProductoDTO> productos = productoService.getAllProductos();
            return ResponseEntity.ok(productos);
        } catch (ExcepcionProductoNoRegistrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "info",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al obtener productos",
                    "detail", e.getMessage()
            ));
        }
    }

    @PostMapping("/PostProductos")
    public ResponseEntity<?> createProducto(@Valid @RequestBody ProductoDTO productoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            ProductoDTO productoCreado = productoService.insertProducto(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", productoCreado));
        } catch (ExcepcionProductoDuplicado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos duplicados",
                    "campo", e.getCampoDuplicado(),
                    "message", e.getMessage()
            ));
        } catch (ExcepcionProductoNoRegistrado e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }

    @PutMapping("/PutProductos/{id}")
    public ResponseEntity<?> updateProducto(
            @PathVariable int id,
            @Valid @RequestBody ProductoDTO productoDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            ProductoDTO productoActualizado = productoService.updateProducto(id, productoDTO);
            return ResponseEntity.ok(productoActualizado);
        } catch (ExcepcionProductoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        } catch (ExcepcionProductoDuplicado e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos duplicados",
                    "campo", e.getCampoDuplicado(),
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/DeleteProductos/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable int id) {
        try {
            productoService.deleteProducto(id);
            return ResponseEntity.ok().body(Map.of(
                    "status", "success",
                    "message", "Producto eliminado exitosamente"
            ));
        } catch (ExcepcionProductoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Error al eliminar producto",
                    "detail", e.getMessage()
            ));
        }
    }
}