package ru.golitsyn.telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.golitsyn.telegram.domain.User;
import ru.golitsyn.telegram.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by Sergey Golitsyn (deft) on 07.10.2018
 */
@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  public List<Long> getAllChatIds(){
    return StreamSupport
			.stream(userRepository.findAll().spliterator(), true)
			.map(User::getChatId)
			.collect(Collectors.toList());
  }
}
