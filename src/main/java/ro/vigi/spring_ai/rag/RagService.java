package ro.vigi.spring_ai.rag;

import java.util.List;
import java.util.function.Function;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor.DEFAULT_REQUEST_TO_STRING;

@Service
class RagService {

    private static final String SYSTEM_PROMPT = """
                Answer the query strictly referring the provided context:
                {context}
                Query:
                {query}
                In case you don't have any answer from the context provided, just say:
                I'm sorry I don't have the information you are looking for.
            """;

    private final ChatClient chatClient;

    private final DocumentService documentService;

    RagService(ChatClient.Builder builder,
               DocumentService documentService,
               Function<ChatResponse, String> chatResponseToString) {
        this.documentService = documentService;
        ChatOptions chatOptions = ChatOptionsBuilder.builder()
                                                    .withTemperature(0.0D)
                                                    .withMaxTokens(2048)
                                                    .build();
        this.chatClient = builder
                .defaultOptions(chatOptions)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(new SimpleLoggerAdvisor(DEFAULT_REQUEST_TO_STRING, chatResponseToString))
                .build();
    }

    public String chat(String query) {
        return chatClient.prompt(createPrompt(query, documentService.searchData(query)))
                         .call()
                         .content();
    }

    private String createPrompt(String query, List<Document> context) {
        PromptTemplate promptTemplate = new PromptTemplate(SYSTEM_PROMPT);
        promptTemplate.add("query", query);
        promptTemplate.add("context", context);
        return promptTemplate.render();
    }

}
