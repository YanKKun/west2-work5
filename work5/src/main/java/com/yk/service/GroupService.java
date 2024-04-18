package com.yk.service;

import com.yk.pojo.Group;
import com.yk.pojo.Result;
import org.springframework.stereotype.Service;

@Service
public interface GroupService {

    /**
     * 创建群
     * @param groupName 群名
     * @return 结果集
     */
    Result saveGroup(String groupName);

    /**
     * 更新群资料
     * @param groupName 群名
     * @param id 群id
     * @return 结果集
     */
    Result updateGroup(String groupName,Integer id);

    /**
     * 删群
     * @param id 群id
     * @return 结果集
     */
    Result deleteGroup(Integer id);

    /**
     * 通过id查找群
     * @param id 群id
     * @return 结果集
     */
    Result<Group> findGroupById(Integer id);

    /**
     * 添加用户进群
     * @param userId 用户id
     * @param groupId 群id
     * @return 结果集
     */
    Result saveGroupMember(Integer userId,Integer groupId);

    /**
     * 删除用户
     * @param userId 用户id
     * @param groupId 群id
     * @return 结果集
     */
    Result deleteGroupMember(Integer userId,Integer groupId);

    /**
     * 通过用户id和群id查找用户信息
     * @param uid 用户id
     * @param gid 群id
     * @return 用户
     */
    Result findGroupMemberById(Integer uid,Integer gid);

}
