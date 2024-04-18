package com.yk.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@TableName(value = "user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull
    private Integer id;
    @Size(min = 4,max = 15,message = "用户名不得小于3个字符大于10个字符")
    private String username;
    @Size(min = 4,max = 15,message = "密码不得小于3个字符大于10个字符")
    private String password;
    @Size(min = 2,max = 10,message = "昵称不得小于3个字符大于10个字符")
    private String nickname;

}
