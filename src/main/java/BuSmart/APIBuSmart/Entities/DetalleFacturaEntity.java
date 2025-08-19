package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DETALLEFACTURA")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DetalleFacturaEntity {
    @Id
    @Column(name = "IDDETALLEFACTURA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DETALLEFACTURA")
    @SequenceGenerator(name = "SEQ_DETALLEFACTURA", sequenceName = "SEQ_DETALLEFACTURA", allocationSize = 1)
    private long IdDetalleFactura;

    @Column(name = "IDFACTURA")
    private long IdFactura;

    @Column(name = "IDCITA")
    private long IdCita;

    @Column(name = "IDPRODUCTO")
    private long IdProducto;

    @Column(name = "CANTIDAD")
    private int Cantidad;

    @Column(name = "PRECIO_UNITARIO")
    private int PrecioUnitario;
}
