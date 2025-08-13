package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.EspecialistaEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepEspecialista.ExceptionEspecialidadDuplicados;
import BuSmart.APIBuSmart.Exceptions.ExcepEspecialista.ExceptionEspecialistaNoRegistrado;
import BuSmart.APIBuSmart.Models.DTO.EspecialistaDTO;
import BuSmart.APIBuSmart.Repositories.EspecialistaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EspecialistaService {


    @Autowired
    EspecialistaRepository repo;

    public List<EspecialistaDTO> obtenerEspecialista() {
        List<EspecialistaEntity> lista = repo.findAll();
        return lista.stream().map(this::convertirADTO).collect(Collectors.toList());

    }


    public EspecialistaDTO InsertarTipoEspecialista(EspecialistaDTO data) {
        if (data == null || data.getEspecialidad().isEmpty()) {
            throw new IllegalArgumentException("Tipo Especialista no puede ser nulo");
        }
        String especialidad = data.getEspecialidad().trim();
        if (repo.existsByEspecialidadIgnoreCase(especialidad)) {
            throw new ExceptionEspecialidadDuplicados("especialidad");
        }
        data.setEspecialidad(especialidad);
        try {
            EspecialistaEntity entity = convertirAEntity(data);
            EspecialistaEntity EncargadoGuardado = repo.save(entity);
            return convertirADTO(EncargadoGuardado);
        } catch (Exception e) {
            log.error("Error al registrar tipo Especialista" + e.getMessage());
            throw new ExceptionEspecialistaNoRegistrado("Error al registrar especialista.");
        }
    }


    public EspecialistaDTO ActualizarTipoDeEncargado(Long id, @Valid EspecialistaDTO json) {
        EspecialistaEntity existente = repo.findById(id)
                .orElseThrow(() -> new ExceptionEspecialistaNoRegistrado("especialista no encontrado"));
        String nuevaEspecialidad = json.getEspecialidad().trim();
        if (!existente.getEspecialidad().equalsIgnoreCase(nuevaEspecialidad) &&
                repo.existsByEspecialidadIgnoreCase(nuevaEspecialidad)) {
            throw new ExceptionEspecialidadDuplicados("especialidad");
        }
        existente.setEspecialidad(nuevaEspecialidad);
        EspecialistaEntity TipoEncargadoActualizado = repo.save(existente);
        return convertirADTO(TipoEncargadoActualizado);
    }

    public boolean EliminarTipoEncargado(Long id) {
        try {
            EspecialistaEntity tipoEncargadoExiste = repo.findById(id).orElse(null);
            if (tipoEncargadoExiste != null){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw  new EmptyResultDataAccessException("No se encontro el tipo especialista con el id: " + id + "para eliminar. ", 1);
        }
    }

    private EspecialistaDTO convertirADTO(EspecialistaEntity tipoEncargadoEntity) {
        //Creando objeto a retornar
        EspecialistaDTO dto = new EspecialistaDTO();
        //Transferir los datos al dto
        dto.setIdEspecialista(tipoEncargadoEntity.getIdEspecialista());
        dto.setEspecialidad(tipoEncargadoEntity.getEspecialidad());
        return dto;

    }

    private EspecialistaEntity convertirAEntity(EspecialistaDTO data){
        EspecialistaEntity entity = new EspecialistaEntity();
        entity.setIdEspecialista(data.getIdEspecialista());
        entity.setEspecialidad(data.getEspecialidad());
        return entity;
    }

}
