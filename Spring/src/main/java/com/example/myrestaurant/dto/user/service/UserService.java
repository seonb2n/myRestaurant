package com.example.myrestaurant.dto.user.service;

import com.example.myrestaurant.config.UserEnrollForm;
import com.example.myrestaurant.dto.restaurant.domain.Restaurant;
import com.example.myrestaurant.dto.restaurant.respository.RestaurantRepository;
import com.example.myrestaurant.dto.restaurant.service.RestaurantService;
import com.example.myrestaurant.dto.user.domain.User;
import com.example.myrestaurant.dto.user.domain.UserAuthority;
import com.example.myrestaurant.dto.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<User> findUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User save(User user) {
        restaurantService.saveAll(user.getRestaurantList());
        return userRepository.save(user);
    }

    public User enroll(UserEnrollForm userEnrollForm) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userEnrollForm.setPassword(encoder.encode(userEnrollForm.getPassword()));

        return userRepository.save(User.builder()
                .email(userEnrollForm.getEmail())
                .password(userEnrollForm.getPassword())
                .build()
        );
    }

    public void addAuthority(Long userId, String authority) {
        userRepository.findById(userId).ifPresent(user -> {
            UserAuthority newRole = new UserAuthority(user.getUserId(), authority);
            if(user.getAuthorities() == null) {
                HashSet<UserAuthority> authorities = new HashSet<>();
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            } else if(!user.getAuthorities().contains(newRole)) {
                HashSet<UserAuthority> authorities = new HashSet<>();
                authorities.addAll(user.getAuthorities());
                authorities.add(newRole);
                save(user);
            }
        });
    }

    public User addRestaurants(User user, Restaurant... restaurants) {
        user.addRestaurant(restaurants);
        return save(user);
    }

    public Set<Restaurant> getRestaurants(User user) {
        return user.getRestaurantList();
    }

    public Restaurant getRestaurant(User user, Long id) {
        if (restaurantService.findById(id).isPresent()) {
            if(user.getRestaurantList().contains(restaurantService.findById(id).get())) {
                return restaurantService.findById(id).get();
            }
        }

        return null;
    }

    public void deleteRestaurant(User user, Restaurant restaurant) {
        if(getRestaurants(user).contains(restaurant)) {
            Set<Restaurant> s1 = getRestaurants(user);
            s1.remove(restaurant);
            user.setRestaurantList(s1);
            save(user);
        } else {
            System.out.println("Error : restaurant does not exist");
        }
    }
}

