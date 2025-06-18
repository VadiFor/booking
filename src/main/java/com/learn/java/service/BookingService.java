package com.learn.java.service;

import com.learn.java.dto.BookingCreateRequestDto;
import com.learn.java.dto.BookingUpdateRequestDto;
import com.learn.java.model.Booking;

import java.util.List;

public interface BookingService {
	Booking create(BookingCreateRequestDto bookingCreateRequestDto);
	
	Booking update(String id, BookingUpdateRequestDto bookingUpdateRequestDto);
	
	String delete(String id);
	
	Booking getById(String id);
	
	List<Booking> getAllBooking();
}
