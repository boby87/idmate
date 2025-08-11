package ktv.cm.idmate.exception;

import ktv.cm.idmate.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> ValidationErrors = new HashMap<>();
        List<ObjectError> ValidationErrorsList = ex.getBindingResult().getAllErrors();
        for (ObjectError error : ValidationErrorsList) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            ValidationErrors.put(fieldName, errorMessage);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)  // on retourne le ResponseEntity avec generalement 3 elements; le status de type HttpStatus, le body qui contient l'objet Dto et le headers qui est facultatif dans ce cas
                .body(new ErrorDto(HttpStatus.BAD_REQUEST, ValidationErrors.toString()));

    }
}
