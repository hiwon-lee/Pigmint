package io.github.hiwonlee.pigmint.dto;

import io.github.hiwonlee.pigmint.domain.Diary;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiaryResponseDto {
    private final Long diaryId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final Long ledgerId;
    private final String ledgerDescription;
    private final int ledgerAmount;

    public DiaryResponseDto(Diary entity) {
        this.diaryId = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
        this.ledgerId = entity.getLedger().getId();
        this.ledgerDescription = entity.getLedger().getDescription();
        this.ledgerAmount = entity.getLedger().getAmount();
    }
}