package com.board.Config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";

        if(authentication != null) {
            userId = authentication.getName(); //로그인한 사용자의 id(email)을 가지고 온다.
        }

        return Optional.of(userId); //해당 userId를 등록자와 수정자로 지정
    }
}
