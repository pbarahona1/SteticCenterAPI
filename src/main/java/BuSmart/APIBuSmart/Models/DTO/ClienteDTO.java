package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Getter @Setter
public class ClienteDTO {
    private int idCliente;

    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 100, message = "La dirección no debe superar los 100 caracteres")
    private String direccion;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    @Size(max = 100, message = "El correo no debe superar los 100 caracteres")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 256, message = "La contraseña debe tener entre 8 y 256 caracteres")
    private String contrasenaCliente;
}