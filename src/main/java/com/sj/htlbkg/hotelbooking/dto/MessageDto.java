package com.sj.htlbkg.hotelbooking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class MessageDto implements Serializable {
    private String code;
    private String message;

    public MessageDto() {
    }

    public MessageDto(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
