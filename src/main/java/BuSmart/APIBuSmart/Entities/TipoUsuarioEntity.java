package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TIPOUSUARIO") // Nombre exacto de la tabla en DB
@Getter @Setter @EqualsAndHashCode
public class TipoUsuarioEntity {

    @Id
    @Column(name = "IDTIPOUSUARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPOUSUARIO")
    @SequenceGenerator(name = "SEQ_TIPOUSUARIO", sequenceName = "SEQ_TIPOUSUARIO", allocationSize = 1)
    private Long idTipoUsuario;

    @Column(name = "NOMBRETIPO")
    private String nombreTipo;

    @Override
    public String toString() {
        return "TipoUsuarioEntity{" +
                "idTipoUsuario=" + idTipoUsuario +
                ", nombreTipo='" + nombreTipo + '\'' +
                '}';
    }
}