package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.CitasEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepCitas.ExcepcionCitasDuplicadas;
import BuSmart.APIBuSmart.Exceptions.ExcepCitas.ExceptionCitaNoEncontrada;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionEncargadoNoEncontrado;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionEncargadoNoRegistrado;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionsUsuarioNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.CitasDTO;
import BuSmart.APIBuSmart.Repositories.CitasRepository;
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
public class CitasService {

    @Autowired
    CitasRepository repocita;

    public List<CitasDTO> obtenerCitas() {
        List<CitasEntity> lista = repocita.findAll();
        return lista.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    /*Paginada*/
    public Page<CitasDTO> getAllUsers(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<CitasEntity> cita = repocita.findAll(pageable);

            return cita.map(this::ConvertirADTO);

        } catch (Exception e) {
            log.error("Error al listar citas: " + e.getMessage(), e);
            throw new ExceptionCitaNoEncontrada("Error al listar citas: " + e.getMessage());
        }
    }

    public CitasDTO insertarCitas(@Valid CitasDTO json) {
        if (json == null){
            throw new IllegalArgumentException("La cita no puede ser nula");
        }

        // Validación para evitar citas duplicadas para mismo usuario en fecha y horario
        boolean yaExiste = repocita.existsByIdUsuarioAndIdHorarioAndFechaCita(
                json.getIdUsuario(), json.getIdHorario(), json.getFecha_cita()
        );

        if (yaExiste) {
            throw new ExcepcionCitasDuplicadas("El usuario ya tiene una cita programada en esa fecha y horario", "idUsuario/idHorario/fecha_cita");
        }

        try {
            CitasEntity entity = ConvertirAEntity(json);
            CitasEntity respuesta = repocita.save(entity);
            return ConvertirADTO(respuesta);
        } catch (Exception e) {
            log.error("Error al registrar la cita: " + e.getMessage());
            throw new ExceptionEncargadoNoRegistrado("Error al registrar cita");
        }
    }


    public CitasDTO actualizarCita(Long id, @Valid CitasDTO json) {
        CitasEntity citaExistente = repocita.findById(id)
                .orElseThrow(() -> new ExceptionCitaNoEncontrada("Cita no encontrada"));

        boolean yaExiste = repocita.existsByIdUsuarioAndIdHorarioAndFechaCita(
                json.getIdUsuario(), json.getIdHorario(), json.getFecha_cita()
        );

        if (yaExiste && citaExistente.getIdCita() != json.getIdCita()) {
            throw new ExcepcionCitasDuplicadas(
                    "El usuario ya tiene una cita programada en esa fecha y horario",
                    "idUsuario/idHorario/fecha_cita"
            );
        }

        citaExistente.setIdUsuario(json.getIdUsuario());
        citaExistente.setIdCliente(json.getIdCliente());
        citaExistente.setIdHorario(json.getIdHorario());
        citaExistente.setFechaCita(json.getFecha_cita());
        citaExistente.setEstado(json.getEstado());

        CitasEntity citaActualizada = repocita.save(citaExistente);
        return ConvertirADTO(citaActualizada);
    }

    public boolean removerCitas(long id) {
        try{
            CitasEntity objUsuario = repocita.findById(id).orElse(null);
            if (objUsuario != null){
                repocita.deleteById(id);
                return true;
            }else{
                System.out.println("cita no encontrado.");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro la cita con ID: " + id + " para eliminar.", 1);
        }
    }

    private CitasDTO ConvertirADTO(CitasEntity encargadoEntity) {
        CitasDTO dto = new CitasDTO();
        dto.setIdCita(encargadoEntity.getIdCita());
        dto.setIdUsuario(encargadoEntity.getIdUsuario());
        dto.setIdCliente(encargadoEntity.getIdCliente());
        dto.setIdHorario(encargadoEntity.getIdHorario());
        dto.setFecha_cita(encargadoEntity.getFechaCita());
        dto.setEstado(encargadoEntity.getEstado());
        return dto;
    }

    private CitasEntity ConvertirAEntity(@Valid CitasDTO json) {
        CitasEntity entity = new CitasEntity();
        entity.setIdCita(json.getIdCita() );
        entity.setIdUsuario(json.getIdUsuario());
        entity.setIdCliente(json.getIdCliente());
        entity.setIdHorario(json.getIdHorario());
        entity.setFechaCita(json.getFecha_cita());
        entity.setEstado(json.getEstado());
        return entity;
    }
}
