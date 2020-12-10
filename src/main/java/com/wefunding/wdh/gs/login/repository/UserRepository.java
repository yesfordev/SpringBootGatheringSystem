package com.wefunding.wdh.gs.login.repository;

import com.wefunding.wdh.gs.login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByUserEmail(String email);

}
