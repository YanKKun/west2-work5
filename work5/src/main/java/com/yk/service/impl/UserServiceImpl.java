package com.yk.service.impl;

import com.yk.mapper.UserMapper;
import com.yk.pojo.Result;
import com.yk.pojo.vo.UserVo;
import com.yk.service.UserService;
import com.yk.utils.SecurityContextHolderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 12080
 */
@Slf4j
@Component
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private SecurityContextHolderUtils securityContextHolderUtils;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Result register(String nickname, String username, String password) {
        log.info("数据传入");
        if(userMapper.findByUserName(username)!=null){
            return Result.error("已被注册");
        }
        userMapper.saveUser(nickname, username, passwordEncoder.encode(password));
        return Result.success();
    }

    @Override
    public Result<UserVo> findByUserName(String username) {
        if(userMapper.findByUserName(username)==null){
            return Result.error("找不到该用户");
        }
        return Result.success(userMapper.findByUserDtoName(username));
    }

    @Override
    public Result<UserVo> findByUserId(Integer id) {
        if(userMapper.findByUserId(id)==null){
            return Result.error("找不到该用户");
        }
        return Result.success(userMapper.findByUserDtoId(id));
    }

    @Override
    public Result updateUserInfo(String nickname){
        userMapper.updateUserInfo(nickname, securityContextHolderUtils.getUserId());
        return Result.success();
    }
}
