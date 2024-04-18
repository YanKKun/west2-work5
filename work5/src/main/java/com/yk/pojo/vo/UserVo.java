package com.yk.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
/**
 * @author 12080
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserVo {
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull
    private Integer id;
    private String username;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;

}