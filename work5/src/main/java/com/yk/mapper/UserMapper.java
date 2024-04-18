package com.yk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yk.pojo.User;
import com.yk.pojo.vo.UserVo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 新建用户
     * @param nickname 用户昵称
     * @param username 用户名
     * @param password 密码
     */
    @Insert("insert into user set nickname=#{nickname},username=#{username},password=#{password}")
    void saveUser(@Param("nickname") String nickname, @Param("username") String username, @Param("password") String password);

    /**
     * 通过用户名返回对应用户
     * @param username 用户名
     * @return 用户
     */
    @Select("select id,password,username,nickname from user where username = #{username}")
    User findByUserName(@Param("username") String username);
    /**
     * 通过用户名返回对应用户
     * @param username 用户名
     * @return 用户
     */
    @Select("select id,password,username,nickname from user where username = #{username}")
    UserVo findByUserDtoName(@Param("username") String username);
    /**
     * 通过用户id返回指定用户
     * @param id 用户id
     * @return 用户
     */
    @Select("select username,password,id,nickname from user where id = #{id}")
    User findByUserId(@Param("id") Integer id);

    /**
     * 通过用户id返回指定用户
     * @param id 用户id
     * @return 用户
     */
    @Select("select username,password,id,nickname from user where id = #{id}")
    UserVo findByUserDtoId(@Param("id") Integer id);
    /**
     * 修改用户基本信息
     * @param nickname 用户昵称
     * @param id 用户id
     */
    @Update("update user set nickname = #{nickname} where id = #{id}")
    void updateUserInfo(@Param("nickname") String nickname ,@Param("id") Integer id);

}
