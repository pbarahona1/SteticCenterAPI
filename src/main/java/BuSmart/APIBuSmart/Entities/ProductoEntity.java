package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTOS")
@Getter @Setter @ToString @EqualsAndHashCode
public class ProductoEntity {

    @Id
    @Column(name = "IDPRODUCTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Productos")
    @SequenceGenerator(name = "seq_Productos", sequenceName = "seq_Productos", allocationSize = 1)
    private int idProducto;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "PRECIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "STOCK")
    private int stock = 0;

    @Column(name = "IMG_URL")
    private String imgUrl;
}