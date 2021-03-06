package com.example.myrestaurant.interfaces.restaurant;

import com.example.myrestaurant.domain.restaurant.domain.RestaurantCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RestaurantDtoMapper {

    List<RestaurantCommand.RegisterRestaurantCommand> of(List<RestaurantDto.RestaurantUpdateDto> restaurantUpdateDto);

}
