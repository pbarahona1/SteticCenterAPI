package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {
    boolean existsByNombre(String nombre);
}