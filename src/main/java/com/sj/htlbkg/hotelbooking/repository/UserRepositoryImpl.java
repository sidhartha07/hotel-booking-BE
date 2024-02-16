/*

package com.sj.htlbkg.hotelbooking.repository;

import com.sj.htlbkg.hotelbooking.mapper.UserMapper;
import com.sj.htlbkg.hotelbooking.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sj.htlbkg.hotelbooking.utils.UserQueries.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int create(User user) {
        MapSqlParameterSource sqlParameterSource = getSqlParameterSource(user);
        int inscnt = jdbcTemplate.update(INSERT_USER, sqlParameterSource);
        logger.info("Insert to T_User completed with {} count", inscnt);
        return inscnt;
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = jdbcTemplate.query(FIND_USER_BY_EMAIL, new MapSqlParameterSource("email", username), new UserMapper());
        return users.stream().findFirst().isPresent() ? users.stream().findFirst().get() : null;
    }

    @Override
    public User login(String email, String password) {
        List<User> users = jdbcTemplate.query(FIND_USER_BY_EMAIL_PSWD, new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("password", password), new UserMapper());
        return users.stream().findFirst().isPresent() ? users.stream().findFirst().get() : null;
    }

    private MapSqlParameterSource getSqlParameterSource(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("password", user.getPassword());
        map.put("firstName", user.getFirstName());
        map.put("lastName", user.getLastName());
        map.put("address", user.getAddress());
        map.put("phoneNumber", user.getPhoneNumber());
        map.put("profileImage", user.getProfileImage());
        return new MapSqlParameterSource(map);
    }
}


 */