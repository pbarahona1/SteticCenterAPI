package BuSmart.APIBuSmart.Repositories;

import BuSmart.APIBuSmart.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
