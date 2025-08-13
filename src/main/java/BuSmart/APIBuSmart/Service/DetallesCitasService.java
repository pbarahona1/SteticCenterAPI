package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.CitasEntity;
import BuSmart.APIBuSmart.Entities.DetallesCitasEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepCitas.ExcepcionCitasDuplicadas;
import BuSmart.APIBuSmart.Exceptions.ExcepCitas.ExceptionCitaNoEncontrada;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionEncargadoNoRegistrado;
import BuSmart.APIBuSmart.Models.DTO.CitasDTO;
import BuSmart.APIBuSmart.Models.DTO.DetallesCitasDTO;
import BuSmart.APIBuSmart.Repositories.CitasRepository;
import BuSmart.APIBuSmart.Repositories.DetallesCitasRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DetallesCitasService {

    @Autowired
    DetallesCitasRepository repocita;

    public List<DetallesCitasDTO> obtenerCitas() {
        List<DetallesCitasEntity> lista = repocita.findAll();
        return lista.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    public DetallesCitasDTO insertarCitas(@Valid DetallesCitasDTO json) {
        if (json == null){
            throw new IllegalArgumentException("el detalle de cita no puede ser nulo");
        }

        try {
            DetallesCitasEntity entity = ConvertirAEntity(json);
            DetallesCitasEntity respuesta = repocita.save(entity);
            return ConvertirADTO(respuesta);
        } catch (Exception e) {
            log.error("Error al registrar el detalle de cita: " + e.getMessage());
            throw new ExceptionEncargadoNoRegistrado("Error al registrar el detalle de cita");
        }
    }



    public DetallesCitasDTO actualizarCita(Long id, @Valid DetallesCitasDTO json) {
        DetallesCitasEntity citaExistente = repocita.findById(id)
                .orElseThrow(() -> new ExceptionCitaNoEncontrada("detalle de cita no encontrada"));

        citaExistente.setIdCita(json.getIdCita());
        citaExistente.setObservaciones(json.getObservaciones());
        DetallesCitasEntity citaActualizada = repocita.save(citaExistente);
        return ConvertirADTO(citaActualizada);
    }


    public boolean removerCitas(long id) {
        try{
            DetallesCitasEntity objUsuario = repocita.findById(id).orElse(null);
            if (objUsuario != null){
                repocita.deleteById(id);
                return true;
            }else{
                System.out.println("detalle de cita no encontrado.");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro el detalle de cita con ID: " + id + " para eliminar.", 1);
        }
    }

    private DetallesCitasDTO ConvertirADTO(DetallesCitasEntity encargadoEntity) {
        DetallesCitasDTO dto = new DetallesCitasDTO();
        dto.setIdDetalleCita(encargadoEntity.getIdDetalleCita());
        dto.setIdCita(encargadoEntity.getIdCita());
        dto.setObservaciones(encargadoEntity.getObservaciones());
        return dto;
    }

    private DetallesCitasEntity ConvertirAEntity(@Valid DetallesCitasDTO json) {
        DetallesCitasEntity entity = new DetallesCitasEntity();
        entity.setIdDetalleCita(json.getIdDetalleCita() );
        entity.setIdCita(json.getIdCita());
        entity.setObservaciones(json.getObservaciones());
        return entity;
    }
}
