package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.DetalleUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleUsuarioRepository extends JpaRepository<DetalleUsuarioEntity, Long> {
}
