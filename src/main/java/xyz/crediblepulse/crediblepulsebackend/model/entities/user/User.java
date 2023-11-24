package xyz.crediblepulse.crediblepulsebackend.model.entities.user;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.List;
import lombok.*;
import xyz.crediblepulse.crediblepulsebackend.model.technical.BusinessEntity;
import xyz.crediblepulse.crediblepulsebackend.types.Gender;
import xyz.crediblepulse.crediblepulsebackend.types.UserStatus;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class User extends BusinessEntity<String> implements Serializable {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private String phoneNumber;

    private String description;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "time_zone")
    private ZoneId timeZone;

    @OneToMany(mappedBy = "user")
    private List<AuthAccount> authAccounts;

    private Boolean verified;
}
