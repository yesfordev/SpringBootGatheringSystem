package com.wefunding.wdh.gs.login.controller;

import com.wefunding.wdh.gs.config.security.TokenProvider;
import com.wefunding.wdh.gs.login.entity.User;
import com.wefunding.wdh.gs.login.exception.CEmailLoginFailedException;
import com.wefunding.wdh.gs.login.model.CommonResult;
import com.wefunding.wdh.gs.login.model.LoginReq;
import com.wefunding.wdh.gs.login.model.RegisterReq;
import com.wefunding.wdh.gs.login.model.SingleResult;
import com.wefunding.wdh.gs.login.repository.UserRepository;
import com.wefunding.wdh.gs.login.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RequiredArgsConstructor //초기화 되지 않은 객체에 대한 생성자를 생성해주는 역할, @Autowired 없이도 의존성(Dependency Injection) 주입 가능
@RestController
@RequestMapping(value = "/login")
@Slf4j
@CrossOrigin
public class LoginController {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "담당자로그인", notes = "아이디(이메일), 패스워드로 로그인")
    @PostMapping(value = "/login")
//    public SingleResult<String> login(@ApiParam(value = "ID(이메일)", required = true) @RequestParam String id,
//                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
    public SingleResult<String> login(@RequestBody LoginReq loginReq) {

        log.info("[API CALL] - 로그인");

        User user = userRepository.findByUserEmail(loginReq.getId()).orElseThrow(CEmailLoginFailedException::new);
        if (!passwordEncoder.matches(loginReq.getPassword(), user.getPassword()))
            throw new CEmailLoginFailedException();

        return responseService.getSingleResult(tokenProvider.createToken(String.valueOf(user.getUserId()), user.getRoles()));
    }

    @ApiOperation(value = "담당자가입 및 등록", notes = "GS 회원가입")
    @PostMapping(value = "/register")
//    public CommonResult register(@ApiParam(value = "ID(이메일)", required = true) @RequestParam String userEmail,
//                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
//                               @ApiParam(value = "담당자이름", required = true) @RequestParam String name) {
    public CommonResult register(@RequestBody RegisterReq registerReq) {

        log.info("[API CALL] - 가입");

        userRepository.save(User.builder()
                .userEmail(registerReq.getUserEmail())
                .password(passwordEncoder.encode(registerReq.getPassword()))
                .name(registerReq.getName())
                .roles(Collections.singletonList("ROLE_USER")) //향후 롤을 어떻게 관리할지 고민 필요
                .build());
        return responseService.getSuccessResult();
    }

}