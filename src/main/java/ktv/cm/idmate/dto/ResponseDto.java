package ktv.cm.idmate.dto;

import org.springframework.http.HttpStatus;

public record ResponseDto(HttpStatus  status, String message) {

    public ResponseDto(HttpStatus status) {
        this(status, null);
    }

    public ResponseDto(String message) {
        this(HttpStatus.OK, message);
    }

    public ResponseDto() {
        this(HttpStatus.OK, null);
    }
}
