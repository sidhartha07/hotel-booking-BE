package com.sj.htlbkg.hotelbooking.service;

import com.sj.htlbkg.hotelbooking.dto.AddressDto;
import com.sj.htlbkg.hotelbooking.dto.HotelDto;
import com.sj.htlbkg.hotelbooking.dto.HotelRequest;
import com.sj.htlbkg.hotelbooking.model.Address;
import com.sj.htlbkg.hotelbooking.model.Hotel;
import com.sj.htlbkg.hotelbooking.model.HotelImage;
import com.sj.htlbkg.hotelbooking.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    private static final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public void saveHotel(HotelRequest request, List<MultipartFile> images) throws IOException {
        Address address = mapToAddrsEntity(request);
        String facilities = String.join(",", request.getFacilities());
        Hotel hotel = mapToHotelEntity(request, facilities);
        List<String> imgUrls = new ArrayList<>();
        if (!CollectionUtils.isEmpty(images)) {
            imgUrls = saveImagesToCloudinary(images);
        }
        hotelRepository.create(hotel, address, imgUrls);
    }

    private List<String> saveImagesToCloudinary(List<MultipartFile> images) {
        List<String> imgUrls = new ArrayList<>();
        for(MultipartFile img : images) {
            String imgUrl = cloudinaryService.uploadImage(img, "htl_imgs");
            if(!StringUtils.hasText(imgUrl)) {
                throw new RuntimeException();
            }
            imgUrls.add(imgUrl);
        }
        return imgUrls;
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
        List<HotelImage> imgs = hotelRepository.findImagesByHotelId(hotelId);
        List<String> imgUrls = new ArrayList<>();
        if (!CollectionUtils.isEmpty(imgs)) {
            for (HotelImage img : imgs) {
                imgUrls.add(img.getImageUrl());
            }
        }
        HotelDto hotelDto = mapToHotelDto(hotel, addressDto);
        hotelDto.setImageUrls(imgUrls);
        return hotelDto;
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
                List<String> imgUrls = hotelRepository.findImagesByHotelId(hotel.getHotelId()).stream().map(HotelImage::getImageUrl).toList();
                hotelDto.setImageUrls(imgUrls);
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
    @Transactional
    public void deleteHotel(String hotelId) {
        List<HotelImage> imgs = hotelRepository.findImagesByHotelId(hotelId);
        int dltcnt = hotelRepository.delete(hotelId);
        List<String> publicIds = imgs.stream().map(img -> img.getImageUrl().substring(53)).toList();
        for (String publicId : publicIds) {
            cloudinaryService.deleteImage(publicId);
        }
        if (dltcnt != 2 + publicIds.size()) {
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
