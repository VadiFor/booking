package com.learn.java.repository;

import com.learn.java.model.Booking;
import com.learn.java.model.enums.StatusBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
	List<Booking> findAllByResourceIdAndStatus(String resourceId, StatusBooking status);
}
