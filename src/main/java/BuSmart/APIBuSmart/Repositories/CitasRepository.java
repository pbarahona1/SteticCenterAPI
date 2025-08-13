package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.CitasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CitasRepository extends JpaRepository<CitasEntity, Long> {
    boolean existsByIdUsuarioAndIdHorarioAndFechaCita(int idUsuario, int idHorario, Date fecha_cita);
}
