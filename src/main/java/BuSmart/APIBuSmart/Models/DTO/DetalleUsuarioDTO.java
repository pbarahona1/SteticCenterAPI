package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString @EqualsAndHashCode
@Getter @Setter
public class DetalleUsuarioDTO {

    private Long idDetalleUsuario;

    @NotNull(message = "El id del Usuario es obligatorio")
    private int idUsuario;

    @NotNull(message = "El id del especialista")
    private int idEspecialista;
}
