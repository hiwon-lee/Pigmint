package io.github.hiwonlee.pigmint.repository;

import io.github.hiwonlee.pigmint.domain.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

// JpaRepository를 상속받으면 기본적인 DB 작업(저장, 조회, 삭제 등)을 알아서 다 해줍니다.
public interface LedgerRepository extends JpaRepository<Ledger, Long> {
    // 최상위 5개(Top5)를 UserId로 찾고, transactionDate와 id를 내림차순(Desc)으로 정렬
    List<Ledger> findTop5ByUserIdOrderByTransactionDateDescIdDesc(Long userId);

    // 월별 조회를 위한 함수
    List<Ledger> findAllByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(
            Long userId, LocalDate startDate, LocalDate endDate);


    // 특정 기간 동안의 수입 또는 지출 합계를 계산하는 기능
    // JPQL 직접 사용
    // COALESCE(SUM(l.amount), 0) : sum결과가 null일 경우, 0 반환
    @Query("SELECT COALESCE(SUM(l.amount), 0) FROM Ledger l WHERE l.id = :userId AND l.type = :type AND l.transactionDate BETWEEN :startDate AND :endDate")
    Integer calculateTotalAmountByTypeAndDateRange(
            @Param("userId") Long userId,
            @Param("type") String type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


}
