package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "FACTURA")
@Getter @Setter @ToString @EqualsAndHashCode
public class FacturasEntity {

    @Id
    @Column(name = "IDFACTURA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Factura")
    @SequenceGenerator(name = "seq_Factura", sequenceName = "seq_Factura", allocationSize = 1)
    private long IdFactura;

    @Column(name = "IDCLIENTE")
    private long IdCliente;

    @Column(name = "FECHA")
    private Date Fecha;

    @Column(name = "TOTAL")
    private int Total;

    @Column(name = "ESTADO")
    private String Estado;
}
