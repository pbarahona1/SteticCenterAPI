package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.RutaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<RutaEntity, Long> {
}
