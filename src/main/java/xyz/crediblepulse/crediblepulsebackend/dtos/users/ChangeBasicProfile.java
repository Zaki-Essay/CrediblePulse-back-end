package xyz.crediblepulse.crediblepulsebackend.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeBasicProfile {

    private String firstName;
    private String lastName;
    private String description;
}
