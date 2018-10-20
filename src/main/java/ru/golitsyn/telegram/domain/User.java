package ru.golitsyn.telegram.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Класс пользователя для хранения айди чата,
 * что бы по айди чата рассылать уведомления
 * Created by Sergey Golitsyn (deft) on 07.10.2018
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "users", schema = "telegram")
public class User {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "chat_id")
  private Long chatId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "is_bot")
  private Boolean isBot;

  public User(Long chatId, String firstName
		  , String lastName, boolean isBot) {
	this.chatId = chatId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.isBot = isBot;
  }
}
