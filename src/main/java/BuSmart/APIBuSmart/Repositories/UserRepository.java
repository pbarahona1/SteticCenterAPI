package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByCorreo(String email);
    Optional<UserEntity> findByUsuario(String usuario);

    boolean existsByCorreo(String email);
    boolean existsByUsuario(String usuario);
    boolean existsByDui(String dui);

    boolean existsByUsuarioAndIdUsuarioNot(String usuario, Long id);
    boolean existsByDuiAndIdUsuarioNot(String dui, Long id);

    // En UserRepository.java
    @Query("SELECT u FROM UserEntity u WHERE " +
            "LOWER(u.nombre) LIKE LOWER('%' || :filtro || '%') OR " +
            "LOWER(u.apellido) LIKE LOWER('%' || :filtro || '%') OR " +
            "LOWER(u.correo) LIKE LOWER('%' || :filtro || '%') OR " +
            "LOWER(u.usuario) LIKE LOWER('%' || :filtro || '%')")
    Page<UserEntity> searchUsers(@Param("filtro") String filtro, Pageable pageable);

}
