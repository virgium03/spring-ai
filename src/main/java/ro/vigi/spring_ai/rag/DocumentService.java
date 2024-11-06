package ro.vigi.spring_ai.rag;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
class DocumentService {

    private final VectorStore vectorStore;

    DocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    List<Document> searchData(String query) {
        return vectorStore.similaritySearch(query);
    }
}
