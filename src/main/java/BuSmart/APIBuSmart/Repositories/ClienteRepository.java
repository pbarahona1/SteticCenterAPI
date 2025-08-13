package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    Optional<ClienteEntity> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}