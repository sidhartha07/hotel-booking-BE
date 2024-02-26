package com.sj.htlbkg.hotelbooking.mapper;

import com.sj.htlbkg.hotelbooking.model.Hotel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelRowMapper implements RowMapper<Hotel> {
    @Override
    public Hotel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Hotel.builder()
                .hotelId(rs.getString("htl_id"))
                .hotelName(rs.getString("htl_nm"))
                .address(rs.getString("addrs"))
                .description(rs.getString("htl_dsc"))
                .type(rs.getString("htl_typ"))
                .facilities(rs.getString("fclts"))
                .rating(rs.getDouble("rtng"))
                .build();
    }
}
