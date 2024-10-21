package ro.vigi.spring_ai;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.ollama.OllamaContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfiguration {

    @Bean
    @RestartScope
//    @ServiceConnection
    OllamaContainer ollama() {
        return new OllamaContainer(DockerImageName.parse("docker.io/ollama/ollama")
                                                  .asCompatibleSubstituteFor("ollama/ollama"));
    }

}
