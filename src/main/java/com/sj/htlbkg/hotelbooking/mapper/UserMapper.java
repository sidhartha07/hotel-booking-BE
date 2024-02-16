package com.sj.htlbkg.hotelbooking.mapper;

import com.sj.htlbkg.hotelbooking.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getString("user_id"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("fst_nme"));
        user.setLastName(rs.getString("lst_nme"));
        user.setAddress(rs.getString("addrs"));
        user.setPhoneNumber(rs.getString("phn_nmbr"));
        user.setProfileImage(rs.getString("prfl_img"));
        return user;
    }
}
