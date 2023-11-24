package xyz.crediblepulse.crediblepulsebackend.model.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.crediblepulse.crediblepulsebackend.model.entities.user.AuthAccount;

@Repository
public interface AuthAccountRepository extends JpaRepository<AuthAccount, UUID> {}
