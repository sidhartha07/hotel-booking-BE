package com.sj.htlbkg.hotelbooking.service;

import com.sj.htlbkg.hotelbooking.dto.AddressDto;
import com.sj.htlbkg.hotelbooking.dto.HotelDto;
import com.sj.htlbkg.hotelbooking.dto.HotelRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HotelService {
    void saveHotel(HotelRequest request, List<MultipartFile> images) throws IOException;
    HotelDto findHotelById(String hotelId);
    List<HotelDto> findAllHotels();
    void updateHotel(HotelRequest request, String hotelId);
    void deleteHotel(String hotelId);
}
