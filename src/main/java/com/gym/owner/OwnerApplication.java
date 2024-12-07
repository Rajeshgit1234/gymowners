package com.gym.owner;

import com.gym.owner.common.RedisJava;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class OwnerApplication {


	public static void main(String[] args) {
		try {

			RedisJava redis = new RedisJava();
			redis.ConnectRedis();
			SpringApplication.run(OwnerApplication.class, args);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}



}
