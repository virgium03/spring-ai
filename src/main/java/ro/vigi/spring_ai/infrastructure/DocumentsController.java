package ro.vigi.spring_ai.infrastructure;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
class DocumentsController {

    private final VectorStore vectorStore;

    DocumentsController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @GetMapping("/uploadDocuments")
    String documents() {
        return "/upload_document";
    }

    @PostMapping("/uploadDocument")
    String uploadDocument(@RequestParam("file") MultipartFile file,
                          RedirectAttributes redirectAttributes) {

        String message = "";
        String messageType = "";
        try {
            log.info("--- Uploading file: [{}].", file.getOriginalFilename());
            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            TextSplitter textSplitter = new TokenTextSplitter();
            vectorStore.accept(textSplitter.apply(getDocsFromPdf(file)));

            messageType = "alert-info";
        } catch (Exception e) {
            message = "Failed to upload " + file.getOriginalFilename() + "!";
            messageType = "alert-danger";
        }
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("messageType", messageType);
        return "redirect:/uploadDocuments";
    }

    List<Document> getDocsFromPdf(MultipartFile file) {

        ExtractedTextFormatter extractedTextFormatter =
                ExtractedTextFormatter.builder()
                                      .withNumberOfTopPagesToSkipBeforeDelete(0)
                                      .withNumberOfBottomTextLinesToDelete(1)
                                      .withNumberOfTopTextLinesToDelete(1)
                                      .build();
        PdfDocumentReaderConfig readerConfig =
                PdfDocumentReaderConfig.builder()
                                       .withPageExtractedTextFormatter(extractedTextFormatter)
                                       .withPagesPerDocument(1)
                                       .build();

        DocumentReader pdfReader = new PagePdfDocumentReader(file.getResource(), readerConfig);

        return pdfReader.get();
    }
}
