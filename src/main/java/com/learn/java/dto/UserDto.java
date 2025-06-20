package com.learn.java.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private String id;
	private String fullName;
	private String email;
	private String phone;
	private String position;
	private Boolean active;
}