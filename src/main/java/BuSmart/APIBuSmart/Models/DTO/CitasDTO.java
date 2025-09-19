package BuSmart.APIBuSmart.Models.DTO;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@ToString @EqualsAndHashCode
public class CitasDTO {
    private long idCita;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer idUsuario;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer idCliente;

    @NotNull(message = "El ID del horario es obligatorio")
    private Integer idHorario;

    @NotNull(message = "La fecha de la cita es obligatoria")
    private Date fecha_cita;

    @NotBlank(message = "El estado de la cita es obligatorio")
    @Pattern(
            regexp = "PENDIENTE|CONFIRMADA|CANCELADA|COMPLETADA",
            message = "El estado debe ser PENDIENTE, CONFIRMADA, CANCELADA o COMPLETADA"
    )
    private String estado;

}
