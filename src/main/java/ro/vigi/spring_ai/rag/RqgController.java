package ro.vigi.spring_ai.rag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RqgController {

    private final RagService ragService;

    RqgController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/rag-chat")
    public String chat(@RequestBody String query) {
        return ragService.chat(query);
    }

}
