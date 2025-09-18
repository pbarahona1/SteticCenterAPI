package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Entity
@Table(name = "USUARIOS")
@Getter @Setter @EqualsAndHashCode
public class UserEntity {

    @Id
    @Column(name = "IDUSUARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Usuarios")
    @SequenceGenerator(name = "seq_Usuarios", sequenceName = "seq_Usuarios", allocationSize = 1)
    private Long idUsuario;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "DUI")
    private String dui;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTIPOUSUARIO", referencedColumnName = "IDTIPOUSUARIO")
    private TipoUsuarioEntity TipoUsuario;

    @Column(name = "USUARIO")
    private String usuario;

    @Column(name = "CONTRASENA")
    private String contrasena;

    @Column(name = "NACIMIENTO")
    private Date nacimiento;

    @Column(name = "DIRECCION")
    private String direccion;

    @Override
    public String toString() {
        return "UserEntity{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", dui='" + dui + '\'' +
                ", TipoUsuario=" + TipoUsuario +
                ", usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", nacimiento=" + nacimiento +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
