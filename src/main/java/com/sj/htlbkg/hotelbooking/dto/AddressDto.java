package com.sj.htlbkg.hotelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto implements Serializable {
    private String city;
    private String pincode;
    private String state;
    private String streetNo;
    private String landMark;
}
