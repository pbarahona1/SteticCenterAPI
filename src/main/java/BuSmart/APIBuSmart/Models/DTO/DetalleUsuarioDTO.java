package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString @EqualsAndHashCode
@Getter @Setter
public class RutaDTO {

    private Long idruta;

    @NotBlank(message = "El nombre de la ruta es obligatorio")
    private String rutaNombre;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotBlank(message = "La información de la ruta es obligatoria")
    private String infoRutas;

    @NotBlank(message = "La imagen de la ruta es obligatoria")
    private String rutaImagen;

    @NotBlank(message = "La URL de la ruta es obligatoria")
    private String urlRuta;

}
