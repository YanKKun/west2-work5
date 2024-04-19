package com.yk.controller;

import com.yk.mapper.GroupMapper;
import com.yk.pojo.Group;
import com.yk.pojo.Result;
import com.yk.pojo.User;
import com.yk.pojo.dto.UserGroupDto;
import com.yk.service.GroupService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 群功能模块接口
 * @author 12080
 */
@RestController
@RequestMapping("/group")
@Validated
public class GroupController {

    @Resource
    private GroupService groupService;

    /**
     * 创建群
     * @param group 群对象
     * @return 结果集
     */
    @PostMapping("/createGroup")
    public Result createGroup(@Valid @RequestBody Group group){
        return groupService.saveGroup(group.getGroupName());
    }

    /**
     * 删除群
     * @param group 群对象
     * @return 结果集
     */
    @PostMapping("/deleteGroup")
    public Result deleteGroup(@Valid @RequestBody Group group){
        return groupService.deleteGroup(group.getId());
    }

    /**
     * 通过群id查找群
     * @param group 群对象
     * @return 结果集
     */
    @GetMapping("/findGroupById")
    public Result findGroupById(@Valid @RequestBody Group group){
        return groupService.findGroupById(group.getId());
    }

    /**
     * 通过名称模糊查找群
     * @param group 群对象
     * @return 群集合
     */
    @GetMapping("/listGroupByName")
    public Result listGroupByName(@Valid @RequestBody Group group){
        return groupService.listGroupByName(group.getGroupName());
    }

    /**
     * 添加群成员
     * @param userGroupDto 用户群DTO对象
     * @return 结果集
     */
    @PostMapping("/addMember")
    public Result addMember(@Valid @RequestBody UserGroupDto userGroupDto){
        return groupService.saveGroupMember(userGroupDto.getUser().getId(), userGroupDto.getGroup().getId());
    }

    /**
     * 踢出群成员
     * @param userGroupDto 用户群DTO对象
     * @return 结果集
     */
    @PostMapping("/deleteMember")
    public Result deleteMember(@Valid @RequestBody UserGroupDto userGroupDto){
        return groupService.deleteGroupMember(userGroupDto.getUser().getId(), userGroupDto.getGroup().getId());
    }


}
