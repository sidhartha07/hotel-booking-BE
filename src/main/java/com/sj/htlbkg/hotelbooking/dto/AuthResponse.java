package com.sj.htlbkg.hotelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse implements Serializable {
    private String fullName;
    private String email;
    private String token;
}
