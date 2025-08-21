package io.github.hiwonlee.pigmint.dto;

import io.github.hiwonlee.pigmint.domain.Ledger;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LedgerResponseDto {
    private final Long id;
    private final LocalDate transactionDate;
    private final String type;
    private final String category;
    private final String description;
    private final Integer amount;
    private final Long diaryId;

    public LedgerResponseDto(Ledger entity) {
        this.id = entity.getId();
        this.transactionDate = entity.getTransactionDate();
        this.type = entity.getType();
        this.category = entity.getCategory();
        this.description = entity.getDescription();
        this.amount = entity.getAmount();
        this.diaryId = (entity.getDiary() != null) ? entity.getDiary().getId() : null;
    }
}