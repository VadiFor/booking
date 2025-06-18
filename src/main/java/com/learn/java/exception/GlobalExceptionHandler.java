package com.learn.java.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
