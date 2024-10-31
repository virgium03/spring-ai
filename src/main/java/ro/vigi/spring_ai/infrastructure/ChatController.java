package ro.vigi.spring_ai.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatController {

    private final ChatClient chatClient;

    private final VectorStore vectorStore;

    ChatController(ChatClient.Builder builder, VectorStore vectorStore) {
        this.vectorStore = vectorStore;
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor(), new QuestionAnswerAdvisor(vectorStore))
                .build();
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String query) {
        String systemPromptTemplate = """
                You are a helpful assistant, conversing with a user about the subjects contained in a set of documents.
                Use the information from the DOCUMENTS section to provide accurate answers. If unsure or if the answer
                isn't found in the DOCUMENTS section, simply state that you don't know the answer and do not mention
                the DOCUMENTS section.
                
                DOCUMENTS:
                {documents}
                """;
        List<Document> similarDocuments = vectorStore
                .similaritySearch(SearchRequest.query(query).withTopK(10));
        String documentsText = similarDocuments.stream()
                                               .map(Document::getContent)
                                               .collect(Collectors.joining(System.lineSeparator()));

        return chatClient
                .prompt()
                .system(spec -> spec.text(systemPromptTemplate)
                                    .param("documents", documentsText))
                .user(query)
                .call()
                .content();

    }

}
