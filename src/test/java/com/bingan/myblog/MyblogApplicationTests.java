package com.bingan.myblog;

import com.bingan.myblog.dao.UserRepository;
import com.bingan.myblog.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyblogApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void saveUser(){
        User user = new User();
        user.setPassword("111");
        user.setUsername("auser");
        userRepository.save(user);
    }

}
