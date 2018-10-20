package ru.golitsyn.telegram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.golitsyn.telegram.domain.User;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Sergey Golitsyn (deft) on 07.10.2018
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findAllByChatId(long chatId);
}
