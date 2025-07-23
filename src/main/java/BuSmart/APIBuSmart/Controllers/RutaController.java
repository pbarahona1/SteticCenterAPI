package BuSmart.APIBuSmart.Controllers;

import BuSmart.APIBuSmart.Models.DTO.RutaDTO;
import BuSmart.APIBuSmart.Service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apiRuta")
public class RutaController {

    @Autowired
    RutaService service;

    @GetMapping("/ConsultarRutas")
    public List<RutaDTO> obtenerDatos(){return  service.obtenerRutas();}

}
