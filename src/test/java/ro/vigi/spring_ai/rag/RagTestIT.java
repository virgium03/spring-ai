package ro.vigi.spring_ai.rag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;

import ro.vigi.spring_ai.TestContainersConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestContainersConfiguration.class)
class RagTestIT {

    @Autowired
    private RagService ragService;

    @Autowired
    private DocumentService documentService;

    @Value("classpath:/rag/bauknecht.pdf")
    private Resource pdfDocument;

    @Test
    @DisplayName("Should respond to a question related to an uploaded document")
    void should_respond_to_a_question_related_to_an_uploaded_document() {
        documentService.addPdfDocument(pdfDocument);

        String response = ragService.chat("What is the energy consumption per year of the appliance?");

        assertThat(response).contains("216 kWh");
    }

    @Test
    @DisplayName("Should respond to a question not related to the uploaded documents")
    void should_respond_to_a_question_not_related_to_the_uploaded_documents() {
        String response = ragService.chat("Who is Hodor?");

        assertThat(response).contains("I'm sorry I don't have the information you are looking for.");
    }
}
