package io.github.hiwonlee.pigmint.repository;

import io.github.hiwonlee.pigmint.domain.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository를 상속받으면 기본적인 DB 작업(저장, 조회, 삭제 등)을 알아서 다 해줍니다.
public interface LedgerRepository extends JpaRepository<Ledger, Long> {
}