package com.sj.htlbkg.hotelbooking.mapper;

import com.sj.htlbkg.hotelbooking.model.HotelImage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelImageRowMapper implements RowMapper<HotelImage> {
    @Override
    public HotelImage mapRow(ResultSet rs, int rowNum) throws SQLException {
        return HotelImage.builder()
                .imageUrl(rs.getString("img_url"))
                .build();
    }
}
