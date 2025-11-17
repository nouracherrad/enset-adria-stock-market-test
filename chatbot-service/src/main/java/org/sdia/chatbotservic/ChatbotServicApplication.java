package org.sdia.chatbotservic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ChatbotServicApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotServicApplication.class, args);
	}

}
