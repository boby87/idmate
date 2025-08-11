package ktv.cm.idmate.dto;

import org.springframework.web.multipart.MultipartFile;

public record DocumentRequest(String documentType,
                              MultipartFile document) {
}
