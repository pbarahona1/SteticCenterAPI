package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.TipoEncargadoEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepTipoEncargado.ExceptionTipoEncargadoNoEncontrado;
import BuSmart.APIBuSmart.Exceptions.ExcepTipoEncargado.ExceptionTipoEncargadoNoRegistrado;
import BuSmart.APIBuSmart.Models.DTO.TipoEncargadoDTO;
import BuSmart.APIBuSmart.Repositories.RutaRepository;
import BuSmart.APIBuSmart.Repositories.TipoEncargadoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TipoEncargadoService {


    @Autowired
    TipoEncargadoRepository repo;

    public List<TipoEncargadoDTO> obtenerTipoEncargado() {
        List<TipoEncargadoEntity> lista = repo.findAll();
        return lista.stream().map(this::convertirADTO).collect(Collectors.toList());

    }

    private TipoEncargadoDTO convertirADTO(TipoEncargadoEntity tipoEncargadoEntity) {
        //Creando objeto a retornar
        TipoEncargadoDTO dto = new TipoEncargadoDTO();
        //Transferir los datos al dto
        dto.setIdTipoEncargado(tipoEncargadoEntity.getIdTipoEncargado());
        dto.setTipoFamiliar(tipoEncargadoEntity.getTipoFamiliar());

        return dto;

    }


    public TipoEncargadoDTO InsertarTipoEncargado(TipoEncargadoDTO data) {

    if (data == null || data.getTipoFamiliar().isEmpty()){
        throw new IllegalArgumentException("Tipo Encargado no puede ser nulo");
    }
    try {
        TipoEncargadoEntity entity = convertirAEntity(data);
        TipoEncargadoEntity EncargadoGuardado = repo.save(entity);
        return convertirADTO(EncargadoGuardado);
    }catch (Exception e){
        log.error("Error al registrar tipo encargado" + e.getMessage());
        throw new ExceptionTipoEncargadoNoRegistrado("Error al registrar usuario.");
    }
    }

    private TipoEncargadoEntity convertirAEntity(TipoEncargadoDTO data){

        TipoEncargadoEntity entity = new TipoEncargadoEntity();
        entity.setTipoFamiliar(data.getTipoFamiliar());
        return entity;
    }


    public TipoEncargadoDTO ActualizarTipoDeEncargado(Long id, @Valid TipoEncargadoDTO json) {
        //Verificar la existencia del encargado
        TipoEncargadoEntity existente = repo.findById(id).orElseThrow(() -> new ExceptionTipoEncargadoNoEncontrado("Tipo encargado no encontrado"));
        //Convertir los datos DTO a entity
        existente.setTipoFamiliar(json.getTipoFamiliar());
        //Guardar los nuevos valores
        TipoEncargadoEntity TipoEncargadoActualizado = repo.save(existente);
        //Convertrir los datos de entity a dto
        return convertirADTO(TipoEncargadoActualizado);
    }

    public boolean EliminarTipoEncargado(Long id) {
        try {
            //validar si existe
            TipoEncargadoEntity tipoEncargadoExiste = repo.findById(id).orElse(null);
            //EliminarUsuario
            if (tipoEncargadoExiste != null){
                repo.deleteById(id);
                return true;
            }else{
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw  new EmptyResultDataAccessException("No se encontro el tipo encargado con el id: " + id + "para eliminar. ", 1);
        }
    }
}
