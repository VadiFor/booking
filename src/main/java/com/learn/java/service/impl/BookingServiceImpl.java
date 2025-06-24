package com.learn.java.service.impl;

import com.learn.java.client.ResourceClient;
import com.learn.java.client.UserClient;
import com.learn.java.dto.*;
import com.learn.java.exception.BookingOverlapException;
import com.learn.java.exception.IncorrectDataException;
import com.learn.java.kafka.ProducerKafka;
import com.learn.java.mapper.BookingMapper;
import com.learn.java.model.Booking;
import com.learn.java.model.enums.StatusBooking;
import com.learn.java.repository.BookingRepository;
import com.learn.java.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
	private final BookingRepository bookingRepository;
	private final BookingMapper bookingMapper;
	private final UserClient userClient;
	private final ResourceClient resourceClient;
	private final ProducerKafka producerKafka;
	
	@Override
	public Booking create(BookingCreateRequestDto createDto) {
		checkData(createDto.getUserId(),
				createDto.getResourceId(),
				createDto.getStartTime(),
				createDto.getEndTime());
		Booking newBooking = bookingMapper.fromCreateDtoToBooking(createDto);
		checkFreeBookingResource(newBooking.getResourceId(), newBooking.getStartTime(), newBooking.getEndTime());
		bookingRepository.save(newBooking);
		if (getUser(newBooking.getUserId()).getPosition().equals("TECHNICIAN"))
			producerKafka.sendMessage("booking.service.resource.status", newBooking.getResourceId() + "-MAINTENANCE");
		return newBooking;
	}
	
	@Override
	public Booking update(String id, BookingUpdateRequestDto updateDto) {
		Booking foundBooking = getById(id);
		checkData(updateDto.getUserId(),
				updateDto.getResourceId(),
				updateDto.getStartTime() != null ? updateDto.getStartTime() : foundBooking.getStartTime(),
				updateDto.getEndTime() != null ? updateDto.getEndTime() : foundBooking.getEndTime()
		);
		bookingMapper.updateBookingFromDto(foundBooking, updateDto);
		if (updateDto.getResourceId() != null || updateDto.getStartTime() != null || updateDto.getEndTime() != null)
			checkFreeBookingResource(foundBooking.getResourceId(), foundBooking.getStartTime(), foundBooking.getEndTime());
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
	
	private void checkData(String userId, String resourceId, LocalDateTime startTime, LocalDateTime endTime) {
		StringBuilder exMessage = new StringBuilder();
		if (userId != null) getUser(userId);
		if (resourceId != null) checkStatusResource(getResource(resourceId));
		if (startTime.isBefore(LocalDateTime.now()))
			exMessage.append("StartTime must be later than the current time\n");
		if (!startTime.isBefore(endTime)) exMessage.append("EndTime must be later than startTime\n");
		if (exMessage.length() > 0)
			throw new IncorrectDataException(exMessage.toString());
	}
	
	private void checkStatusResource(ResourceDto resourceDto) {
		if (!resourceDto.getStatus().equals("ACTIVE"))
			throw new IncorrectDataException("Resource is not in the ACTIVE status");
	}
	
	private void checkFreeBookingResource(String resourceId, LocalDateTime startTime, LocalDateTime endTime) {
		List<Booking> bookingList = bookingRepository.findAllByResourceIdAndStatus(resourceId, StatusBooking.CREATED);
		if (!bookingList.isEmpty()) {
			List<Booking> overlapBook = bookingList.stream()
					.filter(booking -> booking.getStartTime().isBefore(endTime) && booking.getEndTime().isAfter(startTime))
					.toList();
			if (!overlapBook.isEmpty()) {
				StringBuilder exMessage = new StringBuilder("Resource has already been booked:\n");
				overlapBook
						.forEach(booking -> exMessage
								.append("StartTime: ").append(booking.getStartTime())
								.append(", EndTime: ").append(booking.getEndTime())
								.append("\n")
						);
				throw new BookingOverlapException(exMessage.toString());
			}
		}
	}
}
