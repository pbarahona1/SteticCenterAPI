package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBRUTA")
@Getter @Setter @ToString @EqualsAndHashCode
public class RutaEntity {

    @Id
    @Column(name ="IDRUTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Ruta")
    @SequenceGenerator(name = "seq_Ruta", sequenceName = "seq_Ruta", allocationSize = 1)
    private Long id;

    @Column(name ="RUTANOMBRE")
    private String NombreRuta;

    @Column(name ="PRECIO")
    private int Precio;

    @Column(name ="INFORUTAS")
    private String InfoRutas;

    @Column(name ="RUTAIMAGEN")
    private String RutaImagen;

    @Column(name ="URLRUTA")
    private String URLRuta;
}
