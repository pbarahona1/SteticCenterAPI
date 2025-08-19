package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.DetalleFacturaEntity;
import BuSmart.APIBuSmart.Entities.FacturasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFacturaEntity, Long> {
}
