package com.sj.htlbkg.hotelbooking.dto;

import com.sj.htlbkg.hotelbooking.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDto implements Serializable {
    private String hotelId;
    private String hotelName;
    private AddressDto address;
    private String description;
    private String type;
    private List<String> facilities;
    private Double rating;
    private List<String> imageUrls;
}
