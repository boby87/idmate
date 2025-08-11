package ktv.cm.idmate.dto;

import org.springframework.http.HttpStatus;

public record ErrorDto(HttpStatus errorCode, String errorMessage) {

}
