package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Getter @Setter
public class EspecialistaDTO {

    private Long idEspecialista;

    @NotBlank(message = "La especialidad es obligatorio")
    private String especialidad;
}
