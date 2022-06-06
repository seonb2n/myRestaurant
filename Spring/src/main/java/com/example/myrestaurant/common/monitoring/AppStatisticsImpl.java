package com.example.myrestaurant.common.monitoring;

import com.example.myrestaurant.infrastructures.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppStatisticsImpl implements AppStatistics{

    @Autowired
    private UserRepository userRepository;

    @Override
    public int getTotalUserCount() {
        return userRepository.findAll().size();
    }
}
