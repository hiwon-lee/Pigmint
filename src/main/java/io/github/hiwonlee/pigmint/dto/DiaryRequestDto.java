package io.github.hiwonlee.pigmint.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryRequestDto {
    private String title;
    private String content;
}
