package io.github.hiwonlee.pigmint.service;

import io.github.hiwonlee.pigmint.domain.Ledger;
import io.github.hiwonlee.pigmint.dto.MonthlySummaryResponseDto;
import io.github.hiwonlee.pigmint.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor  //-> dto에서는 왜 안썼지?
@Transactional(readOnly = true)  // ->?
public class ReportService {
    private final LedgerRepository ledgerRepository;
    private final UserService userService;  // User ID를 가져오기 위함

    public MonthlySummaryResponseDto getMonthlySummary(int year, int month) {
        Long userId = userService.getCurrentUserId();  // 현재 로그인한 사용자 id

        LocalDate startDate = YearMonth.of(year, month).atDay(1);
        LocalDate endDate = YearMonth.of(year,month).atEndOfMonth();

        long totalIncome = ledgerRepository.calculateTotalAmountByTypeAndDateRange(userId,"INCOME", startDate, endDate);
        long totalExpense = ledgerRepository.calculateTotalAmountByTypeAndDateRange(userId, "EXPENSE", startDate, endDate);

        return new MonthlySummaryResponseDto(totalIncome, totalExpense);

         }

}
