package com.learn.java.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDto {
	private String id;
	private String name;
	private String type;
	private String location;
	private String status;
	private Boolean portable;
	private Boolean requiresApproval;
	private Boolean isShared;
}
