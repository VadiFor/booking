package com.learn.java.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, String>> entityNotFound(EntityNotFoundException ex) {
		log.info(ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(Map.of("error", ex.getMessage()));
	}
	
	@ExceptionHandler(IncorrectDataException.class)
	public ResponseEntity<Map<String, List<String>>> incorrectData(IncorrectDataException ex) {
		List<String> incorrectData = Arrays.stream(ex.getMessage().split("\n")).toList();
		log.info(incorrectData.toString());
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(Map.of("incorrect data", incorrectData));
	}
	
	@ExceptionHandler(BookingOverlapException.class)
	public ResponseEntity<Map<String, List<String>>> overlapBooking(BookingOverlapException ex) {
		List<String> overlapData = Arrays.stream(ex.getMessage().split("\n")).toList();
		log.info(ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(Map.of("error", overlapData));
	}
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<Map<String, String>> handleFeignStatusException(FeignException ex) {
		log.warn("Ошибка Feign клиента: {}", ex.getMessage());
		
		HttpStatus status = HttpStatus.resolve(ex.status());
		if (status == null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		Map<String, String> mapMessage = Map.of();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapMessage = mapper.readValue(ex.contentUTF8(), Map.class); // ex.contentUTF8() - тело ответа от внешнего сервиса
		} catch (Exception e) {
			mapMessage.put("Ошибка при обращении к внешнему сервису", ex.getMessage());
		}
		
		return ResponseEntity
				.status(status)
				.body(mapMessage);
	}
}
