package com.sj.htlbkg.hotelbooking.repository;

import com.sj.htlbkg.hotelbooking.model.Address;
import com.sj.htlbkg.hotelbooking.model.Hotel;
import com.sj.htlbkg.hotelbooking.model.HotelImage;

import java.util.List;

public interface HotelRepository {
    void create(Hotel hotel, Address address, List<String> images);
    Hotel findHotelById(String hotelId);
    Address findAddressById(String addressId);
    List<Hotel> findAllHotels();
    void update(Hotel hotel, Address address);
    int delete(String hotelId);
    List<HotelImage> findImagesByHotelId(String hotelId);
}
