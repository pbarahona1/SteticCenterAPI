package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ServiciosDTO {

    private Long idServicio;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;


    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "1", inclusive = true, message = "El precio debe ser mayor o igual a 1")
    private Long precio;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 1, message = "La duración debe ser al menos 1 minuto")
    @Max(value = 1440, message = "La duración no puede exceder un día (1440 minutos)")
    private Long duracion_min;

    @NotBlank(message = "no puede ser nulo")
    private String imgUrl;
}
