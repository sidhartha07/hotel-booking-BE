package com.sj.htlbkg.hotelbooking.service;

import com.sj.htlbkg.hotelbooking.dto.HotelDto;
import com.sj.htlbkg.hotelbooking.dto.HotelRequest;
import com.sj.htlbkg.hotelbooking.model.Address;
import com.sj.htlbkg.hotelbooking.model.Hotel;
import com.sj.htlbkg.hotelbooking.repository.HotelRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {
    @InjectMocks
    private HotelServiceImpl hotelService = new HotelServiceImpl();
    @Mock
    private HotelRepository hotelRepository;

    /*
    @Test
    void when_SaveHotel_Is_HotelRequest_Valid_Should_Save_Hotel() {
        Address address = Address.builder()
                .city("cty")
                .pincode("pin")
                .state("state")
                .streetNo("str")
                .landmark("lndmrk")
                .build();
        HotelRequest request = HotelRequest.builder()
                .hotelName("htl")
                .description("desc")
                .type("typ")
                .facilities(List.of("sprts","ac"))
                .rating(7.0)
                .address(address)
                .build();
        String facilities = String.join(",", request.getFacilities());
        Hotel hotel = Hotel.builder()
                .hotelName(request.getHotelName())
                .description(request.getDescription())
                .type(request.getType())
                .facilities(facilities)
                .rating(request.getRating())
                .build();
        hotelService.saveHotel(request);
        verify(hotelRepository, times(1)).create(any(Hotel.class), any(Address.class));
    }

    @Before(value = "when_FindHotelById_Is_Hotel_Null_Should_Throw_Runtime_Exception")
    void setup() {
        Hotel hotel = Hotel.builder()
                .hotelId("hotelId")
                .hotelName("htl")
                .build();
        Address address = Address.builder()
                .addressId("addrsId")
                .hotelId(hotel.getHotelId())
                .build();
        doReturn(hotel).when(hotelRepository).findHotelById("hotelId");
        doReturn(address).when(hotelRepository).findAddressById("hotelId");
    }

    @Test
    void when_FindHotelById_Is_Hotel_Null_Should_Throw_Runtime_Exception() {
        doReturn(null).when(hotelRepository).findHotelById("invalidId");
        doReturn(null).when(hotelRepository).findAddressById("invalidId");
        assertThrows(RuntimeException.class, () -> hotelService.findHotelById("invalidId"));
        verify(hotelRepository, times(1)).findHotelById(anyString());
        verify(hotelRepository, times(1)).findAddressById(anyString());
    }

     */
}
