package com.learn.java.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class BookingDetailedDataDto {
	private String id;
	private UserDto user;
	private ResourceDto resource;
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm")
	private LocalDateTime startTime;
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm")
	private LocalDateTime endTime;
	private StatusBooking status;
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
	private LocalDateTime createDate;
}
