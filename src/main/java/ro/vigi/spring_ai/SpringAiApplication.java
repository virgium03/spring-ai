package ro.vigi.spring_ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

import ro.vigi.spring_ai.infrastructure.RuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(RuntimeHints.class)
public class SpringAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiApplication.class, args);
	}

}
