package BuSmart.APIBuSmart.Models.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class FacturasDTO {
    private long IdFactura;

    private long IdCliente;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de la factura debe ser en el pasado")
    @NotNull(message = "La Fecah es obligatorio")
    private Date Fecha;

    @NotNull(message = "El total no puede ser nulo")
    @Min(value = 0)
    private int Total;

    @NotNull(message = "El estado no puede ser nulo")
    @Pattern(
            regexp = "PENDIENTE|PAGADA|CANCELADA",
            message = "El estado debe ser PENDIENTE, PAGADA o CANCELADA"
    )
    private String Estado;
}
