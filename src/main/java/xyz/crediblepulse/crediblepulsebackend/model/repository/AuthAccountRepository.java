package xyz.crediblepulse.crediblepulsebackend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.crediblepulse.crediblepulsebackend.model.entities.user.AuthAccount;

import java.util.UUID;

@Repository
public interface AuthAccountRepository extends JpaRepository<AuthAccount, UUID> {}
