package ro.vigi.spring_ai.classification;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ClassificationController {

    private final TextClassifier textClassifier;

    ClassificationController(TextClassifier textClassifier) {
        this.textClassifier = textClassifier;
    }

    @PostMapping("/classifyText")
    String classifyText(@RequestBody String text) {
        return textClassifier.classifyText(text);
    }

    @PostMapping("/classifyStructuredText")
    ClassificationType classifyStructuredText(@RequestBody String text) {
        return textClassifier.classifyStructuredText(text);
    }
}
