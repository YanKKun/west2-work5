package com.yk.mapper;

import com.yk.pojo.Content;
import com.yk.pojo.vo.UserVo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;


@Mapper
public interface ChatMapper {

    /**
     * 发消息
     * @param fromUserId 主用户id
     * @param toUserId 目标用户id
     * @param toGroupId 目标群id
     */
    @Insert("insert into content set from_userid = #{from_userid} ,to_userid=#{to_userid},to_groupid=#{to_groupid},content=#{content},create_time=now()")
    void saveContext(@Param("from_userid") Integer fromUserId,@Param("to_userid") Integer toUserId,@Param("to_groupid")Integer toGroupId,@Param("content")String content);

    /**
     * 通过时间查找联系人消息
     * @param fromUserId  主用户id
     * @param toUserId 目标用户id
     * @param beganTime 起始时间
     * @param endTime 终止时间
     * @return 消息集合
     */
    @Select("select id,content,create_time from content where from_userid = #{from_userid} and to_userid=#{to_userid} and timestamp(create_time)between #{began_time} and #{end_time}")
    List<Content> findContentByContactIdAndTime(@Param("from_userid") Integer fromUserId, @Param("to_userid") Integer toUserId, @Param("began_time") LocalDateTime beganTime, @Param("end_time")LocalDateTime endTime);

    /**
     * 通过时间查找群消息
     * @param toGroupId 目标群id
     * @param beganTime 起始时间
     * @param endTime 终止时间
     * @return 消息集合
     */
    @Select("select id,content,create_time from content where  to_groupid=#{to_groupid} and timestamp(create_time)between #{began_time} and #{end_time}")
    List<Content> findContentByGroupIdAndTimeAndUserId( @Param("to_groupid") Integer toGroupId, @Param("began_time") LocalDateTime beganTime, @Param("end_time")LocalDateTime endTime);

    /**
     * 添加联系人
     * @param fromUserId 主用户id
     * @param toUserId 目标用户id
     */
    @Insert("insert into contact set from_userid = #{from_userid},to_userid=#{to_userid}")
    void saveContact(@Param("from_userid") Integer fromUserId, @Param("to_userid") Integer toUserId);

    /**
     * 删除联系人
     * @param fromUserId 主用户id
     * @param toUserId 目标用户id
     */
    @Delete("delete from contact where from_userid = #{from_userid} and to_userid =#{to_userid}")
    void deleteContact(@Param("from_userid") Integer fromUserId, @Param("to_userid") Integer toUserId);

    /**
     * 通过id查询联系人信息
     * @param fromUserId 主用户id
     * @param toUserId 目标用户id
     * @return 目标用户
     */
    UserVo findContactById(@Param("from_userid") Integer fromUserId, @Param("to_userid") Integer toUserId);

    /**
     * 查找用户所有联系人
     * @param fromUserId 用户id
     * @return 联系人集合
     */
    List<UserVo> listAllContactByUserId(@Param("from_userid") Integer fromUserId);


    /**
     * 屏蔽用户
     * @param fromUserId 主用户id
     * @param toUserId 目标用户
     */
    @Update("update contact set is_block = 1 where from_userid = #{from_userid} and to_userid = #{to_userid}")
    void blockContact(@Param("from_userid") Integer fromUserId,@Param("to_userid")Integer toUserId);


    /**
     * 取消屏蔽用户
     * @param fromUserId 主用户id
     * @param toUserId 目标用户
     */
    @Update("update contact set is_block = 0 where from_userid = #{from_userid} and to_userid = #{to_userid}")
    void unblockContact(@Param("from_userid") Integer fromUserId,@Param("to_userid")Integer toUserId);

}
