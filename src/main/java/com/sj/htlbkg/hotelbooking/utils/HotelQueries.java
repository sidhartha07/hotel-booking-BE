package com.sj.htlbkg.hotelbooking.utils;

public class HotelQueries {
    public static final String INSERT_HOTEL = """
            INSERT INTO t_htl (htl_id, htl_nm, addrs, htl_dsc, htl_typ, fclts, rtng, cr_dtm, upd_dtm)
            VALUES (:hotelId,:hotelName,:address,:description,:type,:facilities,:rating,:created,:updated);
            """;
    public static final String INSERT_ADDRESS = """
            INSERT INTO t_addrs (addrs_id, htl_id, cty, pin, ste, str_no, lnd_mrk, cr_dtm, upd_dtm)
            VALUES (:addressId,:hotelId,:city,:pincode,:state,:streetNo,:landmark,:created,:updated);
            """;
    public static final String INSERT_HOTEL_IMAGE = """
            INSERT INTO t_htl_imgs (img_id, htl_id, img_url, cr_dtm, upd_dtm)
            VALUES (gen_random_uuid(),:hotelId,:imgUrl,:created,:updated);
            """;
    public static final String FIND_IMAGES_BY_HOTEL_ID = """
            SELECT img_url
            FROM t_htl_imgs WHERE htl_id = :hotelId;
            """;
    public static final String FIND_HOTEL_BY_ID = """
            SELECT htl_id, htl_nm, addrs, htl_dsc, htl_typ, fclts, rtng
            FROM t_htl WHERE htl_id = :hotelId;
            """;
    public static final String FIND_ADDRESS_BY_ID = """
            SELECT addrs_Id, htl_id, cty, pin, ste, str_no, lnd_mrk
            FROM t_addrs WHERE htl_id = :hotelId;
            """;
    public static final String FIND_ALL_HOTELS = """
            SELECT htl_id, htl_nm, addrs, htl_dsc, htl_typ, fclts, rtng
            FROM t_htl;
            """;
    public static final String UPDATE_HOTEL = """
            UPDATE t_htl
            SET htl_nm=:hotelName, htl_dsc=:description, htl_typ=:type, fclts=:facilities, rtng=:rating, upd_dtm=:updated
            WHERE htl_id=:hotelId;
            """;
    public static final String UPDATE_ADDRESS = """
            UPDATE t_addrs
            SET cty=:city, pin=:pincode, ste=:state, str_no=:streetNo, lnd_mrk=:landmark, upd_dtm=:updated
            WHERE htl_id=:hotelId;
            """;
    public static final String DELETE_HOTEL = """
            DELETE FROM t_addrs
            WHERE htl_id=:hotelId;
            """;
    public static final String DELETE_ADDRESS = """
            DELETE FROM t_htl
            WHERE htl_id=:hotelId;
            """;
    public static final String DELETE_IMAGES = """
            DELETE FROM t_htl_imgs
            WHERE htl_id=:hotelId;
            """;
}
