package com.learn.java.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learn.java.model.enums.StatusBooking;
import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description = "ID user")
	private String userId;
	@Schema(description = "ID resource")
	private String resourceId;
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm")
	@Schema(description = "Time start booking", example = "20.06.2025 11:00")
	private LocalDateTime startTime;
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm")
	@Schema(description = "Time end booking", example = "20.06.2025 11:30")
	private LocalDateTime endTime;
	@Schema(description = "Status booking", example = "COMPLETED")
	private StatusBooking status;
}