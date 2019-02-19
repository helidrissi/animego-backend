package com.udemy.angular.repositories;

import com.udemy.angular.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUser extends JpaRepository<User, Long> {

    User findByMailAndPassword(String mail, String password);
}
