package ro.vigi.spring_ai.classification;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class TextClassifier {

    private final ChatClient chatClient;

    TextClassifier(ChatClient.Builder builder) {
        ChatOptions chatOptions = ChatOptionsBuilder.builder()
                .withTemperature(0.0D)
                .build();
        chatClient = builder.defaultOptions(chatOptions)
                .build();
    }

    ClassificationType classify(String text) {
        return chatClient
                .prompt()
                .messages(getPromptWithFewShotsHistory())
                .user(text)
                .call()
                .entity(ClassificationType.class);
    }

    private List<Message> getPromptWithFewShotsHistory() {
        return List.of(
                new SystemMessage("""
                        Classify the provided text into one of these classes.
                        
                        BUSINESS: Commerce, finance, markets, entrepreneurship, corporate developments.
                        SPORT: Athletic events, tournament outcomes, performances of athletes and teams.
                        TECHNOLOGY: innovations and trends in software, artificial intelligence, cybersecurity.
                        OTHER: Anything that doesn't fit into the other categories.
                        """),

                new UserMessage("Apple Vision Pro and the New UEFA Euro App Deliver an Innovative Entertainment Experience."),
                new AssistantMessage("TECHNOLOGY"),
                new UserMessage("Wall Street, Trading Volumes Reach All-Time Highs Amid Market Optimism."),
                new AssistantMessage("BUSINESS"),
                new UserMessage("Sony PlayStation 6 Launch, Next-Gen Gaming Experience Redefines Console Performance."),
                new AssistantMessage("TECHNOLOGY"),
                new UserMessage("Water Polo Star Secures Landmark Contract with Major League Team."),
                new AssistantMessage("SPORT"),
                new UserMessage("Culinary Travel, Best Destinations for Food Lovers This Year!"),
                new AssistantMessage("OTHER"),
                new UserMessage("UEFA Euro 2024, Memorable Matches and Record-Breaking Goals Define Tournament Highlights."),
                new AssistantMessage("SPORT"),
                new UserMessage("Rock Band Resurgence, Legendary Groups Return to the Stage with Iconic Performances."),
                new AssistantMessage("OTHER")
        );
    }

}
