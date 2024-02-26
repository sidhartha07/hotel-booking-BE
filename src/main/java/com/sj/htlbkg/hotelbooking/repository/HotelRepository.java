package com.sj.htlbkg.hotelbooking.repository;

import com.sj.htlbkg.hotelbooking.model.Address;
import com.sj.htlbkg.hotelbooking.model.Hotel;

import java.util.List;

public interface HotelRepository {
    void create(Hotel hotel, Address address);
    Hotel findHotelById(String hotelId);
    Address findAddressById(String addressId);
    List<Hotel> findAllHotels();
    void update(Hotel hotel, Address address);
    int delete(String hotelId);
}
