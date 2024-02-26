package com.sj.htlbkg.hotelbooking.repository;

import com.sj.htlbkg.hotelbooking.mapper.AddressRowMapper;
import com.sj.htlbkg.hotelbooking.mapper.HotelRowMapper;
import com.sj.htlbkg.hotelbooking.model.Address;
import com.sj.htlbkg.hotelbooking.model.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.sj.htlbkg.hotelbooking.utils.HotelCmnConstants.*;
import static com.sj.htlbkg.hotelbooking.utils.HotelQueries.*;

@Repository
public class HotelRepositoryImpl implements HotelRepository {

    private final Logger logger = LoggerFactory.getLogger(HotelRepositoryImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void create(Hotel hotel, Address address) {
        String addressId = UUID.randomUUID().toString();
        String hotelId = UUID.randomUUID().toString();
        hotel.setHotelId(hotelId);
        hotel.setAddress(addressId);
        address.setAddressId(addressId);
        address.setHotelId(hotelId);
        MapSqlParameterSource addressSqlParameterSource = getAddressSqlParameterSource(address);
        MapSqlParameterSource hotelSqlParameterSource = getHotelSqlParameterSource(hotel);
        int htlinscnt = jdbcTemplate.update(INSERT_HOTEL, hotelSqlParameterSource);
        int addrinscnt = jdbcTemplate.update(INSERT_ADDRESS, addressSqlParameterSource);
        logger.info("insert to t_htl completed with {} count", htlinscnt);
        logger.info("insert to t_addrs for htl_id {} completed with {} count", hotelId, addrinscnt);
    }

    @Override
    public Hotel findHotelById(String hotelId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(HOTEL_ID, hotelId);
        List<Hotel> hotels = jdbcTemplate.query(FIND_HOTEL_BY_ID, map, new HotelRowMapper());
        logger.info("hotel details fetched for hotel_id : {}", hotelId);
        return !CollectionUtils.isEmpty(hotels) ? hotels.get(0) : null;
    }

    @Override
    public Address findAddressById(String hotelId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(HOTEL_ID, hotelId);
        List<Address> addresses = jdbcTemplate.query(FIND_ADDRESS_BY_ID, map, new AddressRowMapper());
        logger.info("address fetched for hotel_id : {}", hotelId);
        return !CollectionUtils.isEmpty(addresses) ? addresses.get(0) : null;
    }

    @Override
    public List<Hotel> findAllHotels() {
        List<Hotel> hotels = jdbcTemplate.query(FIND_ALL_HOTELS, new HotelRowMapper());
        logger.info("hotel details fetched");
        return !CollectionUtils.isEmpty(hotels) ? hotels : null;
    }

    @Override
    public void update(Hotel hotel, Address address) {
        MapSqlParameterSource addressSqlParameterSource = getAddressSqlParameterSource(address);
        MapSqlParameterSource hotelSqlParameterSource = getHotelSqlParameterSource(hotel);
        int htlupdcnt = jdbcTemplate.update(UPDATE_HOTEL, hotelSqlParameterSource);
        int addrupdcnt = jdbcTemplate.update(UPDATE_ADDRESS, addressSqlParameterSource);
        logger.info("update to t_htl for htl_id {} completed with {} count", hotel.getHotelId(), htlupdcnt);
        logger.info("update to t_addrs for htl_id {} completed with {} count", hotel.getHotelId(), addrupdcnt);
    }

    @Override
    public int delete(String hotelId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue(HOTEL_ID, hotelId);
        int addrdltcnt = jdbcTemplate.update(DELETE_ADDRESS, map);
        int htldltcnt = jdbcTemplate.update(DELETE_HOTEL, map);
        logger.info("deletion to t_addrs completed with {} count", addrdltcnt);
        logger.info("deletion to t_htl completed with {} count", htldltcnt);
        return addrdltcnt + htldltcnt;
    }

    private MapSqlParameterSource getAddressSqlParameterSource(Address address) {
        Map<String, Object> map = new HashMap<>();
        map.put(ADDRESS_ID, address.getAddressId());
        map.put(HOTEL_ID, address.getHotelId());
        map.put(CITY, address.getCity());
        map.put(PINCODE, address.getPincode());
        map.put(STATE, address.getState());
        map.put(STREET_NO, address.getStreetNo());
        map.put(LANDMARK, address.getLandmark());
        map.put(CREATED, LocalDateTime.now());
        map.put(UPDATED, LocalDateTime.now());
        return new MapSqlParameterSource(map);
    }

    private MapSqlParameterSource getHotelSqlParameterSource(Hotel hotel) {
        Map<String, Object> map = new HashMap<>();
        map.put(HOTEL_ID, hotel.getHotelId());
        map.put(HOTEL_NAME, hotel.getHotelName());
        map.put(ADDRESS, hotel.getAddress());
        map.put(DESCRIPTION, hotel.getDescription());
        map.put(TYPE, hotel.getType());
        map.put(FACILITIES, hotel.getFacilities());
        map.put(RATING, hotel.getRating());
        map.put(CREATED, LocalDateTime.now());
        map.put(UPDATED, LocalDateTime.now());
        return new MapSqlParameterSource(map);
    }
}
