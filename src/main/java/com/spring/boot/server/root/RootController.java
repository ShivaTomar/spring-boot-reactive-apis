package com.spring.boot.server.root;

import com.spring.boot.server.security.annotations.SecuredRoute;
import com.spring.boot.server.security.rules.SecurityRule;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RootController {

    @SecuredRoute(SecurityRule.IS_ANONYMOUS)
    @GetMapping("/")
    Single<String> index() {
        log.info("Request to the root");

        return Single.just("Welcome to the Spring boot Rest APIs");
    }

}
