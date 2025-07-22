package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbUsuario")
@Getter @Setter @ToString @EqualsAndHashCode
public class UserEntity {

    @Id
    @Column(name = "IDUSUARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    private Long id;

    @Column(name = "USUARIO")
    private String usuario;

    @Column(name = "CONTRASENA")
    private String contrasena;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "EDAD")
    private int edad;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "DUI")
    private String DUI;

    @Column(name = "URLFoto", columnDefinition = "CLOB")
    private String imagen;
}
