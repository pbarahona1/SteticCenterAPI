package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.EntitieServicios;
import BuSmart.APIBuSmart.Entities.EntitieUsuarioServicios;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarioServicios.ExceptionUsuarioServicioNoRegistrado;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarioServicios.ExceptionUsuarioServiciosNoEncontrado;
import BuSmart.APIBuSmart.Exceptions.ExceptionsServicios.ExceptionServiciosNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.ServiciosDTO;
import BuSmart.APIBuSmart.Models.DTO.UsuarioServiciosDTO;
import BuSmart.APIBuSmart.Repositories.RepositoryUsuarioServicios;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServicesUsuarioServicio {

    @Autowired
    RepositoryUsuarioServicios repo;

    public List<UsuarioServiciosDTO> ObtenerUsuariosServicios(){
        List<EntitieUsuarioServicios> servicios = repo.findAll();
        return servicios.stream().map(this::ConvertirADTO).collect(Collectors.toList());
    }

    public UsuarioServiciosDTO ConvertirADTO(EntitieUsuarioServicios entitieUsuarioServicios){
        UsuarioServiciosDTO dto = new UsuarioServiciosDTO();

        dto.setIdUsuarioServicio(entitieUsuarioServicios.getIdUsuarioServicio());
        dto.setIdServicio(entitieUsuarioServicios.getIdServicio());
        dto.setIdUsuario(entitieUsuarioServicios.getIdUsuario());

        return dto;
    }

    public UsuarioServiciosDTO InsertarUsuarioServicios(UsuarioServiciosDTO data){
        try {
            EntitieUsuarioServicios entity = convertirAEntity(data);
            EntitieUsuarioServicios ServicioGuardado = repo.save(entity);
            return ConvertirADTO(ServicioGuardado);
        }catch (Exception e){
            log.error("Error al registrar el servicio" + e.getMessage());
            throw new ExceptionUsuarioServicioNoRegistrado("Error al registrar servicio.");
        }
    }

    public EntitieUsuarioServicios convertirAEntity(UsuarioServiciosDTO data){

        EntitieUsuarioServicios entity = new EntitieUsuarioServicios();

        entity.setIdServicio(data.getIdServicio());
        entity.setIdUsuario(data.getIdUsuario());

        return entity;
    }

    public UsuarioServiciosDTO ActualizarUsuariosServicios(long id, @Valid UsuarioServiciosDTO json){
        EntitieUsuarioServicios existente = repo.findById(id).orElseThrow(() -> new ExceptionUsuarioServiciosNoEncontrado("Proveedor no encontrado"));

        existente.setIdServicio(json.getIdServicio());
        existente.setIdUsuario(json.getIdUsuario());


        EntitieUsuarioServicios ServicioActualizado = repo.save(existente);
        return ConvertirADTO(ServicioActualizado);
    }


    public boolean EliminarUsuarioServicio (Long id){
        try {
            EntitieUsuarioServicios ServicioExiste = repo.findById(id).orElse(null);
            if (ServicioExiste != null){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro el UsuarioServicio con el id: "+ id +"para eliminar.", 1);
        }
    }

}
