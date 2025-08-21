package io.github.hiwonlee.pigmint.controller;

import io.github.hiwonlee.pigmint.domain.Ledger;
import io.github.hiwonlee.pigmint.dto.LedgerResponseDto;
import io.github.hiwonlee.  pigmint.repository.LedgerRepository;
import io.github.hiwonlee.pigmint.service.LedgerService;
import io.github.hiwonlee.pigmint.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor // final이 붙은 필드를 인자로 받는 생성자를 자동으로 만들어줍니다. (의존성 주입)
public class LedgerController {

//    private final LedgerRepository ledgerRepository; // DB 작업을 위해 Repository를 주입받습니다.
    private final LedgerService ledgerService;
    private final UserService userService;


    @GetMapping("/api/ledgers")
    public ResponseEntity<List<LedgerResponseDto>> getLedgers(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "limit", required = false) Integer limit) { // limit 파라미터 추가

        Long userId = userService.getCurrentUserId();
        List<LedgerResponseDto> ledgers;  // 응답 리스트 형태

        if (limit != null) {
            // limit 파라미터가 있으면 최신 내역 반환 w/ service
            ledgers = ledgerService.findRecentLedgers(userId);
//            return ResponseEntity.ok(recentLedagers); // DTO로 변환하여 반환하는 것이 더 좋습니다.
        } else {
            // 기존의 월별 조회 로직
            // 파라미터가 없으면 현재 년/월을 사용
            LocalDate today = LocalDate.now();
            int currentYear = (year != null) ? year : today.getYear();
            int currentMonth = (month != null) ? month : today.getMonthValue();

            // Service를 통해 월별 내역 조회
            ledgers = ledgerService.findLedgersByMonth(userId, currentYear, currentMonth);
        }

        return  ResponseEntity.ok(ledgers);
    }
}