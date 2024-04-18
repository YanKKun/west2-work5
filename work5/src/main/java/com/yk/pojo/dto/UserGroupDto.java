package com.yk.pojo.dto;

import com.yk.pojo.Group;
import com.yk.pojo.User;
import lombok.Data;

@Data
public class UserGroupDto {
    private User user;
    private Group group;
}
