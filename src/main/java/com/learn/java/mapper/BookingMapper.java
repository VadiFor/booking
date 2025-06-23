package com.learn.java.mapper;

import com.learn.java.dto.BookingCreateRequestDto;
import com.learn.java.dto.BookingDetailedDataDto;
import com.learn.java.dto.BookingUpdateRequestDto;
import com.learn.java.model.Booking;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BookingMapper {
	Booking fromCreateDtoToBooking(BookingCreateRequestDto bookingCreateRequestDto);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateBookingFromDto(@MappingTarget Booking booking, BookingUpdateRequestDto bookingUpdateRequestDto);
	
	BookingDetailedDataDto toDetailedData(Booking booking);
}
