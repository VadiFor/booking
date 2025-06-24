package com.learn.java.mapper;

import com.learn.java.dto.BookingCreateRequestDto;
import com.learn.java.dto.BookingDetailedDataDto;
import com.learn.java.dto.BookingUpdateRequestDto;
import com.learn.java.model.Booking;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookingMapper {
	Booking fromCreateDtoToBooking(BookingCreateRequestDto bookingCreateRequestDto);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateBookingFromDto(@MappingTarget Booking booking, BookingUpdateRequestDto bookingUpdateRequestDto);
	
	BookingDetailedDataDto toDetailedData(Booking booking);
	
	@Mapping(target = "userId", expression = "java(bookingDetailedDataDto.getUser().getId())")
	@Mapping(target = "resourceId", expression = "java(bookingDetailedDataDto.getResource().getId())")
	Booking fromDetailedToBooking(BookingDetailedDataDto bookingDetailedDataDto);
}
