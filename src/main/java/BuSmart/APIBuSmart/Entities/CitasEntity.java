package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ENCARGADO")
@Getter @Setter @ToString @EqualsAndHashCode
public class EncargadoEntity {

    @Id
    @Column(name = "IDENCARGADO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENCARGADO")
    @SequenceGenerator(name = "SEQ_ENCARGADO", sequenceName = "SEQ_ENCARGADO", allocationSize = 1)
    private long IdEncargado;

    @Column(name = "NOMBRE")
    private String Nombre;

    @Column(name = "NUMERO")
    private String Numero;

    @Column(name = "EDAD")
    private int Edad;

    @Column(name = "DUI")
    private String DUI;

    @Column(name = "CORREOELECTRONICO")
    private String Correo;

    @Column(name = "IDUSUARIO")
    private long IdUsuario;

    @Column(name = "IDTIPOENCARGADO")
    private long IdTipoEncargado;
}
