package com.wefunding.wdh.gs.login.service;


import com.wefunding.wdh.gs.login.exception.CUserNotFoundException;
import com.wefunding.wdh.gs.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userJpaRepo;

    public UserDetails loadUserByUsername(String userId) {
        return userJpaRepo.findById(Long.valueOf(userId)).orElseThrow(CUserNotFoundException::new);
    }
}