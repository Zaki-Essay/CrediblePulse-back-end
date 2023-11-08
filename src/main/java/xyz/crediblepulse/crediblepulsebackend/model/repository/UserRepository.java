package xyz.crediblepulse.crediblepulsebackend.model.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.crediblepulse.crediblepulsebackend.model.entities.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);
}
