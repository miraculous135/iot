package com.mqtt.mosquitto.service;

import com.mqtt.mosquitto.entity.user;
import com.mqtt.mosquitto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;

@Service
public interface UserService {
     user subscribe(String email);
     String td(String email);
     void sendEmail(String message);

}
