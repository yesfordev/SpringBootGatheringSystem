package com.wefunding.wdh.gs.login.model;

import lombok.Data;

/**
 * Created by yes on 2020/12/14
 */
@Data
public class RegisterReq {
    private String userEmail;
    private String password;
    private String name;
}
