package com.udemy.angular.repositories;

import com.udemy.angular.entities.AnimeCharacter;
import com.udemy.angular.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface IAnimeCharacter extends JpaRepository<AnimeCharacter, Long> {

    List<AnimeCharacter> findByUserOrShared(User user, boolean shared);

    List<AnimeCharacter> findByUser(User user);

    List<AnimeCharacter> findBySharedAndUserNotLike(boolean shared, User user);
}
