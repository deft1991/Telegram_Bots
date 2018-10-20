package ru.golitsyn.telegram.schedulers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.golitsyn.telegram.config.AppConfig;
import ru.golitsyn.telegram.ping.PingHelper;
import ru.golitsyn.telegram.services.UserService;
import ru.golitsyn.telegram.telegram.TelegramBotNotificator;

import java.io.IOException;
import java.util.List;

/**
 * Нотификатор для девовских стендов
 * <p>
 * Created by Sergey Golitsyn (deft) on 07.10.2018
 */
@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DevNotificator {

  private final AppConfig appConfig;
  private final UserService userService;
  private final TelegramBotNotificator telegramBotNotificator;
//  @Autowired
//  PingHelper pingHelper;


  //  @Scheduled(cron = "0 0/30 * * * *")
  @Scheduled(cron = "0 * * * * ?")
  public void gatewayNotificator() {
	try {
	  boolean isReach = PingHelper.sendPingRequest(appConfig.getServerIp().getDevGateway());
	  PingHelper.sendPingRequest("172.17.111.145");
	  List<Long> allChatIds = userService.getAllChatIds();
	  if (!isReach) {
		allChatIds.forEach(chaId -> {
		  telegramBotNotificator.sendMsg(chaId.toString(), "Беда беда --> упал девовский ГЭТВЭЙ");
		});
	  }
	} catch (IOException e) {
	  log.error(e.getLocalizedMessage());
	}
  }
}
