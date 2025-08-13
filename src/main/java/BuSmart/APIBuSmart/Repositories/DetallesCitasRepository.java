package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.DetallesCitasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallesCitasRepository extends JpaRepository<DetallesCitasEntity, Long> {
}
