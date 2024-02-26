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
public class HotelImage implements Serializable {
    private String imageId;
    private String hotelId;
    private String imageUrl;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
}
