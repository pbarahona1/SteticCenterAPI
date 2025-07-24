package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.EncargadoEntity;
import BuSmart.APIBuSmart.Entities.UserEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionEncargadoNoEncontrado;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionEncargadoNoRegistrado;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionsUsuarioNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.EncargadoDTO;
import BuSmart.APIBuSmart.Repositories.EncargadoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EncargadoService {

    @Autowired
    EncargadoRepository repoEncargado;

    public List<EncargadoDTO> obtenerEncargados() {
        List<EncargadoEntity> lista = repoEncargado.findAll();
        return lista.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    private EncargadoDTO ConvertirADTO(EncargadoEntity encargadoEntity) {
        EncargadoDTO dto = new EncargadoDTO();
        dto.setIdEncargado(encargadoEntity.getIdEncargado());
        dto.setNombre(encargadoEntity.getNombre());
        dto.setNumero(encargadoEntity.getNumero());
        dto.setEdad(encargadoEntity.getEdad());
        dto.setDUI(encargadoEntity.getDUI());
        dto.setCorreo(encargadoEntity.getCorreo());
        dto.setIdUsuario(encargadoEntity.getIdUsuario());
        dto.setIdTipoEncargado(encargadoEntity.getIdTipoEncargado());
        return dto;
    }

    public EncargadoDTO insertarEncargado(@Valid EncargadoDTO json) {
        if (json == null){
            throw new IllegalArgumentException("Encargado no puede ser nulo");
        }

        try{
            EncargadoEntity entity = ConvertirAEntity(json);
            EncargadoEntity respuesta = repoEncargado.save(entity);
            return ConvertirADTO(respuesta);
        }catch (Exception e){
            log.error("error al registrar encargado" + e.getMessage());
            throw new ExceptionEncargadoNoRegistrado("error al registrar encargado");
        }
    }

    private EncargadoEntity ConvertirAEntity(@Valid EncargadoDTO json) {
        EncargadoEntity entity = new EncargadoEntity();
        entity.setIdEncargado(json.getIdEncargado() );
        entity.setNombre(json.getNombre());
        entity.setNumero(json.getNumero());
        entity.setEdad(json.getEdad());
        entity.setDUI(json.getDUI());
        entity.setCorreo(json.getCorreo());
        entity.setIdUsuario(json.getIdUsuario());
        entity.setIdTipoEncargado(json.getIdTipoEncargado());
        return entity;
    }

            public EncargadoDTO actualizarEncargado(Long id, @Valid EncargadoDTO json){
        EncargadoEntity EncargadoExiste = repoEncargado.findById(id).orElseThrow(() ->new ExceptionEncargadoNoEncontrado("Encargado no encontrado"));
        EncargadoExiste.setNombre(json.getNombre());
        EncargadoExiste.setNumero(json.getNumero());
        EncargadoExiste.setEdad(json.getEdad());
        EncargadoExiste.setDUI(json.getDUI());
        EncargadoExiste.setCorreo(json.getCorreo());
        EncargadoExiste.setIdUsuario(json.getIdUsuario());
        EncargadoExiste.setIdTipoEncargado(json.getIdTipoEncargado());
        EncargadoEntity encargadoActualizado = repoEncargado.save(EncargadoExiste);
        return ConvertirADTO(encargadoActualizado);
    }

    public boolean removerEncargado(long id) {
        try{
            EncargadoEntity objUsuario = repoEncargado.findById(id).orElse(null);
            if (objUsuario != null){
                repoEncargado.deleteById(id);
                return true;
            }else{
                System.out.println("Encargado no encontrado.");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro Encargado con ID: " + id + " para eliminar.", 1);
        }
    }
}
