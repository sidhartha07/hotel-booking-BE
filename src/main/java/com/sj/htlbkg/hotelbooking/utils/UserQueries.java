package com.sj.htlbkg.hotelbooking.utils;

public class UserQueries {
    public static final String INSERT_USER = """
            INSERT INTO T_USER (EMAIL, PSWD, FST_NME, LST_NME, ADDRS, PHN_NMBR, PRFL_IMG)
            VALUES (:email,:password,:firstName,:lastName,:address,:phoneNumber,:profileImage);
            """;

    public static final String FIND_USER_BY_EMAIL = """
            SELECT * FROM T_USER WHERE email = :email;
            """;

    public static final String FIND_USER_BY_EMAIL_PSWD = """
            SELECT * FROM T_USER WHERE email = :email AND pswd = :password;
            """;
}
