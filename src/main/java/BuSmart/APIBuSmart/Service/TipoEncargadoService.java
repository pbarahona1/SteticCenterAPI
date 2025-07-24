package BuSmart.APIBuSmart.Service;

import BuSmart.APIBuSmart.Models.DTO.TipoEncargadoDTO;
import BuSmart.APIBuSmart.Repositories.RutaRepository;
import BuSmart.APIBuSmart.Repositories.TipoEncargadoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TipoEncargadoService {


    @Autowired
    TipoEncargadoRepository repo;

    public List<TipoEncargadoDTO> obtenerTipoEncargado() {

    }


    public TipoEncargadoDTO InsertarTipoEncargado(@Valid TipoEncargadoDTO json) {

    }

    public TipoEncargadoDTO ActualizarTipoDeEncargado(Long id, @Valid TipoEncargadoDTO json) {

    }

    public boolean EliminarTipoEncargado(Long id) {

    }
}
