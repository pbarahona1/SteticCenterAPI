package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.UnidadesEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepUnidades.ExcepcionUnidadNoEncontrada;
import BuSmart.APIBuSmart.Models.DTO.UnidadesDTO;
import BuSmart.APIBuSmart.Repositories.UnidadesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UnidadesService {

    @Autowired
    private UnidadesRepository repository;

    // Obtener todas las unidades
    public List<UnidadesDTO> obtenerTodasUnidades() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Crear nueva unidad
    public UnidadesDTO crearUnidad(UnidadesDTO dto) {
        UnidadesEntity entity = convertirAEntity(dto);
        UnidadesEntity guardada = repository.save(entity);
        return convertirADTO(guardada);
    }

    // Actualizar unidad existente
    public UnidadesDTO actualizarUnidad(Long id, UnidadesDTO dto) {
        if (!repository.existsById(id)) {
            throw new ExcepcionUnidadNoEncontrada("Unidad no encontrada con ID: " + id);
        }

        UnidadesEntity entity = convertirAEntity(dto);
        entity.setIdUnidad(id); // Aseguramos que se actualice el registro correcto
        UnidadesEntity actualizada = repository.save(entity);
        return convertirADTO(actualizada);
    }

    // Eliminar unidad
    public void eliminarUnidad(Long id) {
        if (!repository.existsById(id)) {
            throw new ExcepcionUnidadNoEncontrada("Unidad no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

    // Métodos de conversión
    private UnidadesDTO convertirADTO(UnidadesEntity entity) {
        UnidadesDTO dto = new UnidadesDTO();
        dto.setIdUnidad(entity.getIdUnidad());
        dto.setTipoUnidad(entity.getTipoUnidad());
        dto.setCapacidad(entity.getCapacidad());
        dto.setIdEstado(entity.getIdEstado());
        dto.setIdRuta(entity.getIdRuta());
        dto.setUnidades(entity.getUnidades());
        return dto;
    }

    private UnidadesEntity convertirAEntity(UnidadesDTO dto) {
        UnidadesEntity entity = new UnidadesEntity();
        entity.setTipoUnidad(dto.getTipoUnidad());
        entity.setCapacidad(dto.getCapacidad());
        entity.setIdEstado(dto.getIdEstado());
        entity.setIdRuta(dto.getIdRuta());
        entity.setUnidades(dto.getUnidades());
        return entity;
    }
}