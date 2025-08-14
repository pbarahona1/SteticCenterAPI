package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.NotBlank;
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

    private Long precio;

    private Long duracion_min;

}
