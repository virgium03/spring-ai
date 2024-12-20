package ro.vigi.spring_ai.rag;

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

    private final DocumentService documentService;

    DocumentsController(DocumentService documentService) {
        this.documentService = documentService;
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

            documentService.addPdfDocument(file.getResource());

            messageType = "alert-info";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            message = "Failed to upload " + file.getOriginalFilename() + "!";
            messageType = "alert-danger";
        }
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("messageType", messageType);
        return "redirect:/uploadDocuments";
    }

}
