package xyz.crediblepulse.crediblepulsebackend.service;

import xyz.crediblepulse.crediblepulsebackend.dtos.users.UserRequestDto;
import xyz.crediblepulse.crediblepulsebackend.exception.exceptions.ApiBusinessException;

public interface UserService {

    String create(UserRequestDto userRequestDto) throws ApiBusinessException;
}
