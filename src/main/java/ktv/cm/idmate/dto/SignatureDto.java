package ktv.cm.idmate.dto;

public record SignatureDto(String base64Signature,
                               boolean useNameAsSignature) {
}
