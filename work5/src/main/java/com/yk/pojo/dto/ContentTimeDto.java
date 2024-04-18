package com.yk.pojo.dto;

import com.yk.pojo.Content;
import com.yk.pojo.vo.TimeVo;
import com.yk.pojo.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 12080
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentTimeDto {
    private Content content;
    private TimeVo timeVo;
}
