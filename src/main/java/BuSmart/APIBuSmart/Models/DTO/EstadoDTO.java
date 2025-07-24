package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Getter @Setter
public class EstadoDTO {

    private Long idEstado;

    @NotBlank(message = "El estado de la unidad es obligatorio")
    private String TipoEstado;
}
