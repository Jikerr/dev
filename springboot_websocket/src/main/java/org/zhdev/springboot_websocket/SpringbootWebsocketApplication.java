package org.zhdev.springboot_websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.zhdev")
public class SpringbootWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebsocketApplication.class, args);
	}
}
