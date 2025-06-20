package com.learn.java.model;

import com.learn.java.model.enums.StatusBooking;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "booking")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
	@Id
	@Builder.Default
	private String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private String resourceId;
	
	@Column(nullable = false)
	private LocalDateTime startTime;
	
	@Column(nullable = false)
	private LocalDateTime endTime;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private StatusBooking status = StatusBooking.CREATED;
	
	@Column(nullable = false)
	@Builder.Default
	private LocalDateTime createDate = LocalDateTime.now();
}
