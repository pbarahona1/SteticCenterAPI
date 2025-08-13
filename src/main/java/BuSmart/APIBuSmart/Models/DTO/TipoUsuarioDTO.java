package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @ToString @EqualsAndHashCode
public class TipoUsuarioDTO {
    private Long idTipoUsuario;

    @NotBlank(message = "El tipo de usuario es obligatorio")
    private String nombreTipo;

}