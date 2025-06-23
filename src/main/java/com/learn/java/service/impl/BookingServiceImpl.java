package com.learn.java.service.impl;

import com.learn.java.client.ResourceClient;
import com.learn.java.client.UserClient;
import com.learn.java.dto.*;
import com.learn.java.mapper.BookingMapper;
import com.learn.java.model.Booking;
import com.learn.java.model.enums.StatusBooking;
import com.learn.java.repository.BookingRepository;
import com.learn.java.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
	private final BookingRepository bookingRepository;
	private final BookingMapper bookingMapper;
	private final UserClient userClient;
	private final ResourceClient resourceClient;
	
	@Override
	public Booking create(BookingCreateRequestDto bookingCreateRequestDto) {
		getUser(bookingCreateRequestDto.getUserId());
		getResource(bookingCreateRequestDto.getResourceId());
		Booking newBooking = bookingMapper.fromCreateDtoToBooking(bookingCreateRequestDto);
		bookingRepository.save(newBooking);
		return newBooking;
	}
	
	@Override
	public Booking update(String id, BookingUpdateRequestDto bookingUpdateRequestDto) {
		Booking foundBooking = getById(id);
		if (bookingUpdateRequestDto.getUserId() != null) getUser(bookingUpdateRequestDto.getUserId());
		if (bookingUpdateRequestDto.getResourceId() != null) getResource(bookingUpdateRequestDto.getResourceId());
		bookingMapper.updateBookingFromDto(foundBooking, bookingUpdateRequestDto);
		Booking updatedBooking = bookingRepository.save(foundBooking);
		return updatedBooking;
	}
	
	@Override
	public String delete(String id) {
		Booking foundBooking = getById(id);
		bookingRepository.delete(foundBooking);
		return "Booking has been successfully deleted";
	}
	
	@Override
	public String cancel(String id) {
		Booking foundBooking = getById(id);
		foundBooking.setStatus(StatusBooking.CANCELLED);
		Booking updatedBooking = bookingRepository.save(foundBooking);
		return "Booking has been successfully cancelled";
	}
	
	@Override
	public Booking getById(String id) {
		return bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking with id «" + id + "» not found"));
	}
	
	@Override
	public BookingDetailedDataDto getDetailedDataBooking(String id) {
		Booking foundBooking = getById(id);
		BookingDetailedDataDto bookingDetailedDataDto = bookingMapper.toDetailedData(foundBooking);
		bookingDetailedDataDto.setUser(getUser(foundBooking.getUserId()));
		bookingDetailedDataDto.setResource(getResource(foundBooking.getResourceId()));
		return bookingDetailedDataDto;
	}
	
	@Override
	public List<Booking> getAllBooking() {
		return bookingRepository.findAll();
	}
	
	private UserDto getUser(String userId) {
		UserDto userDto = userClient.getUserDtoById(userId);
		return userDto;
	}
	
	private ResourceDto getResource(String resourceId) {
		ResourceDto resourceDto = resourceClient.getResourceDtoById(resourceId);
		return resourceDto;
	}
}
