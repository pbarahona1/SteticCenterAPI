package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Getter @Setter
public class DetallesCitasDTO {
    private Long idDetalleCita;

    @NotNull(message = "El id cita es obligatorio")
    private int idCita;

    @NotBlank(message = "La observaciones son obligatorio")
    @Size(max = 500, message = "Las observaciones no pueden exceder los 500 caracteres")
    private String observaciones;
}
