package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.PaquetesEntity;
import BuSmart.APIBuSmart.Entities.UserEntity;
import BuSmart.APIBuSmart.Exceptions.ExcepPaquetes.*;
import BuSmart.APIBuSmart.Exceptions.ExcepUsuarios.ExceptionsUsuarioNoEncontrado;
import BuSmart.APIBuSmart.Models.DTO.PaquetesDTO;
import BuSmart.APIBuSmart.Models.DTO.UserDTO;
import BuSmart.APIBuSmart.Repositories.PaquetesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaquetesService {

    @Autowired
    private PaquetesRepository paquetesRepository;

    public List<PaquetesDTO> getAllPaquetes() {
        try {
            List<PaquetesEntity> paquetes = paquetesRepository.findAll();
            if (paquetes.isEmpty()) {
                throw new ExcepcionPaquetesNoRegistrado("No hay paquetes registrados en el sistema");
            }
            return paquetes.stream()
                    .map(this::convertirAPaquetesDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al listar paquetes", e);
            throw new ExcepcionPaquetesNoEncontrado("Error al obtener la lista de paquetes");
        }
    }

    /*paginado*/
    public Page<PaquetesDTO> getAllPaquetesPaginado(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PaquetesEntity> paquetes = paquetesRepository.findAll(pageable);

            return paquetes.map(this::convertirAPaquetesDTO);

        } catch (Exception e) {
            log.error("Error al listar paquetes: " + e.getMessage(), e);
            throw new ExcepcionPaquetesNoEncontrado("Error al listar paquetes: " + e.getMessage());
        }
    }

    public PaquetesDTO createPaquetes(PaquetesDTO paquetesDTO) {
        try {
            if (paquetesRepository.existsByNombre(paquetesDTO.getNombre())) {
                throw new ExcepcionPaquetesDuplicado("nombre", "El nombre del paquete ya existe");
            }

            PaquetesEntity nuevoPaquetes = convertirAPaquetesEntity(paquetesDTO);
            PaquetesEntity paquetesGuardado = paquetesRepository.save(nuevoPaquetes);
            return convertirAPaquetesDTO(paquetesGuardado);

        } catch (ExcepcionPaquetesDuplicado e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al crear paquete", e);
            throw new ExcepcionPaquetesNoRegistrado("Error al registrar el paquete");
        }
    }

    public PaquetesDTO updatePaquetes(Integer id, PaquetesDTO paquetesDTO) {
        try {
            PaquetesEntity paquetesExistente = paquetesRepository.findById(id)
                    .orElseThrow(() -> new ExcepcionPaquetesNoEncontrado("Paquete no encontrado"));

            if (!paquetesExistente.getNombre().equals(paquetesDTO.getNombre()) &&
                    paquetesRepository.existsByNombre(paquetesDTO.getNombre())) {
                throw new ExcepcionPaquetesDuplicado("nombre", "Ya existe un paquete con este nombre");
            }

            paquetesExistente.setNombre(paquetesDTO.getNombre());
            paquetesExistente.setPrecio(paquetesDTO.getPrecio());
            paquetesExistente.setDescripcion(paquetesDTO.getDescripcion());
            paquetesExistente.setImgUrl(paquetesDTO.getImgUrl());

            PaquetesEntity paquetesActualizado = paquetesRepository.save(paquetesExistente);
            return convertirAPaquetesDTO(paquetesActualizado);

        } catch (ExcepcionPaquetesNoEncontrado | ExcepcionPaquetesDuplicado e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar paquete con ID: " + id, e);
            throw new ExcepcionPaquetesNoRegistrado("Error al actualizar el paquete");
        }
    }

    public void deletePaquetes(Integer id) {
        try {
            if (!paquetesRepository.existsById(id)) {
                throw new ExcepcionPaquetesNoEncontrado("Paquete no encontrado");
            }
            paquetesRepository.deleteById(id);
        } catch (ExcepcionPaquetesNoEncontrado e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar paquete con ID: " + id, e);
            throw new ExcepcionPaquetesNoRegistrado("Error al eliminar el paquete");
        }
    }

    private PaquetesDTO convertirAPaquetesDTO(PaquetesEntity paquetes) {
        PaquetesDTO dto = new PaquetesDTO();
        dto.setIdPaquete(paquetes.getIdPaquete());
        dto.setNombre(paquetes.getNombre());
        dto.setPrecio(paquetes.getPrecio());
        dto.setDescripcion(paquetes.getDescripcion());
        dto.setImgUrl(paquetes.getImgUrl());
        return dto;
    }

    private PaquetesEntity convertirAPaquetesEntity(PaquetesDTO dto) {
        PaquetesEntity paquetes = new PaquetesEntity();
        paquetes.setIdPaquete(dto.getIdPaquete());
        paquetes.setNombre(dto.getNombre());
        paquetes.setPrecio(dto.getPrecio());
        paquetes.setDescripcion(dto.getDescripcion());
        paquetes.setImgUrl(dto.getImgUrl());
        return paquetes;
    }
}