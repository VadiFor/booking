package com.learn.java.controller;

import com.learn.java.dto.BookingCreateRequestDto;
import com.learn.java.dto.BookingDetailedDataDto;
import com.learn.java.dto.BookingUpdateRequestDto;
import com.learn.java.model.Booking;
import com.learn.java.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {
	private final BookingService bookingService;
	
	@GetMapping
	public List<Booking> getAll() {
		return bookingService.getAllBooking();
	}
	
	@GetMapping("/{id}")
	public Booking getBooking(@PathVariable String id) {
		return bookingService.getById(id);
	}
	
	@GetMapping("/detail/{id}")
	public BookingDetailedDataDto getDetailBooking(@PathVariable String id) {
		return bookingService.getDetailedDataBooking(id);
	}
	
	@PostMapping
	public Booking createBooking(@RequestBody BookingCreateRequestDto bookingCreateRequestDto) {
		return bookingService.create(bookingCreateRequestDto);
	}
	
	@PatchMapping("/{id}")
	public Booking updateBooking(@PathVariable String id,
								 @RequestBody BookingUpdateRequestDto bookingUpdateRequestDto) {
		return bookingService.update(id, bookingUpdateRequestDto);
	}
	
	@PatchMapping("/cancel/{id}")
	public String cancelBooking(@PathVariable String id) {
		return bookingService.cancel(id);
	}
	
	@DeleteMapping("/{id}")
	public String deleteBooking(@PathVariable String id) {
		return bookingService.delete(id);
	}
}
