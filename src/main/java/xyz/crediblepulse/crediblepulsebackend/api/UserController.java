package xyz.crediblepulse.crediblepulsebackend.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static xyz.crediblepulse.crediblepulsebackend.config.swagger.SwaggerOpenIdConfig.OPEN_ID_SCHEME_NAME;
import static xyz.crediblepulse.crediblepulsebackend.constants.ApiPaths.*;

@RestController
@RequestMapping(value = API + V1)
@Tag(name = "User profile endpoints")
@SecurityRequirement(name = OPEN_ID_SCHEME_NAME)
@RequiredArgsConstructor
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

}
