package BuSmart.APIBuSmart.Models.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.message.Message;

@Getter @Setter
@ToString @EqualsAndHashCode
public class EncargadoDTO {
    private long IdEncargado;

    @NotBlank(message = "El nombre es obligatorio")
    private String Nombre;

    @Min(value = 18, message = "La edad mínima debe ser de 18 años")
    private int Edad;

    @NotNull(message = "El DUI es obligatoria")
    private String DUI;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "debe ser un correo con formato valido")
    private String Correo;

    @NotNull(message = "El IdUsuario es obligatorio")
    @Positive(message = "El IdUsuario debe ser un número positivo")
    private long IdUsuario;

    @NotNull(message = "El ID tipo de encargado es obligatorio")
    @Positive(message = "El ID tipo de encargado debe ser un número positivo")
    private long IdTipoEncargado;
}
