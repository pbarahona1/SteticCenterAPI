package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.HorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<HorarioEntity, Long>{
}
