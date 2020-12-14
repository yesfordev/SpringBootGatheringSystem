package com.wefunding.wdh.gs.login.controller;

import com.wefunding.wdh.gs.login.entity.User;
import com.wefunding.wdh.gs.login.exception.CUserNotFoundException;
import com.wefunding.wdh.gs.login.model.CommonResult;
import com.wefunding.wdh.gs.login.model.SingleResult;
import com.wefunding.wdh.gs.login.repository.UserRepository;
import com.wefunding.wdh.gs.login.service.ResponseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor //초기화 되지 않은 객체에 대한 생성자를 생성해주는 역할, @Autowired 없이도 의존성(Dependency Injection) 주입 가능
@RestController
@RequestMapping(value = "/user")
@Slf4j
@CrossOrigin
public class UserController {

    private final UserRepository userRepository;
    private final ResponseService responseService;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "유효 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "본인정보조회", notes = "유효한 token을 통하여 본인정보를 조회한다.")
    @GetMapping(value = "/user")
    public SingleResult<User> findUser() {

        log.info("[API CALL] - 본인정보조회");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        return responseService.getSingleResult(userRepository.findByUserEmail(id).orElseThrow(CUserNotFoundException::new));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "유효 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "담당자삭제", notes = "user_id로 정보 삭제")
    @DeleteMapping(value = "/user/{userId}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable long userId) {

        log.info("[API CALL] - ");

        userRepository.deleteById(userId);

        return responseService.getSuccessResult();
    }

}
