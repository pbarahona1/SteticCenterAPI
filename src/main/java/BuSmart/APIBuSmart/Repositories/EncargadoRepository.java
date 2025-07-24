package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.EncargadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncargadoRepository extends JpaRepository<EncargadoEntity, Long> {
}
