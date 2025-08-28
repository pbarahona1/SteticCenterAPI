package BuSmart.APIBuSmart.Models.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString @EqualsAndHashCode
@Getter @Setter
public class UserDTO {
    private Long idUsuario;

    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El nombre es obligatorio")
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo valido")
    private String correo;

    //se usa para validar el formato del DUI(Ocho dígitos numéricos (del 0 al 9))
    @Pattern(regexp = "^\\d{8}-\\d$", message = "El formato del DUI debe ser ########-#")
    @NotBlank(message = "El dui debe de ser valido")
    private String dui;

    @NotNull(message = "El tipo de usuario debe de ser valido")
    private int idTipoUsuario;

    @Size(max = 30, message = "El usuario no debe superar los 50 caracteres")
    @NotBlank(message = "El usuario es obligatorio")
    private String usuario ;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 50 caracteres")
    private String contrasena;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @NotNull(message = "El nacimiento es obligatorio")
    private Date nacimiento;


    @NotBlank(message = "La direccion es obligatorio")
    private String direccion;

}
