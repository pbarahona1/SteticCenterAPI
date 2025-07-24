package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.RutaEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionRutaNoEncontrada;
import BuSmart.APIBuSmart.Models.DTO.RutaDTO;
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
public class RutaService {

        @Autowired
        RutaRepository repo;

    public List<RutaDTO> obtenerRutas() {
        List<RutaEntity> Ruta = repo.findAll();
        return Ruta.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    public RutaDTO InsertarRutas(@Valid RutaDTO rut) {
        try{
            RutaEntity entity = ConvertirAEntity(rut);
            RutaEntity usuarioGuardado = repo.save(entity);
            return ConvertirADTO(usuarioGuardado);
        }catch (Exception e){
            log.error("Error al registrar Ruta: " + e.getMessage());
            throw new ExceptionRutaNoEncontrada("Error al registar el.");
        }


    }



    private RutaDTO ConvertirADTO(RutaEntity rutaEntity) {
        RutaDTO dto = new RutaDTO();
        dto.setIdruta(rutaEntity.getIdruta());
        dto.setRutaNombre(rutaEntity.getRutaNombre());
        dto.setPrecio(rutaEntity.getPrecio());
        dto.setInfoRutas(rutaEntity.getInfoRutas());
        dto.setRutaImagen(rutaEntity.getRutaImagen());
        dto.setUrlRuta(rutaEntity.getUrlRuta());
        return dto;
    }


    private RutaEntity ConvertirAEntity(RutaDTO data) {
        RutaEntity entity = new RutaEntity();
        entity.setRutaNombre(data.getRutaNombre());
        entity.setPrecio(data.getPrecio());
        entity.setInfoRutas(data.getInfoRutas());
        entity.setRutaImagen(data.getRutaImagen());
        entity.setUrlRuta(data.getUrlRuta());
        return entity;
    }

    public RutaDTO ActualizarRuta(Long id, RutaDTO json) {
        RutaEntity rutaExistente = repo.findById(id).orElseThrow(() -> new ExceptionRutaNoEncontrada("Ruta no encontrada"));
        rutaExistente.setRutaNombre(json.getRutaNombre());
        rutaExistente.setPrecio(json.getPrecio());
        rutaExistente.setInfoRutas(json.getInfoRutas());
        rutaExistente.setRutaImagen(json.getRutaImagen());
        rutaExistente.setUrlRuta(json.getUrlRuta());

        RutaEntity RutaActualizada = repo.save(rutaExistente);
        return ConvertirADTO(RutaActualizada);
    }

    public boolean QuitarRuta(Long id) {
        try{
            RutaEntity objRuta = repo.findById(id).orElse(null);
            if (objRuta != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Ruta no encontrada.");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro la ruta con ID: " + id + " para eliminar.", 1);
        }
    }
}
