package com.sj.htlbkg.hotelbooking.exception;

import com.sj.htlbkg.hotelbooking.dto.ResponseMsgDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidRequestException extends RuntimeException {
    private static final long serialVersionUID = 30L;

    private final HttpStatus status;
    private final transient Object error;
    private final ResponseMsgDto responseMsgDto;

    public InvalidRequestException(String message) {
        super(message);
        status = HttpStatus.BAD_REQUEST;
        this.error = message;
        this.responseMsgDto = null;
    }

    public InvalidRequestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.error = message;
        this.responseMsgDto = null;
    }

    public InvalidRequestException(HttpStatus status, Exception e, ResponseMsgDto responseMsgDto) {
        super(e);
        this.status = status;
        this.error = e;
        this.responseMsgDto = responseMsgDto;
    }

    public InvalidRequestException(HttpStatus status, Error e, ResponseMsgDto responseMsgDto) {
        super(responseMsgDto.getException());
        this.status = status;
        this.error = e;
        this.responseMsgDto = null;
    }

    public InvalidRequestException(HttpStatus status, ResponseMsgDto responseMsgDto) {
        super(responseMsgDto.getException());
        this.status = status;
        this.error = responseMsgDto;
        this.responseMsgDto = responseMsgDto;

    }
}
