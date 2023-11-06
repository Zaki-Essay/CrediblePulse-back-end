package com.nemo.mypath.dtos.users;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String userStatus;
    private String gender;
    private Boolean verified;
    private Boolean googleCalendarLinked;
    private String description;
    private List<String> authAccounts;
    String timeZone;
}
