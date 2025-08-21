package io.github.hiwonlee.pigmint.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity // 이 클래스가 데이터베이스 테이블과 연결되는 '엔티티'임을 선언합니다.
@Getter // 각 필드의 값을 가져올 수 있는 getter 메서드를 자동으로 만들어줍니다. (Lombok)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무 인자 없는 기본 생성자를 만듭니다. JPA는 이 생성자가 꼭 필요합니다.
public class Ledger {

    @Id // 이 필드가 테이블의 '기본 키(Primary Key)'임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 값을 DB가 자동으로 생성(auto-increment)해줍니다.
    @Column(name = "ledger_id") // DB 테이블의 컬럼 이름을 지정합니다.
    private Long id;

    // 'User' 엔티티와의 관계를 정의합니다.
    @ManyToOne(fetch = FetchType.LAZY) // Ledger(N) : User(1) 관계
    @JoinColumn(name = "user_id", nullable = false) // DB에 'user_id'라는 이름으로 Foreign Key 생성
    private User user;

    @Column(nullable = false) // 이 컬럼은 비어있을 수 없다고(NOT NULL) 지정합니다.
    private String description; // 내용 (예: "점심 식사")

    @Column(nullable = false)
    private Integer amount; // 금액

    private String memo; // 메모 (비어있어도 됨)

    @Column(nullable = false)
    private LocalDate transactionDate; // 거래 날짜

    @Column(nullable = false)
    private String type; // "INCOME" 또는 "EXPENSE"

    @Column(nullable = false)
    private String category;



    @Builder // 빌더 패턴으로 객체를 생성할 수 있게 함. 데이터 넣을 때 편리
    public Ledger(User user, LocalDate transactionDate, String type, String category, Integer amount, String description, String memo) {
        this.user = user;
        this.transactionDate = transactionDate;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.memo = memo;
    }

    public void update(LocalDate transactionDate, String type, String category, Integer amount, String description, String memo) {
        this.transactionDate = transactionDate;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.memo = memo;
    }
}