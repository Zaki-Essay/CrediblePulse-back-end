package xyz.crediblepulse.crediblepulsebackend.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.crediblepulse.crediblepulsebackend.config.security.CurrentUserProvider;

@RestController
@AllArgsConstructor
public class TestController {

    CurrentUserProvider currentUserProvider;
    @GetMapping("/test")
    public String test(){
        return "hello "+currentUserProvider.getUsername();
    }
}
