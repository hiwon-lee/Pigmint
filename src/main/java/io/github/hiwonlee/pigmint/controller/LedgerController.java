package io.github.hiwonlee.pigmint.controller;

import io.github.hiwonlee.pigmint.domain.Ledger;
import io.github.hiwonlee.  pigmint.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller // 이 클래스가 웹 요청을 처리하는 '컨트롤러'임을 선언합니다.
@RequiredArgsConstructor // final이 붙은 필드를 인자로 받는 생성자를 자동으로 만들어줍니다. (의존성 주입)
public class LedgerController {

    private final LedgerRepository ledgerRepository; // DB 작업을 위해 Repository를 주입받습니다.

    @GetMapping("/") // 사용자가 웹 브라우저에서 '/' 주소로 접속하면 이 메서드가 실행됩니다.
    public String ledgerList(Model model) {
        // 1. DB에서 모든 가계부 내역을 조회합니다.
        List<Ledger> ledgers = ledgerRepository.findAll();

        // 2. 조회한 데이터를 'ledgers'라는 이름으로 모델에 담습니다.
        //    (모델은 서버에서 화면으로 데이터를 전달하는 가방 같은 역할입니다.)
        model.addAttribute("ledgers", ledgers);

        // 3. 'ledgerList.html' 파일을 찾아서 화면에 보여달라고 응답합니다.
        return "ledgerList";
    }
}