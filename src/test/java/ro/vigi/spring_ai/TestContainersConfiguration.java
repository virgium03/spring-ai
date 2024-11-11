package ro.vigi.spring_ai;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.testcontainers.ollama.OllamaContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfiguration {

//    @Bean
//    @RestartScope
//    @ServiceConnection
//    OllamaContainer ollama() {
//        return new OllamaContainer(DockerImageName.parse("docker.io/ollama/ollama")
//                                                  .asCompatibleSubstituteFor("ollama/ollama"));
//    }

//    @Bean
//    @RestartScope
//    @ServiceConnection
//    PostgreSQLContainer<?> pgvectorContainer() {
//        return new PostgreSQLContainer<>(DockerImageName.parse("pgvector/pgvector:pg17"));
//    }

    @Bean
    @Profile("ollama-image")
    @RestartScope
    @ServiceConnection
    OllamaContainer ollama() {
        return new OllamaContainer("ollama/ollama")
                .withReuse(true);
    }

}
