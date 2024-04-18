package com.yk.mapper;

import com.yk.pojo.Group;
import com.yk.pojo.vo.UserVo;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface GroupMapper {

    /**
     * 创建群
     * @param groupName 群名
     * @param groupLeader 群主id
     */
    @Insert("insert into `group` set group_name = #{group_name},group_leader = #{group_leader},create_time = now(),is_deleted=0")
    void saveGroup(@Param("group_name") String groupName,@Param("group_leader") Integer groupLeader);

    /**
     * 更新群信息
     * @param groupName 群名
     * @param id 群id
     */
    @Update("update `group` set group_name = #{group_name} where id = #{id}")
    void updateGroup(@Param("group_name") String groupName,@Param("id") Integer id);

    /**
     * 删除群聊
     * @param id 群id
     */
    @Update("update `group` set is_deleted = 1 where id=#{id}")
    void deleteGroup(@Param("id") Integer id);

    /**
     * 通过群id查找群
     * @param id 群id
     * @return 群
     */

    @Select("select id, group_name, group_leader, member_num, create_time from `group` where is_deleted=0 and id = #{id}")
    Group findGroupById(@Param("id")Integer id);

    /**
     * 通过群名查找群
     * @param name 群名
     * @return 群
     */
    @Select("select id, group_name, group_leader,member_num,create_time from `group` where group_name = #{group_name}")
    Group findGroupByName(@Param("group_name") String name);
    /**
     * 将用户添加到群聊中
     * @param userId 用户id
     * @param groupId 群id
     */
    @Insert("insert into user_group set userid = #{userid},groupid = #{groupid}")
    void saveGroupMember(@Param("userid") Integer userId,@Param("groupid") Integer groupId);

    /**
     * 将用户移除群聊
     * @param userId 用户id
     * @param groupId 群id
     */
    @Delete("delete from user_group where userid = #{userid} and groupid = #{groupid}")
    void deleteGroupMember(@Param("userid") Integer userId,@Param("groupid") Integer groupId);

    /**
     * 通过用户id和群id查找用户信息
     * @param uid 用户id
     * @param gid 群id
     * @return 用户
     */
    UserVo findGroupMemberById(@Param("userid")Integer uid,@Param("groupid")Integer gid);

    /**
     * 返回群成员
     * @param gid 群id
     * @return 群成员
     */
    List<UserVo> listAllGroupMember(@Param("groupid")Integer gid);

    /**
     * 增加群人数
     * @param gid 群id
     */
    @Update("update `group` set member_num =member_num+1 where id = #{groupid}")
    void addMemberNum(@Param("groupid")Integer gid);

    /**
     * 减少群人数
     * @param gid 群id
     */
    @Update("update `group` set member_num =member_num-1 where id = #{groupid}")
    void deleteMemberNum(@Param("groupid")Integer gid);
}
