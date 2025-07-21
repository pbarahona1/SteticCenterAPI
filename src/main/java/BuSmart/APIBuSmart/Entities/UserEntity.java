package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBUsuario")
@Getter @Setter @ToString @EqualsAndHashCode
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
    @Column(name = "IDUSUARIO")
    private Long id;

    @Column(name = "USUARIO")
    private String usuario;

    @Column(name = "CONTRASEÑA")
    private String contrasena;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "EDAD")
    private int edad;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "DUI")
    private String DUI;
}
