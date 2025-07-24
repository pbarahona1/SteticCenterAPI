package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Getter @Setter
public class TipoEncargadoDTO {

    private Long IdTipoEncargado;

    @NotBlank(message = "El tipo de encargado es obligatorio")
    private String TipoEncargado;
}
