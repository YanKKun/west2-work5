package com.yk.controller;

import com.yk.pojo.Result;
import com.yk.pojo.User;
import com.yk.service.LoginService;
import com.yk.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 用户模块接口
 * @author 12080
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private LoginService loginService;

    /**
     * 登录
     * @param user 用户
     * @param session null/传递使用
     * @return 结果集
     */
    @PostMapping("/login")
     public Result login(@RequestBody @NotNull User user, HttpSession session){
        return loginService.login(user.getUsername(),user.getPassword(),session);
    }

    /**
     * 退出
     * @return 结果
     */
    @PostMapping("/logout")
    public Result logout(){
        return loginService.logout();
    }

    /**
     * 注册
     * @param user 用户对象
     * @return 结果集
     */
    @PostMapping("/register")
    public Result register(@Valid @RequestBody User user){
        return userService.register(user.getNickname(),user.getUsername(), user.getPassword());
    }

    /**
     * 更新用户昵称
     * @param user 用户对象
     * @return 结果集
     */
    @PostMapping("/update")
    public Result updateUserInfo(@Valid @RequestBody User user){
        return userService.updateUserInfo(user.getNickname());
    }

    /**
     * 通过id查找用户
     * @param user 用户对象
     * @return 结果集
     */
    @GetMapping("findUserById")
    public Result findByUserId(@Valid @RequestBody User user){
        return userService.findByUserId(user.getId());
    }


}
