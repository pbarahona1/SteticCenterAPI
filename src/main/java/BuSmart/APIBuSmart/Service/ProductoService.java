package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.ProductoEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepProducto.*;
import BuSmart.APIBuSmart.Models.DTO.ProductoDTO;
import BuSmart.APIBuSmart.Repositories.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoDTO> getAllProductos() {
        try {
            List<ProductoEntity> productos = productoRepository.findAll();
            if (productos.isEmpty()) {
                throw new ExcepcionProductoNoRegistrado("No hay productos registrados en el sistema");
            }
            return productos.stream()
                    .map(this::convertirAProductoDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al listar productos: " + e.getMessage(), e);
            throw new ExcepcionProductoNoEncontrado("Error al listar productos: " + e.getMessage());
        }
    }

    public ProductoDTO insertProducto(ProductoDTO productoDTO) {
        if (productoRepository.existsByNombre(productoDTO.getNombre())) {
            throw new ExcepcionProductoDuplicado("nombre", "Ya existe un producto con ese nombre");
        }

        try {
            ProductoEntity productoEntity = convertirAProductoEntity(productoDTO);
            ProductoEntity productoGuardado = productoRepository.save(productoEntity);
            return convertirAProductoDTO(productoGuardado);
        } catch (Exception e) {
            log.error("Error al registrar producto: " + e.getMessage());
            throw new ExcepcionProductoNoRegistrado("Error al registrar el producto: " + e.getMessage());
        }
    }

    public ProductoDTO updateProducto(int id, ProductoDTO productoDTO) {
        ProductoEntity productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ExcepcionProductoNoEncontrado("Producto no encontrado con ID: " + id));

        if (!productoExistente.getNombre().equals(productoDTO.getNombre()) &&
                productoRepository.existsByNombre(productoDTO.getNombre())) {
            throw new ExcepcionProductoDuplicado("nombre", "Ya existe un producto con ese nombre");
        }

        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setPrecio(productoDTO.getPrecio());
        productoExistente.setStock(productoDTO.getStock());

        ProductoEntity productoActualizado = productoRepository.save(productoExistente);
        return convertirAProductoDTO(productoActualizado);
    }

    public void deleteProducto(int id) {
        try {
            if (!productoRepository.existsById(id)) {
                throw new ExcepcionProductoNoEncontrado("Producto no encontrado con ID: " + id);
            }
            productoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcepcionProductoNoEncontrado("No se encontró producto con ID: " + id);
        }
    }

    private ProductoDTO convertirAProductoDTO(ProductoEntity producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        return dto;
    }

    private ProductoEntity convertirAProductoEntity(ProductoDTO dto) {
        ProductoEntity producto = new ProductoEntity();
        producto.setIdProducto(dto.getIdProducto());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        return producto;
    }
}