package ktv.cm.idmate.dto.faceCompare;

import java.util.List;
import java.util.Map;

public record FaceCompareResponse(
        String request_id,
        int time_used,
        double confidence,
        Map<String, Double> thresholds,
        String image_id1,
        String image_id2
) {
}
