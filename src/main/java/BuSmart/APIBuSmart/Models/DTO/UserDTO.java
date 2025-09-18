package BuSmart.APIBuSmart.Models.DTO;

import BuSmart.APIBuSmart.Entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString @EqualsAndHashCode
@Getter @Setter
public class UserDTO {
    private Long idUsuario;

    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String nombre;


    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo valido")
    private String correo;

    //se usa para validar el formato del DUI(Ocho dígitos numéricos (del 0 al 9))
    @Pattern(regexp = "^\\d{8}-\\d$", message = "El formato del DUI debe ser ########-#")
    private String dui;

    private int idTipoUsuario;

    @Size(max = 30, message = "El usuario no debe superar los 50 caracteres")
    private String usuario ;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 50 caracteres")
    private String contrasena;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private Date nacimiento;


    private String direccion;

    @OneToMany(mappedBy = "idTipoUsuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserEntity> usuarios = new ArrayList<>();



}
