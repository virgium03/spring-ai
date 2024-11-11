package ro.vigi.spring_ai.rag;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
class DocumentService {

    private final VectorStore vectorStore;

    DocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    List<Document> searchData(String query) {
        return vectorStore.similaritySearch(query);
    }

    void addPdfDocument(Resource pdfContent) {
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(getDocsFromPdf(pdfContent)));
    }

    List<Document> getDocsFromPdf(Resource pdfContent) {
        log.debug("--- Extracting text from PDF file: [{}].", pdfContent.getFilename());

//        ExtractedTextFormatter extractedTextFormatter =
//                ExtractedTextFormatter.builder()
//                                      .withNumberOfTopPagesToSkipBeforeDelete(0)
//                                      .withNumberOfBottomTextLinesToDelete(1)
//                                      .withNumberOfTopTextLinesToDelete(1)
//                                      .build();
//        PdfDocumentReaderConfig readerConfig =
//                PdfDocumentReaderConfig.builder()
//                                       .withPageExtractedTextFormatter(extractedTextFormatter)
//                                       .withPagesPerDocument(1)
//                                       .build();
//
//        DocumentReader pdfReader = new PagePdfDocumentReader(file.getResource(), readerConfig);
//
//        return pdfReader.get();
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(pdfContent);
        return tikaDocumentReader.read();
    }
}
