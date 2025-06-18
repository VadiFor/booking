package com.learn.java.dto;

import com.learn.java.model.enums.StatusBooking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BookingUpdateRequestDto {
	private String userId;
	private String resourceId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private StatusBooking status;
}