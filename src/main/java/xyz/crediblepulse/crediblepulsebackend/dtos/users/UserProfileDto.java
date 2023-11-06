package com.nemo.mypath.dtos.users;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserProfileDto {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String userStatus;

    private String role;

    private Map<String, Object> metaData;
}
