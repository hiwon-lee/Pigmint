package io.github.hiwonlee.pigmint.service;

import io.github.hiwonlee.pigmint.dto.LedgerResponseDto;
import io.github.hiwonlee.pigmint.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    // 최근 5개 내역 조회
    public List<LedgerResponseDto> findRecentLedgers(Long userId) {
        return ledgerRepository.findTop5ByUserIdOrderByTransactionDateDescIdDesc(userId)
                .stream()
                .map(LedgerResponseDto::new) // Entity -> DTO 변환
                .collect(Collectors.toList());
    }

    // 월별 내역 조회
    public List<LedgerResponseDto> findLedgersByMonth(Long userId, int year, int month) {
        LocalDate startDate = YearMonth.of(year, month).atDay(1);
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();

        return ledgerRepository.findAllByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(userId, startDate, endDate)
                .stream()
                .map(LedgerResponseDto::new) // Entity -> DTO 변환
                .collect(Collectors.toList());
    }
}