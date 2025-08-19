package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    boolean existsByCorreo(String correo);
}