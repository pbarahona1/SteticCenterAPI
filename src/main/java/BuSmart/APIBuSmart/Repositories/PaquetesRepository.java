package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.PaquetesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaquetesRepository extends JpaRepository<PaquetesEntity, Integer> {
    boolean existsByNombre(String nombre);
}