package com.learn.java.controller;

import com.learn.java.dto.BookingCreateRequestDto;
import com.learn.java.dto.BookingDetailedDataDto;
import com.learn.java.dto.BookingUpdateRequestDto;
import com.learn.java.model.Booking;
import com.learn.java.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@Tag(name = "Booking Controller")
public class BookingController {
	private final BookingService bookingService;
	
	@GetMapping
	@Operation(summary = "Get all booking")
	public List<Booking> getAll() {
		return bookingService.getAllBooking();
	}
	
	
	@GetMapping("/{id}")
	@Operation(summary = "Get booking by ID")
	public Booking getBooking(@PathVariable String id) {
		return bookingService.getById(id);
	}
	
	@GetMapping("/detail/{id}")
	@Operation(summary = "Get booking by ID with detailed info")
	public BookingDetailedDataDto getDetailBooking(@PathVariable String id) {
		return bookingService.getDetailedDataBooking(id);
	}
	
	@PostMapping
	@Operation(summary = "Create booking")
	public Booking createBooking(@RequestBody BookingCreateRequestDto bookingCreateRequestDto) {
		return bookingService.create(bookingCreateRequestDto);
	}
	
	@PatchMapping("/{id}")
	@Operation(summary = "Update booking")
	public Booking updateBooking(@PathVariable String id,
								 @RequestBody BookingUpdateRequestDto bookingUpdateRequestDto) {
		return bookingService.update(id, bookingUpdateRequestDto);
	}
	
	@PatchMapping("/cancel/{id}")
	@Operation(summary = "Cancel booking")
	public String cancelBooking(@PathVariable String id) {
		return bookingService.cancel(id);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete booking")
	public String deleteBooking(@PathVariable String id) {
		return bookingService.delete(id);
	}
}
