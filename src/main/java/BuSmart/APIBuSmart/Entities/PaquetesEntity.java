package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "PAQUETES")
@Getter @Setter @ToString @EqualsAndHashCode
public class PaquetesEntity {
    @Id
    @Column(name = "IDPAQUETE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Paquetes")
    @SequenceGenerator(name = "seq_Paquetes", sequenceName = "seq_Paquetes", allocationSize = 1)
    private int idPaquete;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "PRECIO", nullable = false)
    @JdbcTypeCode(SqlTypes.NUMERIC)
    private BigDecimal precio;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "IMG_URL")
    private String imgUrl;
}