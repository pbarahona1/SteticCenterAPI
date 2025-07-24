package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.TipoEncargadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEncargadoRepository extends JpaRepository<TipoEncargadoEntity, Long> {
}
