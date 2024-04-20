package com.shopmax.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

//로그인한 사용자의 정보를 등록자와 수정자로 지정하기 위해 사용
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String userId = "test";

        return Optional.of(userId); //해당 userId를 등록자와 수정자로 지정
    }
}
