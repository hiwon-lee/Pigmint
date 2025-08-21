package io.github.hiwonlee.pigmint.controller;

import io.github.hiwonlee.pigmint.dto.MonthlySummaryResponseDto;
import io.github.hiwonlee.pigmint.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/monthly")
    public ResponseEntity<MonthlySummaryResponseDto> getMonthlySummary(
            // todo : value의 이름을 명시적으로 주지 않고, compiler설정으로만 params전달 받을 수 있도록(refactor)
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        // 파라미터가 없으면 현재 년/월을 사용
        LocalDate today = LocalDate.now();
        int currentYear = (year != null) ? year : today.getYear();
        int currentMonth = (month != null) ? month : today.getMonthValue();

        MonthlySummaryResponseDto summary = reportService.getMonthlySummary(currentYear, currentMonth);
        return ResponseEntity.ok(summary);
    }
}