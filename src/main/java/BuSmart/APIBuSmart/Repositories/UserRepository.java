package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsuario(String usuario);
    boolean existsByDui(String dui);

    boolean existsByUsuarioAndIdUsuarioNot(String usuario, Long id);
    boolean existsByDuiAndIdUsuarioNot(String dui, Long id);
}
