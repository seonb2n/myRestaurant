package com.example.myrestaurant.interfaces.restaurant;

import com.example.myrestaurant.domain.restaurant.domain.RestaurantCommand.RegisterRestaurantCommand;
import com.example.myrestaurant.domain.restaurant.domain.RestaurantCommand.RegisterRestaurantCommand.RegisterRestaurantCommandBuilder;
import com.example.myrestaurant.interfaces.restaurant.RestaurantDto.RestaurantUpdateDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-22T10:52:11+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class RestaurantDtoMapperImpl implements RestaurantDtoMapper {

    @Override
    public List<RegisterRestaurantCommand> of(List<RestaurantUpdateDto> restaurantUpdateDto) {
        if ( restaurantUpdateDto == null ) {
            return null;
        }

        List<RegisterRestaurantCommand> list = new ArrayList<RegisterRestaurantCommand>( restaurantUpdateDto.size() );
        for ( RestaurantUpdateDto restaurantUpdateDto1 : restaurantUpdateDto ) {
            list.add( restaurantUpdateDtoToRegisterRestaurantCommand( restaurantUpdateDto1 ) );
        }

        return list;
    }

    protected RegisterRestaurantCommand restaurantUpdateDtoToRegisterRestaurantCommand(RestaurantUpdateDto restaurantUpdateDto) {
        if ( restaurantUpdateDto == null ) {
            return null;
        }

        RegisterRestaurantCommandBuilder registerRestaurantCommand = RegisterRestaurantCommand.builder();

        registerRestaurantCommand.name( restaurantUpdateDto.getName() );
        registerRestaurantCommand.location( restaurantUpdateDto.getLocation() );
        registerRestaurantCommand.link( restaurantUpdateDto.getLink() );
        registerRestaurantCommand.category( restaurantUpdateDto.getCategory() );

        return registerRestaurantCommand.build();
    }
}
