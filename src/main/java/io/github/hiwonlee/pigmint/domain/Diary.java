package io.github.hiwonlee.pigmint.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob // 긴 텍스트를 저장하기 위한 어노테이션
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    // Diary(1) : Ledger(1) 관계
    // Diary가 Ledger에 대한 외래 키를 가집니다.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_id", nullable = false)
    private Ledger ledger;

    @Builder
    public Diary(String title, String content, Ledger ledger) {
        this.title = title;
        this.content = content;
        this.ledger = ledger;
        this.createdAt = LocalDateTime.now();
    }
}