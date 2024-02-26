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
public class Room implements Serializable {
    private String roomId;
    private String hotelId;
    private String roomType;
    private String roomStatus;
    private String addressId;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
}
