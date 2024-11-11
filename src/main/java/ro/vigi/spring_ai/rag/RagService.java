package ro.vigi.spring_ai.rag;

import java.util.function.Function;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor.DEFAULT_REQUEST_TO_STRING;

@Service
class RagService {

    private final ChatClient chatClient;

    private final DocumentService documentService;

    private final Resource systemPrompt;

    RagService(ChatClient.Builder builder,
               DocumentService documentService,
               Function<ChatResponse, String> chatResponseToString,
               @Value("classpath:/prompts/rag-system-prompt.txt") Resource systemPrompt) {
        this.documentService = documentService;
        this.systemPrompt = systemPrompt;
        ChatOptions chatOptions = ChatOptionsBuilder.builder()
                                                    .withTemperature(0.0D)
                                                    .withMaxTokens(2048)
                                                    .build();
        this.chatClient = builder
                .defaultOptions(chatOptions)
                .defaultSystem(systemPrompt)
                .defaultAdvisors(new SimpleLoggerAdvisor(DEFAULT_REQUEST_TO_STRING, chatResponseToString))
                .build();
    }

    public String chat(String query) {
        return chatClient.prompt(createPrompt(query))
                         .call()
                         .content();
    }

    private String createPrompt(String query) {
        PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
        promptTemplate.add("query", query);
        promptTemplate.add("context", documentService.searchData(query));
        return promptTemplate.render();
    }

}
