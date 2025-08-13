package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.TipoUsuarioEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepUnidades.ExcepcionUnidadNoEncontrada;
import BuSmart.APIBuSmart.Models.DTO.TipoUsuarioDTO;
import BuSmart.APIBuSmart.Repositories.TipoUsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TipoUsuarioService {

    @Autowired
    TipoUsuarioRepository repository;

    public List<TipoUsuarioDTO> ObtenerTiposUsuario() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public TipoUsuarioDTO CrearTipoUsuario(TipoUsuarioDTO dto) {
        Optional<TipoUsuarioEntity> existente = repository.findByNombreTipoIgnoreCase(dto.getNombreTipo());
        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe un tipo de usuario con ese nombre.");
        }
        TipoUsuarioEntity entity = convertirAEntity(dto);
        TipoUsuarioEntity guardada = repository.save(entity);
        return convertirADTO(guardada);
    }


    public TipoUsuarioDTO actualizarTipoUsuario(Long id, TipoUsuarioDTO dto) {
        if (!repository.existsById(id)) {
            throw new ExcepcionUnidadNoEncontrada("El tipo de usuario no fue encontrada con el ID: " + id);
        }

        TipoUsuarioEntity entity = convertirAEntity(dto);
        entity.setIdTipoUsuario(id); // Aseguramos que se actualice el registro correcto
        TipoUsuarioEntity actualizada = repository.save(entity);
        return convertirADTO(actualizada);
    }

    public void eliminarTipoUnidad(Long id) {
        if (!repository.existsById(id)) {
            throw new ExcepcionUnidadNoEncontrada("El tipo de usuario no fue encontrada con el ID:  " + id);
        }
        repository.deleteById(id);
    }

    // Métodos de conversión
    private TipoUsuarioDTO convertirADTO(TipoUsuarioEntity entity) {
        TipoUsuarioDTO dto = new TipoUsuarioDTO();
        dto.setIdTipoUsuario(entity.getIdTipoUsuario());
        dto.setNombreTipo(entity.getNombreTipo());
        return dto;
    }

    private TipoUsuarioEntity convertirAEntity(TipoUsuarioDTO dto) {
        TipoUsuarioEntity entity = new TipoUsuarioEntity();
        entity.setIdTipoUsuario(dto.getIdTipoUsuario());
        entity.setNombreTipo(dto.getNombreTipo());
        return entity;
    }
}