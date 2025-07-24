package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.EstadoEntity;
import BuSmart.APIBuSmart.Entities.RutaEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionRutaNoEncontrada;
import BuSmart.APIBuSmart.Models.DTO.EstadoDTO;
import BuSmart.APIBuSmart.Models.DTO.RutaDTO;
import BuSmart.APIBuSmart.Repositories.EstadoRepository;
import BuSmart.APIBuSmart.Repositories.RutaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EstadoService {

    @Autowired
    EstadoRepository rep;


    public List<EstadoDTO> obtenerEstado() {
        List<EstadoEntity> Ruta = rep.findAll();
        return Ruta.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    public EstadoDTO InsertarEstado(@Valid EstadoDTO json) {
        try{
            EstadoEntity entity = ConvertirAEntity(json);
            EstadoEntity EstadoGuardado = rep.save(entity);
            return ConvertirADTO(EstadoGuardado);
        }catch (Exception e){
            log.error("Error al registrar Estado: " + e.getMessage());
            throw new ExceptionRutaNoEncontrada("Error al registar el.");
        }
    }


    public boolean QuitarEstado(Long id) {
        try{
            EstadoEntity objEstado = rep.findById(id).orElse(null);
            if (objEstado != null){
                rep.deleteById(id);
                return true;
            }else{
                System.out.println("Estado no encontrada.");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro el Estado con ID: " + id + " para eliminar.", 1);
        }
    }

    private EstadoDTO ConvertirADTO(EstadoEntity estadoEntity) {
        EstadoDTO dto = new EstadoDTO();
        dto.setIdEstado(estadoEntity.getIdEstado());
        dto.setTipoEstado(estadoEntity.getTipoEstado());
        return dto;
    }

    private EstadoEntity ConvertirAEntity(EstadoDTO data) {
        EstadoEntity entity = new EstadoEntity();
        entity.setTipoEstado(data.getTipoEstado());
        return entity;
    }

    public EstadoDTO ActualizarEstado(Long id, @Valid EstadoDTO json) {
        EstadoEntity EstadoExistente = rep.findById(id).orElseThrow(() -> new ExceptionRutaNoEncontrada("Estado no encontrada"));
        EstadoExistente.setTipoEstado(json.getTipoEstado());
        EstadoEntity EstadoActualizada = rep.save(EstadoExistente);
        return ConvertirADTO(EstadoActualizada);
    }
}
