package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.EntitieServicios;
import BuSmart.APIBuSmart.Entities.FacturasEntity;
import BuSmart.APIBuSmart.Entities.UserEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepCitas.ExceptionCitaNoEncontrada;
import BuSmart.APIBuSmart.Exceptions.ExcepFacrturas.ExceptionFacturaNoEncotrada;
import BuSmart.APIBuSmart.Exceptions.ExcepFacrturas.ExceptionFacturaNoRegistrada;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionsUsuarioNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.FacturasDTO;
import BuSmart.APIBuSmart.Models.DTO.UserDTO;
import BuSmart.APIBuSmart.Repositories.FacturasRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FacturasService {

    @Autowired
    FacturasRepository repo;
    public List<FacturasDTO> getFacturas() {
        try {
            List<FacturasEntity> factura = repo.findAll();
            return factura.stream().map(this::ConvertirFacturasADTO).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*Paginacion*/
    public Page<FacturasDTO> getallFacturas(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<FacturasEntity> productos = repo.findAll(pageable);

            return productos.map(this::ConvertirFacturasADTO);

        } catch (Exception e) {
            log.error("Error al listar facturas: " + e.getMessage(), e);
            throw new ExceptionFacturaNoEncotrada("Error al listar facturas: " + e.getMessage());
        }
    }


    public FacturasDTO insertFactura(@Valid FacturasDTO json) {
            if(json == null){
                throw new IllegalArgumentException("la factura no puede ser nula");
            }
            try{
                FacturasEntity entity = ConvertirAFacturaEntity(json);
                FacturasEntity respuesta = repo.save(entity);
                return ConvertirFacturasADTO(respuesta);
            }catch (Exception e){
                log.error("Error al registrar Factura: " + e.getMessage());
                throw new ExceptionFacturaNoRegistrada("error al registrar factura.");
            }
    }

    public FacturasDTO ActualizarFactura(Long id, @Valid FacturasDTO json) {
        FacturasEntity Facturas = repo.findById(id).orElseThrow(() -> new ExceptionFacturaNoEncotrada("Factura no encontrada"));
        Facturas.setIdCliente(json.getIdCliente());
        Facturas.setFecha(json.getFecha());
        Facturas.setTotal(json.getTotal());
        Facturas.setEstado(json.getEstado());
        FacturasEntity FacturaActualizada = repo.save(Facturas);
        return ConvertirFacturasADTO(FacturaActualizada);
    }

    public boolean EliminarFactura(Long id) {
        try{
            FacturasEntity Factura = repo.findById(id).orElse(null);
            if(Factura != null){
                repo.deleteById(id);
                return true;
            }else {
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro la Factrura con el id " + id + "para eliminar. ", 1);
        }
    }


    private FacturasDTO ConvertirFacturasADTO(FacturasEntity Factura) {
        FacturasDTO dto = new FacturasDTO();
        dto.setIdFactura(Factura.getIdFactura());
        dto.setIdCliente(Factura.getIdCliente());
        dto.setFecha(Factura.getFecha());
        dto.setTotal(Factura.getTotal());
        dto.setEstado(Factura.getEstado());
        return dto;
    }

    private FacturasEntity ConvertirAFacturaEntity(FacturasDTO dto){
        FacturasEntity Factura = new FacturasEntity();
        Factura.setIdFactura(dto.getIdFactura());
        Factura.setIdCliente(dto.getIdCliente());
        Factura.setFecha(dto.getFecha());
        Factura.setTotal(dto.getTotal());
        Factura.setEstado(dto.getEstado());
        return Factura;
    }


}
