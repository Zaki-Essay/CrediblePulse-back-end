package xyz.crediblepulse.crediblepulsebackend.service;

import xyz.crediblepulse.crediblepulsebackend.dtos.users.UserRequestDto;

public interface UserService {

    String create(UserRequestDto userRequestDto) throws Exception;
}
