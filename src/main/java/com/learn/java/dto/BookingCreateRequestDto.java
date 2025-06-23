package com.learn.java.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description = "ID user")
	private String userId;
	
	@NotNull
	@Schema(description = "ID resource")
	private String resourceId;
	
	@NotNull
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm")
	@Schema(description = "Time start booking", example = "20.06.2025 11:00")
	private LocalDateTime startTime;
	
	@NotNull
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm")
	@Schema(description = "Time end booking", example = "20.06.2025 11:30")
	private LocalDateTime endTime;
}
