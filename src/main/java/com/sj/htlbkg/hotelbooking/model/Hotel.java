package com.sj.htlbkg.hotelbooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel implements Serializable {
    private String hotelId;
    private String hotelName;
    private String address;
    private String description;
    private String type;
    private String facilities;
    private Double rating;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
}
