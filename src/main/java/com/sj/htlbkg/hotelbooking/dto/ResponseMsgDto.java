package com.sj.htlbkg.hotelbooking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class ResponseMsgDto implements Serializable {
    private String exception;
    private List<MessageDto> messages;

    public ResponseMsgDto(String exception, List<MessageDto> messages) {
        this.exception = exception;
        this.messages = messages;
    }

    public ResponseMsgDto() {
    }
}
