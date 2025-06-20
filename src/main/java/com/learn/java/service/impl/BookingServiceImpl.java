package com.learn.java.service.impl;

import com.learn.java.client.ResourceClient;
import com.learn.java.client.UserClient;
import com.learn.java.dto.BookingCreateRequestDto;
import com.learn.java.dto.BookingUpdateRequestDto;
import com.learn.java.dto.ResourceDto;
import com.learn.java.dto.UserDto;
import com.learn.java.model.Booking;
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
	private final UserClient userClient;
	private final ResourceClient resourceClient;
	
	@Override
	public Booking create(BookingCreateRequestDto bookingCreateRequestDto) {
		UserDto user = userClient.getUserDtoById(bookingCreateRequestDto.getUserId());
		ResourceDto resource = resourceClient.getResourceDtoById(bookingCreateRequestDto.getResourceId());
		Booking newBooking = Booking.builder()
				.userId(user.getId())
				.resourceId(resource.getId())
				.startTime(bookingCreateRequestDto.getStartTime())
				.endTime(bookingCreateRequestDto.getEndTime())
				.build();
		bookingRepository.save(newBooking);
		return newBooking;
	}
	
	@Override
	public Booking update(String id, BookingUpdateRequestDto bookingUpdateRequestDto) {
		Booking foundBooking = getById(id);
		if (bookingUpdateRequestDto.getUserId() != null) {
			UserDto user = userClient.getUserDtoById(bookingUpdateRequestDto.getUserId());
			foundBooking.setUserId(user.getId());
		}
		if (bookingUpdateRequestDto.getResourceId() != null) {
			ResourceDto resource = resourceClient.getResourceDtoById(bookingUpdateRequestDto.getResourceId());
			foundBooking.setResourceId(resource.getId());
		}
		if (bookingUpdateRequestDto.getStartTime() != null)
			foundBooking.setStartTime(bookingUpdateRequestDto.getStartTime());
		if (bookingUpdateRequestDto.getEndTime() != null)
			foundBooking.setEndTime(bookingUpdateRequestDto.getEndTime());
		if (bookingUpdateRequestDto.getStatus() != null)
			foundBooking.setStatus(bookingUpdateRequestDto.getStatus());
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
	public Booking getById(String id) {
		return bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking with id «" + id + "» not found"));
	}
	
	@Override
	public List<Booking> getAllBooking() {
		return bookingRepository.findAll();
	}
}
