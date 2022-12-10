package com.mqtt.mosquitto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mqtt.mosquitto.entity.user;
import com.mqtt.mosquitto.mapper.UserMapper;
import com.mqtt.mosquitto.service.UserService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    JavaMailSenderImpl mailSender;
    public user subscribe(String email){
        user user1=new user(email,1);
        try{
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("email",email);
            if(userMapper.selectList(queryWrapper)!=null){
                UpdateWrapper updateWrapper =new UpdateWrapper();
                updateWrapper.set("subscribe","1");
                updateWrapper.eq("email",email);
                if (userMapper.update(null,updateWrapper)==0){
                    throw new RuntimeException("订阅失败!");
                }
            }
            else{
                userMapper.insert(user1);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return user1;
    }
    public String td(String email){
        try{
            user user1=new user();
            user1.setEmail(email);
            user1.setSubscribe(0);
            UpdateWrapper updateWrapper =new UpdateWrapper();
            updateWrapper.set("subscribe","0");
            updateWrapper.eq("email",email);
            if (userMapper.update(null,updateWrapper)==0){
                throw new RuntimeException("退订失败!");
            }
        }catch (Exception e){
            return e.getMessage();
        }
        return "退订成功";
    }
    public void sendEmail(String message){
        //System.out.println("222");
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("subscribe","1");
        List<user>users=new ArrayList<>();
        users=userMapper.selectList(queryWrapper);
        System.out.println("用户列表"+users);
        users.forEach((user)->{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper;
            try {
                helper = new MimeMessageHelper(mimeMessage, true);

                helper.setSubject("疫情信息速递");

                helper.setText("尊敬的用户,您好！\n" + "这是您订阅的信息:\n" + message+"\n退订请点击:http://localhost:8081/user/td?email="+user.getEmail());
                helper.setTo(user.getEmail());
                helper.setFrom("417695971@qq.com");
            } catch (MessagingException err) {
                System.out.println(err.getMessage());
                throw new RuntimeException(err);
            }
            mailSender.send(mimeMessage);
        });
        return;
    }
}
