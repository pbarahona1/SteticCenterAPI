package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Long> {
    Optional<TipoUsuarioEntity> findByNombreTipoIgnoreCase(String nombreTipo);
}