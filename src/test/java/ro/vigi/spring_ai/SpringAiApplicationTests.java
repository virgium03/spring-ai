package ro.vigi.spring_ai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

class SpringAiApplicationTests {

	public static void main(String[] args) {
		SpringApplication.from(SpringAiApplication::main)
						 .with(TestContainersConfiguration.class)
						 .run(args);
	}

}
