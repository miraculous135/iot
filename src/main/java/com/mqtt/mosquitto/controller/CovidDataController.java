package com.mqtt.mosquitto.controller;

import com.mqtt.mosquitto.common.Result;
import com.mqtt.mosquitto.publish.PublishSample;
import com.mqtt.mosquitto.subscribe.SubscribeSample;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("covid")
public class CovidDataController {
    @GetMapping("")
    public Result<String> test(){
        try {
            SubscribeSample subscribeSample=new SubscribeSample();
            subscribeSample.sub();
        }catch (Exception e){
            System.out.println(e);
        }

        PublishSample publishSample=new PublishSample();
        publishSample.pub();
        return Result.success("成功");
    }
}
