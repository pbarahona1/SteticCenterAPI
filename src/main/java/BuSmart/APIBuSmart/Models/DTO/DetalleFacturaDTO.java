package BuSmart.APIBuSmart.Models.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class DetalleFacturaDTO {

    private long IdDetalleFactura;

    @NotNull(message = "El ID de la factura es obligatorio")
    private long IdFactura;

    @NotNull(message = "El ID de la cita es obligatorio")
    private long IdCita;

    @NotNull(message = "El ID del producto es obligatorio")
    private long IdProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int Cantidad;

    @NotNull(message = "El precio unitario no puede ser nulo")
    @DecimalMin(value = "0.00", inclusive = true, message = "El precio unitario debe ser mayor o igual a 0.00")
    @Digits(integer = 8, fraction = 2)
    @Column(precision = 10, scale = 2)
    private BigDecimal PrecioUnitario;
}
