package ru.golitsyn.telegram.ping;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Класс для отправки пинга на сервера
 * <p>
 * Created by Sergey Golitsyn (deft) on 07.10.2018
 */
@Slf4j
//@Data
//@Component
//@NoArgsConstructor
public class PingHelper {

  public static boolean sendPingRequest(String ipAddress)
		  throws IOException {
	InetAddress geek = InetAddress.getByName(ipAddress);
	System.out.println("Sending Ping Request to " + ipAddress);
	if (geek.isReachable(5000)) {
	  log.debug("Host is reachable: " + ipAddress);
	  return true;
	} else {
	  log.debug("Sorry ! We can't reach to this host: " + ipAddress);
	  return false;
	}
  }
}
