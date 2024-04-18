package com.yk.pojo.dto;

import com.yk.pojo.Group;
import com.yk.pojo.vo.TimeVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupTimeDto {
    private Group group;
    private TimeVo timeVo;
}
