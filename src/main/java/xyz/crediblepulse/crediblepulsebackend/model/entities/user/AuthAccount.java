package xyz.crediblepulse.crediblepulsebackend.model.entities.user;


import jakarta.persistence.*;
import lombok.*;
import xyz.crediblepulse.crediblepulsebackend.model.technical.BusinessEntity;

import java.io.Serializable;

@Entity
@Table(name = "auth_accounts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class AuthAccount extends BusinessEntity<String> implements Serializable {

    @Column(name = "provider_name")
    private String providerName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
