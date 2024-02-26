package com.sj.htlbkg.hotelbooking.service;

import com.sj.htlbkg.hotelbooking.dto.AddressDto;
import com.sj.htlbkg.hotelbooking.dto.HotelDto;
import com.sj.htlbkg.hotelbooking.dto.HotelRequest;
import com.sj.htlbkg.hotelbooking.model.Address;
import com.sj.htlbkg.hotelbooking.model.Hotel;
import com.sj.htlbkg.hotelbooking.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    @Transactional
    public void saveHotel(HotelRequest request) {
        Address address = mapToAddrsEntity(request);
        String facilities = String.join(",", request.getFacilities());
        Hotel hotel = mapToHotelEntity(request, facilities);
        hotelRepository.create(hotel, address);
    }

    @Override
    public HotelDto findHotelById(String hotelId) {
        Hotel hotel = hotelRepository.findHotelById(hotelId);
        Address address = hotelRepository.findAddressById(hotelId);
        AddressDto addressDto = AddressDto.builder().build();
        if (address != null) {
            addressDto = mapToAddrsDto(address);
        }
        if (hotel == null) {
            throw new RuntimeException();
        }
        return mapToHotelDto(hotel, addressDto);
    }

    @Override
    public List<HotelDto> findAllHotels() {
        List<Hotel> hotels = hotelRepository.findAllHotels();
        List<HotelDto> hotelDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(hotels)) {
            for (Hotel hotel : hotels) {
                Address address = hotelRepository.findAddressById(hotel.getHotelId());
                AddressDto addressDto = mapToAddrsDto(address);
                HotelDto hotelDto = mapToHotelDto(hotel, addressDto);
                hotelDtos.add(hotelDto);
            }
        } else {
            throw new RuntimeException();
        }
        return hotelDtos;
    }

    @Override
    @Transactional
    public void updateHotel(HotelRequest request, String hotelId) {
        Hotel htl = hotelRepository.findHotelById(hotelId);
        Address addrs = hotelRepository.findAddressById(hotelId);
        addrs.setCity(request.getAddress().getCity());
        addrs.setPincode(request.getAddress().getPincode());
        addrs.setState(request.getAddress().getState());
        addrs.setStreetNo(request.getAddress().getStreetNo());
        addrs.setLandmark(request.getAddress().getLandmark());
        String facilities = String.join(",", request.getFacilities());
        htl.setHotelName(request.getHotelName());
        htl.setDescription(request.getDescription());
        htl.setFacilities(facilities);
        htl.setType(request.getType());
        htl.setRating(request.getRating());
        hotelRepository.update(htl, addrs);
    }

    @Override
    public void deleteHotel(String hotelId) {
        int dltcnt = hotelRepository.delete(hotelId);
        if(dltcnt != 2) {
            throw new RuntimeException();
        }
    }

    private static AddressDto mapToAddrsDto(Address address) {
        return AddressDto.builder()
                .city(address.getCity())
                .pincode(address.getPincode())
                .state(address.getState())
                .streetNo(address.getStreetNo())
                .landMark(address.getLandmark())
                .build();
    }

    private static HotelDto mapToHotelDto(Hotel hotel, AddressDto addressDto) {
        return HotelDto.builder()
                .hotelId(hotel.getHotelId())
                .hotelName(hotel.getHotelName())
                .address(addressDto)
                .description(hotel.getDescription())
                .type(hotel.getType())
                .facilities(List.of(hotel.getFacilities().split(",")))
                .rating(hotel.getRating())
                .build();
    }

    private static Address mapToAddrsEntity(HotelRequest request) {
        return Address.builder()
                .city(request.getAddress().getCity())
                .pincode(request.getAddress().getPincode())
                .state(request.getAddress().getState())
                .streetNo(request.getAddress().getStreetNo())
                .landmark(request.getAddress().getLandmark())
                .build();
    }

    private static Hotel mapToHotelEntity(HotelRequest request, String facilities) {
        return Hotel.builder()
                .hotelName(request.getHotelName())
                .description(request.getDescription())
                .type(request.getType())
                .facilities(facilities)
                .rating(request.getRating())
                .build();
    }

}
