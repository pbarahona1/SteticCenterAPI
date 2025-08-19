package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.DetalleFacturaEntity;
import BuSmart.APIBuSmart.Entities.FacturasEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepDetalleFactura.ExceptionDetalleFacturaNoEncontrado;
import BuSmart.APIBuSmart.Exceptions.ExcepFacrturas.ExceptionFacturaNoEncotrada;
import BuSmart.APIBuSmart.Exceptions.ExcepFacrturas.ExceptionFacturaNoRegistrada;
import BuSmart.APIBuSmart.Models.DTO.DetalleFacturaDTO;
import BuSmart.APIBuSmart.Repositories.DetalleFacturaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DetalleFacturaService {
    @Autowired
    DetalleFacturaRepository repo;

    public List<Object> getDetalleFacturas() {
        try{
            List<DetalleFacturaEntity> Facturas = repo.findAll();
            return Facturas.stream()
                    .map(this::ConvertirDetalleFacturasADTO)
                    .collect(Collectors.toList());
        } catch (Exception e){
            log.error("error al lista facturas" + e.getMessage(), e);
            throw new ExceptionFacturaNoEncotrada("error al listar Facturas: " + e.getMessage());
        }
    }

    public DetalleFacturaDTO insertDetalleFactura(@Valid DetalleFacturaDTO json) {
        if(json == null){
            throw new IllegalArgumentException("la factura no puede ser nula");
        }
        try{
            DetalleFacturaEntity entity = ConvertirADetalleFacturaEntity(json);
            DetalleFacturaEntity respuesta = repo.save(entity);
            return (DetalleFacturaDTO) ConvertirDetalleFacturasADTO(respuesta);
        }catch (Exception e){
            log.error("Error al registrar Factura: " + e.getMessage());
            throw new ExceptionFacturaNoRegistrada("error al registrar factura.");
        }
    }

    private DetalleFacturaEntity ConvertirADetalleFacturaEntity(@Valid DetalleFacturaDTO json) {
        DetalleFacturaEntity entity = new DetalleFacturaEntity();
        entity.setIdFactura(json.getIdFactura());
        entity.setIdCita(json.getIdCita());
        entity.setIdProducto(json.getIdProducto());
        entity.setCantidad(json.getCantidad());
        entity.setPrecioUnitario(json.getPrecioUnitario());
        return entity;
    }

    public DetalleFacturaDTO ActualizarDetalleFactura(Long id, @Valid DetalleFacturaDTO json) {
        DetalleFacturaEntity entity = repo.findById(id).orElseThrow(() -> new ExceptionDetalleFacturaNoEncontrado("Detalle Factura no encotrada."));
        entity.setIdFactura(json.getIdFactura());
        entity.setIdCita(json.getIdCita());
        entity.setIdProducto(json.getIdProducto());
        entity.setCantidad(json.getCantidad());
        entity.setPrecioUnitario(json.getPrecioUnitario());
        DetalleFacturaEntity Actualizada = repo.save(entity);
        return (DetalleFacturaDTO) ConvertirDetalleFacturasADTO(Actualizada);
    }

    public boolean EliminarDetalleFactura(Long id) {
        try{
            DetalleFacturaEntity entity = repo.findById(id).orElse(null);
            if(entity != null){
                repo.deleteById(id);
                return true;
            }else {
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro Detalle Factrura con el id " + id + "para eliminar. ", 1);
        }
    }

    private Object ConvertirDetalleFacturasADTO(DetalleFacturaEntity entity) {
        DetalleFacturaDTO dto = new DetalleFacturaDTO();
        dto.setIdDetalleFactura(entity.getIdDetalleFactura());
        dto.setIdFactura(entity.getIdFactura());
        dto.setIdCita(entity.getIdCita());
        dto.setIdProducto(entity.getIdProducto());
        dto.setCantidad(entity.getCantidad());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        return dto;
    }



}