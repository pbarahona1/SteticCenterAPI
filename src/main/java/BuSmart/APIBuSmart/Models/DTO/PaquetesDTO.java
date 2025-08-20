package BuSmart.APIBuSmart.Models.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;

@ToString @EqualsAndHashCode
@Getter @Setter
public class PaquetesDTO {
    private int idPaquete;

    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener máximo 8 dígitos enteros y 2 decimales")
    @Column(precision = 10, scale = 2)
    private BigDecimal precio;

    @Size(max = 500, message = "La descripción no debe superar los 500 caracteres")
    private String descripcion;
}