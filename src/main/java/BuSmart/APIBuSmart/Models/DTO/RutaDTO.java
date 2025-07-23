package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode
public class RutaDTO {

    @NotBlank
    private Long idruta;

    @NotBlank
    private String RutaNombre;

    @NotBlank
    private int Precio;

    @NotBlank
    private String InfoRutas;

    @NotBlank
    private String RutaImagen;

    @NotBlank
    private  String URLRuta;

}
