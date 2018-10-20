package ru.golitsyn.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.golitsyn.telegram.telegram.TelegramBotNotificator;

/**
 * Created by Sergey Golitsyn (deft) on 06.10.2018
 */
@EnableScheduling
@EnableJpaRepositories
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
    TelegramBotNotificator telegramBotNotificator = applicationContext.getBean(TelegramBotNotificator.class);
    telegramBotNotificator.init();

  }
}
