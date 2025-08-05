package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.UnidadesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadesRepository extends JpaRepository<UnidadesEntity, Long> {
}