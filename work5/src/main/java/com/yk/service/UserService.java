package com.yk.service;

import com.yk.pojo.Result;
import com.yk.pojo.vo.UserVo;
import org.springframework.stereotype.Service;

/**
 * @author 12080
 */
@Service
public interface UserService {
    /**
     * 新建用户
     * @param nickname 用户昵称
     * @param username 用户名
     * @param password 密码
     * @return  结果集
     */
    Result register(String nickname , String username , String password);

    /**
     * 通过用户名返回对应用户
     * @param username 用户名
     * @return 用户
     */
    Result<UserVo> findByUserName(String username);

    /**
     * 通过用户id返回指定用户
     * @param id 用户id
     * @return 用户
     */
    Result<UserVo> findByUserId(Integer id);

    /**
     * 更新用户信息
     * @param nickname 昵称
     * @return 结果集
     */
    Result updateUserInfo(String nickname);

}
