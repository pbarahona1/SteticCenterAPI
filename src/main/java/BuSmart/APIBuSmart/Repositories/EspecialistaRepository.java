package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.EspecialistaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialistaRepository extends JpaRepository<EspecialistaEntity, Long> {
    boolean existsByEspecialidadIgnoreCase(String especialidad);
}
