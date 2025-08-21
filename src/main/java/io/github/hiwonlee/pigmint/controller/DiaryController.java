package io.github.hiwonlee.pigmint.controller;

import io.github.hiwonlee.pigmint.dto.DiaryRequestDto;
import io.github.hiwonlee.pigmint.dto.DiaryResponseDto;
import io.github.hiwonlee.pigmint.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DiaryController {

    private final DiaryService diaryService;

    // 특정 가계부 내역에 대한 일기 생성 API
    @PostMapping("/ledgers/{ledgerId}/diary")
    public ResponseEntity<DiaryResponseDto> createDiaryForLedger(
            @PathVariable Long ledgerId,
            @RequestBody DiaryRequestDto requestDto) throws AccessDeniedException {

        DiaryResponseDto createdDiary = diaryService.createDiary(ledgerId, requestDto);
        return ResponseEntity.ok(createdDiary);
    }

    // 현재 로그인한 사용자의 모든 일기 조회 API
    @GetMapping("/diaries")
    public ResponseEntity<List<DiaryResponseDto>> getMyDiaries() {
        List<DiaryResponseDto> diaries = diaryService.findMyDiaries();
        return ResponseEntity.ok(diaries);
    }

    // todo : 일기 수정(PUT), 삭제(DELETE) API 추가
}