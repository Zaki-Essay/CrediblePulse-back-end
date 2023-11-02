package xyz.crediblepulse.crediblepulsebackend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.crediblepulse.crediblepulsebackend.model.entities.user.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);
}
