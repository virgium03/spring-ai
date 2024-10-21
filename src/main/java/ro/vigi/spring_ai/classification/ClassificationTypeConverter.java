package ro.vigi.spring_ai.classification;

import org.springframework.ai.converter.StructuredOutputConverter;

/**
 * Converts an AI response to a {@link ClassificationType} enum instance.
 */
final class ClassificationTypeConverter implements StructuredOutputConverter<ClassificationType> {

    @Override
    public String getFormat() {
        return "json";
    }

    @Override
    public ClassificationType convert(String source) {
        String text = source.trim();

        // Check for and remove triple backticks and "json" identifier
        if (text.startsWith("```") && text.endsWith("```")) {
            // Remove the first line if it contains "```json"
            String[] lines = text.split("\n", 2);
            if (lines[0].trim().equalsIgnoreCase("```json")) {
                text = lines.length > 1 ? lines[1] : "";
            }
            else {
                text = text.substring(3); // Remove leading ```
            }

            // Remove trailing ```
            text = text.substring(0, text.length() - 3);

            // Trim again to remove any potential whitespace
            text = text.trim();
        }
        return ClassificationType.valueOf(text.toUpperCase());
    }
}
