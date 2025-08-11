package ktv.cm.idmate.service.serviceImpl;

import ktv.cm.idmate.dto.faceCompare.FaceCompareResponse;
import lombok.With;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
public class FaceCompareImpl {

    @Value("${face.api.key}")
    private String apiKey;
    @Value("${face.api.secret}")
    private String apiSecret;
    private final WebClient webClient;

    public FaceCompareImpl(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api-us.faceplusplus.com/facepp/v3/compare").build();
    }

    public FaceCompareResponse compareFaces(MultipartFile file1, MultipartFile file2) throws IOException {
        return webClient.post()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("api_key", apiKey)
                        .with("api_secret", apiSecret)
                        .with("image_file1", file1.getResource())
                        .with("image_file2", file2.getResource()))
                .retrieve()
                .bodyToMono(FaceCompareResponse.class)
                .block();
    }
}
