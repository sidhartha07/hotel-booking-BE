package com.sj.htlbkg.hotelbooking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class HotelRequest implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String hotelName;
    private String description;
    private String type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> facilities;
    private Double rating;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Address address;
}
