package com.sseungteam.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDto {
    @NotNull(message = "게임 아이디는 필수 입력 값입니다.")
    private Long gameId;

    private int price;
}
