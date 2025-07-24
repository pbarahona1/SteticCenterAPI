package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "TBRUTA")
@Getter @Setter @ToString @EqualsAndHashCode
public class RutaEntity {

    @Id
    @Column(name ="IDRUTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    private Long idruta;

    @Column(name ="RUTANOMBRE")
    private String rutaNombre;

    @Column(name ="PRECIO")
    private BigDecimal precio;

    @Column(name ="INFORUTAS")
    private String infoRutas;

    @Column(name ="RUTAIMAGEN")
    private String rutaImagen;

    @Column(name ="URLRUTA")
    private String urlRuta;
}
