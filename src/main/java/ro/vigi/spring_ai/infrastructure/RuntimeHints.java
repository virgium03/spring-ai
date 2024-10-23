package ro.vigi.spring_ai.infrastructure;

import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class RuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(org.springframework.aot.hint.RuntimeHints hints, ClassLoader classLoader) {
        hints.resources().registerPattern("db/*"); // https://github.com/spring-projects/spring-boot/issues/32654
        hints.resources().registerPattern("messages/*");
        hints.resources().registerPattern("META-INF/resources/webjars/*");
    }

}
