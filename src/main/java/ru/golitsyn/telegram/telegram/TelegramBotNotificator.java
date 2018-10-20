package ru.golitsyn.telegram.telegram;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.golitsyn.telegram.domain.User;
import ru.golitsyn.telegram.repository.UserRepository;

import java.util.List;

/**
 * Created by Sergey Golitsyn (deft) on 06.10.2018
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotNotificator extends TelegramLongPollingBot {

  private final UserRepository userRepository;

  static {
	ApiContextInitializer.init(); // Initialize Api Context
  }

  public void init() {
	TelegramBotsApi botsApi = new TelegramBotsApi();  // Instantiate Telegram Bots API
	// Register our bot
	try {
	  botsApi.registerBot(this);
	} catch (TelegramApiException e) {
	  e.printStackTrace();
	}
  }

  /**
   * Метод для приема сообщений.
   *
   * @param update Содержит сообщение от пользователя.
   */
  @Override
  public void onUpdateReceived(Update update) {
	//check if the update has a message
	if (update.hasMessage()) {
	  Message message = update.getMessage();
	  //todo при отправке контакта проверять дописывать данные в таблицу пользователя
	  saveNewChatIfNotPersist(message);
	  //check if the message has text. it could also  contain for example a location ( message.hasLocation() )
	  if (message.hasText()) {
		sendMsg(message);
	  }
	}
  }

  private void saveNewChatIfNotPersist(Message message) {
	Long chatId = message.getChatId();
	org.telegram.telegrambots.meta.api.objects.User telegramUser = message.getFrom();
	String firstName = "", lastName = "";
	boolean isBot = false;
	if (!ObjectUtils.isEmpty(telegramUser)) {
	  firstName = telegramUser.getFirstName();
	  lastName = telegramUser.getLastName();
	  isBot = telegramUser.getBot();
	}
	List<User> allByChatId = userRepository.findAllByChatId(chatId);
	if (CollectionUtils.isEmpty(allByChatId)){
	  userRepository.save(new User(chatId, firstName, lastName, isBot));
	}
  }

  private void sendMsg(Message message) {
	//create a object that contains the information to send back the message
	SendMessage sendMessageRequest = new SendMessage();
	sendMessageRequest.setChatId(message.getChatId().toString()); //who should get the message? the sender from which we got the message...
	sendMessageRequest.setText("you said: " + message.getText());
	try {
	  execute(sendMessageRequest); //at the end, so some magic and send the message ;)
	} catch (TelegramApiException e) {
	  //do some error handling
	}
  }

  /**
   * Метод для настройки сообщения и его отправки.
   *
   * @param chatId id чата
   * @param s Строка, которую необходимот отправить в качестве сообщения.
   */
  public synchronized void sendMsg(String chatId, String s) {
	SendMessage sendMessage = new SendMessage();  // Create a message object object
	sendMessage.enableMarkdown(true);
	sendMessage.setChatId(chatId);
	sendMessage.setText(s);
	try {
	  execute(sendMessage); // Sending our message object to user
	} catch (TelegramApiException e) {
//      do some error handling
	}
  }

  @Override
  public String getBotUsername() {
	return BotConfig.BOT_USERNAME;
  }

  @Override
  public String getBotToken() {
	return BotConfig.BOT_TOKEN;
  }

}
