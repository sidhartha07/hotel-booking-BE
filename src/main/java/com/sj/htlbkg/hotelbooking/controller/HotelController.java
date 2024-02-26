package com.sj.htlbkg.hotelbooking.controller;

import com.sj.htlbkg.hotelbooking.dto.HotelDto;
import com.sj.htlbkg.hotelbooking.dto.HotelRequest;
import com.sj.htlbkg.hotelbooking.dto.MessageDto;
import com.sj.htlbkg.hotelbooking.dto.ResponseMsgDto;
import com.sj.htlbkg.hotelbooking.exception.InvalidRequestException;
import com.sj.htlbkg.hotelbooking.service.HotelService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
public class HotelController {
    private final Logger logger = LoggerFactory.getLogger(HotelController.class);
    @Autowired
    private HotelService hotelService;

    @PostMapping("/create")
    public ResponseEntity<Object> createHotel(@RequestBody HotelRequest request) {
        if (validateRequest(request)) {
            MessageDto messageDto = MessageDto.builder()
                    .code("100003")
                    .message("Invalid request. Please provide mandatory fields.")
                    .build();
            ResponseMsgDto responseMsgDto = ResponseMsgDto.builder()
                    .exception("Hotel creation Failed with error")
                    .messages(List.of(messageDto))
                    .build();
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, responseMsgDto);
        }
        hotelService.saveHotel(request);
        MessageDto response = MessageDto.builder()
                .code(HttpStatus.CREATED.toString())
                .message("Hotel creation success")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Object> findHotelById(@PathVariable String hotelId) {
        HotelDto response = null;
        try {
            response = hotelService.findHotelById(hotelId);
        } catch (RuntimeException e) {
            MessageDto messageDto = MessageDto.builder()
                    .code("100004")
                    .message("Invalid hotelId")
                    .build();
            ResponseMsgDto responseMsgDto = ResponseMsgDto.builder()
                    .exception("Hotel fetch Failed with error")
                    .messages(List.of(messageDto))
                    .build();
            throw new InvalidRequestException(HttpStatus.NOT_FOUND, responseMsgDto);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllHotels() {
        List<HotelDto> response = null;
        try {
            response = hotelService.findAllHotels();
        } catch (RuntimeException e) {
            MessageDto messageDto = MessageDto.builder()
                    .code("100005")
                    .message("No Hotels found")
                    .build();
            ResponseMsgDto responseMsgDto = ResponseMsgDto.builder()
                    .exception("Hotel fetch Failed with error")
                    .messages(List.of(messageDto))
                    .build();
            throw new InvalidRequestException(HttpStatus.NOT_FOUND, responseMsgDto);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{hotelId}")
    public ResponseEntity<Object> updateHotel(@RequestBody HotelRequest request, @PathVariable String hotelId) {
        if (validateRequest(request)) {
            MessageDto messageDto = MessageDto.builder()
                    .code("100006")
                    .message("Invalid request. Please provide mandatory fields.")
                    .build();
            ResponseMsgDto responseMsgDto = ResponseMsgDto.builder()
                    .exception("Hotel update Failed with error")
                    .messages(List.of(messageDto))
                    .build();
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, responseMsgDto);
        }
        hotelService.updateHotel(request, hotelId);
        MessageDto response = MessageDto.builder()
                .code(HttpStatus.OK.toString())
                .message("Hotel updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Object> deleteHotel(@PathVariable String hotelId) {
        try {
            hotelService.deleteHotel(hotelId);
        } catch (RuntimeException ex) {
            MessageDto messageDto = MessageDto.builder()
                    .code("100007")
                    .message("Invalid hotelId")
                    .build();
            ResponseMsgDto responseMsgDto = ResponseMsgDto.builder()
                    .exception("Hotel delete Failed with error")
                    .messages(List.of(messageDto))
                    .build();
            throw new InvalidRequestException(HttpStatus.NOT_FOUND, responseMsgDto);
        }
        MessageDto response = MessageDto.builder()
                .code(HttpStatus.OK.toString())
                .message("Hotel deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    private boolean validateRequest(HotelRequest request) {
        return ObjectUtils.isEmpty(request.getAddress()) ||
                ObjectUtils.anyNull(request.getHotelName(),
                        request.getAddress(),
                        request.getAddress().getCity(),
                        request.getAddress().getPincode()) ||
                CollectionUtils.isEmpty(request.getFacilities()) ||
                !StringUtils.hasText(request.getHotelName()) ||
                !StringUtils.hasText(request.getAddress().getCity()) ||
                !StringUtils.hasText(request.getAddress().getPincode()) ||
                !StringUtils.hasText(request.getAddress().getState());
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    @ResponseBody
    public ResponseEntity<Object> handleInvalidRequest(InvalidRequestException ex) {
        ResponseMsgDto responseMsgDto = ex.getResponseMsgDto();
        String code = ex.getResponseMsgDto().getMessages().get(0).getCode();
        switch (code) {
            case "100003" -> logger.warn("Hotel creation failed: - Error: {}", ex.getError());
            case "100004" -> logger.warn("Hotel fetch failed: - Error: {}", ex.getError());
            case "100005" -> logger.warn("Hotel fetch all failed: - Error: {}", ex.getError());
            case "100006" -> logger.warn("Hotel update failed: - Error: {}", ex.getError());
            case "100007" -> logger.warn("Hotel delete failed: - Error: {}", ex.getError());
        }
        return ResponseEntity.status(ex.getStatus().value())
                .header("produces", MediaType.APPLICATION_JSON_VALUE)
                .body(responseMsgDto);
    }
}
