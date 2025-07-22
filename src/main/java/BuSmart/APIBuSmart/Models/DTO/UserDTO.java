package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString @EqualsAndHashCode
@Getter @Setter
public class UserDTO {
    private Long idUsuario;

    @NotBlank(message = "El usuario es obligatorio")
    private String usuario;

    @Size(min = 8, message = "La contraseña debe de ser de minimo de 8 caracteres")
    private String contrasena;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Min(value = 15, message = "La edad mínima debe ser de 15 años")
    private int edad;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo valido")
    private String correo;

    @NotBlank(message = "El dui debe de ser valido")
    private String DUI;
}
