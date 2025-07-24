package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @ToString @EqualsAndHashCode
public class UnidadesDTO {
    private Long idUnidad;

    @NotBlank(message = "El tipo de unidad es obligatorio")
    private String tipoUnidad;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacidad;

    @NotNull(message = "El estado es obligatorio")
    private Integer idEstado;

    private Integer idRuta;

    @Min(value = 1, message = "Debe haber al menos 1 unidad")
    private Integer unidades = 1;
}