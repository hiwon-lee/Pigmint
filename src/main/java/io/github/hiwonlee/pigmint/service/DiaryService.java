package io.github.hiwonlee.pigmint.service;

import io.github.hiwonlee.pigmint.domain.Diary;
import io.github.hiwonlee.pigmint.domain.Ledger;
import io.github.hiwonlee.pigmint.dto.DiaryRequestDto;
import io.github.hiwonlee.pigmint.dto.DiaryResponseDto;
import io.github.hiwonlee.pigmint.repository.DiaryRepository;
import io.github.hiwonlee.pigmint.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final LedgerRepository ledgerRepository;
    private final UserService userService;

    // 일기 생성
    @Transactional
    public DiaryResponseDto createDiary(Long ledgerId, DiaryRequestDto requestDto) throws AccessDeniedException {
        Long currentUserId = userService.getCurrentUserId();

        // 일기를 작성할 가계부 내역을 조회합니다.
        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가계부 내역을 찾을 수 없습니다. id=" + ledgerId));

        // 해당 가계부 내역이 현재 로그인한 사용자의 것인지 확인합니다. (매우 중요!)
        if (!ledger.getUser().getId().equals(currentUserId)) {
            throw new AccessDeniedException("해당 내역에 대한 일기 작성 권한이 없습니다.");
        }

        Diary diary = Diary.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .ledger(ledger)
                .build();

        Diary savedDiary = diaryRepository.save(diary);
        return new DiaryResponseDto(savedDiary);
    }

    // 내 일기 전체 조회
    public List<DiaryResponseDto> findMyDiaries() {
        Long currentUserId = userService.getCurrentUserId();

        List<Diary> diaries = diaryRepository.findDiariesByUserId(currentUserId);

        return diaries.stream()
                .map(DiaryResponseDto::new)
                .collect(Collectors.toList());
    }

    // (추후 필요시) 일기 수정, 삭제 로직 추가
}