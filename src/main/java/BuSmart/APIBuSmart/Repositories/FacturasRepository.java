package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.FacturasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturasRepository extends JpaRepository<FacturasEntity, Long> {
}
