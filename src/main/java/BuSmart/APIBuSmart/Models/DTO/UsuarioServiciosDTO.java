package BuSmart.APIBuSmart.Models.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UsuarioServiciosDTO {


    private Long idUsuarioServicio;

    private Long idUsuario;

    private Long idServicio;

}
