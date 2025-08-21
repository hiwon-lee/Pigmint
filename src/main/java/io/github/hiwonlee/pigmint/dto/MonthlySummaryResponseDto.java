package io.github.hiwonlee.pigmint.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class MonthlySummaryResponseDto {
    private final long totalIncome;
    private final long totalExpense;
    private final long netIncome;

    // @RequiredArgsConstructor를 사용 -> final필드에 대한 생성자를 자동으로 생성
    public MonthlySummaryResponseDto(long totalIncome, long totalExpense) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.netIncome = totalIncome - totalExpense;

    }


}
