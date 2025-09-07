package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.EntitieServicios;
import BuSmart.APIBuSmart.Exceptions.ExceptionsServicios.ExceptionServicioNoRegistrado;
import BuSmart.APIBuSmart.Exceptions.ExceptionsServicios.ExceptionServiciosNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.ServiciosDTO;
import BuSmart.APIBuSmart.Repositories.RepositoryServicios;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class ServiceServicios {

    @Autowired
    RepositoryServicios repo;

    public List<ServiciosDTO> ObtenerServicios(){
        List<EntitieServicios> Servicios = repo.findAll();
        return Servicios.stream().map(this::ConvertirADTO).collect(Collectors.toList());
    }

    public ServiciosDTO ConvertirADTO(EntitieServicios entitieServicios){
        ServiciosDTO dto = new ServiciosDTO();

        dto.setIdServicio(entitieServicios.getIdServicio());
        dto.setNombre(entitieServicios.getNombre());
        dto.setPrecio(entitieServicios.getPrecio());
        dto.setDuracion_min(entitieServicios.getDuracion_min());
        dto.setImgUrl(entitieServicios.getImgUrl());
        dto.setImgUrl(entitieServicios.getImgUrl());

        return dto;

    }

    public ServiciosDTO InsertarServicios(ServiciosDTO data){
        if (data == null || data.getNombre().isEmpty()){
            throw new IllegalArgumentException("El nombre del servicio no puede ser nulo.");
        }
        try {
            EntitieServicios entity = convertirAEntity(data);
            EntitieServicios ServicioGuardado = repo.save(entity);
            return ConvertirADTO(ServicioGuardado);
        }catch (Exception e){
            log.error("Error al registrar el servicio" + e.getMessage());
            throw new ExceptionServicioNoRegistrado("Error al registrar servicio.");
        }
    }

    public EntitieServicios convertirAEntity(ServiciosDTO data){
        EntitieServicios entity = new EntitieServicios();

        entity.setIdServicio(data.getIdServicio());
        entity.setNombre(data.getNombre());
        entity.setPrecio(data.getPrecio());
        entity.setDuracion_min(data.getDuracion_min());
        entity.setImgUrl(data.getImgUrl());

        return entity;
    }


    public ServiciosDTO ActualizarServicios(long id, @Valid ServiciosDTO json){
        EntitieServicios existente = repo.findById(id).orElseThrow(() -> new ExceptionServiciosNoEncontrado("Proveedor no encontrado"));

        existente.setNombre(json.getNombre());
        existente.setPrecio(json.getPrecio());
        existente.setDuracion_min(json.getDuracion_min());
        existente.setImgUrl(json.getImgUrl());

        EntitieServicios ServicioActualizado = repo.save(existente);
        return ConvertirADTO(ServicioActualizado);
    }


    public boolean EliminarServicio (Long id){
        try {
            EntitieServicios ServicioExiste = repo.findById(id).orElse(null);
            if (ServicioExiste != null){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro el servicio con el id: "+ id +"para eliminar.", 1);
        }
    }

}
