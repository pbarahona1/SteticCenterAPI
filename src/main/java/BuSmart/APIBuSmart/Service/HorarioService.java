package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.HorarioEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionRutaNoEncontrada;
import BuSmart.APIBuSmart.Models.DTO.HorarioDTO;
import BuSmart.APIBuSmart.Repositories.HorarioRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HorarioService {

    @Autowired
    HorarioRepository rep;


    public List<HorarioDTO> obtenerHorario() {
        List<HorarioEntity> Ruta = rep.findAll();
        return Ruta.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    public HorarioDTO InsertarHorario(@Valid HorarioDTO json) {
        try{
            HorarioEntity entity = ConvertirAEntity(json);
            HorarioEntity EstadoGuardado = rep.save(entity);
            return ConvertirADTO(EstadoGuardado);
        }catch (Exception e){
            log.error("Error al registrar Horario: " + e.getMessage());
            throw new ExceptionRutaNoEncontrada("Error al registar el.");
        }
    }


    public boolean QuitarHorario(Long id) {
        try{
            HorarioEntity objEstado = rep.findById(id).orElse(null);
            if (objEstado != null){
                rep.deleteById(id);
                return true;
            }else{
                System.out.println("Horario no encontrada.");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro el Horario con ID: " + id + " para eliminar.", 1);
        }
    }

    private HorarioDTO ConvertirADTO(HorarioEntity estadoEntity) {
        HorarioDTO dto = new HorarioDTO();
        dto.setIdHorario(estadoEntity.getIdHorario());
        dto.setDescripcion(estadoEntity.getDescripcion());
        return dto;
    }

    private HorarioEntity ConvertirAEntity(HorarioDTO data) {
        HorarioEntity entity = new HorarioEntity();
        entity.setIdHorario(data.getIdHorario());
        entity.setDescripcion(data.getDescripcion());
        return entity;
    }

    public HorarioDTO ActualizarHorario(Long id, @Valid HorarioDTO json) {
        HorarioEntity EstadoExistente = rep.findById(id).orElseThrow(() -> new ExceptionRutaNoEncontrada("Horario no encontrada"));
        EstadoExistente.setDescripcion(json.getDescripcion());
        HorarioEntity EstadoActualizada = rep.save(EstadoExistente);
        return ConvertirADTO(EstadoActualizada);
    }
}
