package com.learn.java.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreateRequestDto {
	@NotNull
	private String userId;
	
	@NotNull
	private String resourceId;
	
	@NotNull
	private LocalDateTime startTime;
	
	@NotNull
	private LocalDateTime endTime;
}
