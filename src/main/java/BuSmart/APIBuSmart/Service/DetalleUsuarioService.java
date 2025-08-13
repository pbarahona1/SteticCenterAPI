package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.DetalleUsuarioEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionRutaNoEncontrada;
import BuSmart.APIBuSmart.Models.DTO.DetalleUsuarioDTO;
import BuSmart.APIBuSmart.Repositories.DetalleUsuarioRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DetalleUsuarioService {

        @Autowired
        DetalleUsuarioRepository repo;

    public List<DetalleUsuarioDTO> obtenerDetalleUsuario() {
        List<DetalleUsuarioEntity> Ruta = repo.findAll();
        return Ruta.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    public DetalleUsuarioDTO InsertarDetalleUsuario(@Valid DetalleUsuarioDTO rut) {
        try{
            DetalleUsuarioEntity entity = ConvertirAEntity(rut);
            DetalleUsuarioEntity usuarioGuardado = repo.save(entity);
            return ConvertirADTO(usuarioGuardado);
        }catch (Exception e){
            log.error("Error al registrar Ruta: " + e.getMessage());
            throw new ExceptionRutaNoEncontrada("Error al registar el.");
        }


    }

    public DetalleUsuarioDTO ActualizarDetalleUsuario(Long id, DetalleUsuarioDTO json) {
        DetalleUsuarioEntity Existente = repo.findById(id).orElseThrow(() -> new ExceptionRutaNoEncontrada("Detalle de Usuario no encontrada"));
        Existente.setIdUsuario(json.getIdUsuario());
        Existente.setIdEspecialista(json.getIdEspecialista());
        DetalleUsuarioEntity RutaActualizada = repo.save(Existente);
        return ConvertirADTO(RutaActualizada);
    }

    public boolean EliminarDetalleUsuario(Long id) {
        try{
            DetalleUsuarioEntity objRuta = repo.findById(id).orElse(null);
            if (objRuta != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Detalle de usuario no encontrada.");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro el detalle de usuario con ID: " + id + " para eliminar.", 1);
        }
    }

    private DetalleUsuarioDTO ConvertirADTO(DetalleUsuarioEntity rutaEntity) {
        DetalleUsuarioDTO dto = new DetalleUsuarioDTO();
        dto.setIdDetalleUsuario(rutaEntity.getIdDetalleUsuario());
        dto.setIdUsuario(rutaEntity.getIdUsuario());
        dto.setIdEspecialista(rutaEntity.getIdEspecialista());
        return dto;
    }


    private DetalleUsuarioEntity ConvertirAEntity(DetalleUsuarioDTO data) {
        DetalleUsuarioEntity entity = new DetalleUsuarioEntity();
        entity.setIdDetalleUsuario(data.getIdDetalleUsuario());
        entity.setIdUsuario(data.getIdUsuario());
        entity.setIdEspecialista(data.getIdEspecialista());
        return entity;
    }
}
