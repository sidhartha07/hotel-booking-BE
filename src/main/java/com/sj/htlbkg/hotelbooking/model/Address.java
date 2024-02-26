package com.sj.htlbkg.hotelbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address implements Serializable {
    private String addressId;
    private String hotelId;
    private String city;
    private String pincode;
    private String state;
    private String streetNo;
    private String landmark;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
}
