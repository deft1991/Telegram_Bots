package ru.golitsyn.telegram.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by Sergey Golitsyn (deft) on 07.10.2018
 */
@Getter
@Setter
@Configuration
@NoArgsConstructor
@ConfigurationProperties("app")
public class AppConfig {

  private ServerIp serverIp;
  private String name;

  @Getter
  @Setter
  public static class ServerIp {
	private String devFront;
	private String devGateway;
	private String devBack;

  }
}
