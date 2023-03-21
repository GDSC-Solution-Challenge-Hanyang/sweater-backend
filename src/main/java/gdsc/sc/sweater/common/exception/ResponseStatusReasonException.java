package gdsc.sc.sweater.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@Getter
public abstract class ResponseStatusReasonException extends ResponseStatusException {

    private final HttpStatusCode statusCode;
    private final String reasonName;
    private final String reasonMessage;
    public ResponseStatusReasonException(HttpStatusCode statusCode, String reasonName, String reasonMessage) {
        super(statusCode, reasonMessage);
        this.statusCode = statusCode;
        this.reasonName = reasonName;
        this.reasonMessage = reasonMessage;
    }

}
