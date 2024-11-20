package ro.vigi.spring_ai;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.Container;
import org.testcontainers.ollama.OllamaContainer;

import lombok.extern.slf4j.Slf4j;

@TestConfiguration(proxyBeanMethods = false)
@Slf4j
public class TestContainersConfiguration {

//    @Bean
//    @RestartScope
//    @ServiceConnection
//    PostgreSQLContainer<?> pgvectorContainer() {
//        return new PostgreSQLContainer<>(DockerImageName.parse("pgvector/pgvector:pg17"));
//    }

    @Bean
    @RestartScope
    @ServiceConnection
    OllamaContainer ollama() {
        return new OllamaContainer("ollama/ollama")
                .withReuse(true);
    }

    @Bean
    Container.ExecResult pullEmbeddingModel(OllamaContainer ollama,
                                            @Value("${spring.ai.ollama.embedding.options.model}") String embeddingModel)
            throws IOException, InterruptedException {
        log.info("--- Pulling embedding model: [{}].", embeddingModel);
        return ollama.execInContainer("ollama", "pull", embeddingModel);
    }

}
