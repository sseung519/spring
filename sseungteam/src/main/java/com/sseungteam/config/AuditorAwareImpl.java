package com.sseungteam.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        //로그인한 사용자의 정보를 가지고 온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";

        if(authentication != null) {
            userId = authentication.getName(); //로그인한 사용자의 id(여기서는 email)을 가져온다.
        }
        return Optional.of(userId);
    }
}
