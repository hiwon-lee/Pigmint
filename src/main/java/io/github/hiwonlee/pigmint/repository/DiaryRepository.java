package io.github.hiwonlee.pigmint.repository;

import io.github.hiwonlee.pigmint.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    // 특정 사용자의 모든 일기를 최신순으로 조회하는 쿼리
    // todo :  JPQL 사용 -> querydsl로 리팩토링
    @Query("SELECT d FROM Diary d WHERE d.ledger.user.id = :userId ORDER BY d.createdAt DESC")
    List<Diary> findDiariesByUserId(@Param("userId") Long userId);
}