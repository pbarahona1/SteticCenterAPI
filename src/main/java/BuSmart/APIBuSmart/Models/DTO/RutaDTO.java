package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode
public class RutaDTO {

    private Long idRuta;

    @NotBlank
    private String NombreRuta;

    @NotBlank
    private int Precio;

    @NotBlank
    private String InfoRuta;

    @NotBlank
    private String RutaImagen;

    @NotBlank
    private  String URLRuta;

}
