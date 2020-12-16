package com.wefunding.wdh.gs.login.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by yes on 2020/12/16
 */

public class PasswordEncoding implements PasswordEncoder {
    private PasswordEncoder passwordEncoder;

    public PasswordEncoding() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public PasswordEncoding(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
