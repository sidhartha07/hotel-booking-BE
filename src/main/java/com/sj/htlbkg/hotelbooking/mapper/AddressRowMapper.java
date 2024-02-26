package com.sj.htlbkg.hotelbooking.mapper;

import com.sj.htlbkg.hotelbooking.model.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Address.builder()
                .addressId(rs.getString("addrs_id"))
                .hotelId(rs.getString("htl_id"))
                .city(rs.getString("cty"))
                .pincode(rs.getString("pin"))
                .state(rs.getString("ste"))
                .streetNo(rs.getString("str_no"))
                .landmark(rs.getString("lnd_mrk"))
                .build();
    }
}
