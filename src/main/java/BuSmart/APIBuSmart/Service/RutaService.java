package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Entities.RutaEntity;
import BuSmart.APIBuSmart.Models.DTO.RutaDTO;
import BuSmart.APIBuSmart.Repositories.RutaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RutaService {

    @Autowired
    RutaRepository repo;

    public List<RutaDTO> obtenerRutas() {
        List<RutaEntity> Usuario = repo.findAll();
        return Usuario.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    private RutaDTO ConvertirADTO(RutaEntity rutaEntity) {
        RutaDTO dto = new RutaDTO();
        dto.setIdRuta(rutaEntity.getId());
        dto.setNombreRuta(rutaEntity.getNombreRuta());
        dto.setPrecio(rutaEntity.getPrecio());
        dto.setInfoRuta(rutaEntity.getInfoRutas());
        dto.setRutaImagen(rutaEntity.getRutaImagen());
        dto.setURLRuta(rutaEntity.getURLRuta());
        return dto;
    }
}
