package com.mqtt.mosquitto;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootMosquittoApplication {

	public static void main(String[] args) throws MqttException {
		SpringApplication.run(SpringbootMosquittoApplication.class, args);
	}

}
