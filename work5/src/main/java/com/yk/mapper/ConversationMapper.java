package com.yk.mapper;

import com.yk.pojo.Conversation;
import com.yk.pojo.ConversationMember;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 12080
 */
@Mapper
public interface ConversationMapper {

    /**
     * 保存会话
     * @param fUid 用户id
     * @param tUid 目标用户id
     * @param tGid 目标群id
     * @param lSid 最后发送者id
     * @param lc 最后信息内容
     * @param iG 是否是群聊会话
     */
    @Insert("insert into conversation  (from_user_id,to_user_id,to_group_id,last_sender_uid,last_content,last_content_time,is_group) values(#{from_user_id},#{to_user_id},#{to_group_id},#{last_sender_uid},#{last_content},now(),#{is_group})")
    void saveConversation(@Param("from_user_id")Integer fUid,@Param("to_user_id")Integer tUid,@Param("to_group_id")Integer tGid,@Param("last_sender_uid")Integer lSid,@Param("last_content")String lc,@Param("is_group")Integer iG);

    /**
     * 删除会话
     * @param id 会话标识
     */
    @Delete("delete from conversation where id = #{id}")
    void deleteConversation(@Param("id")Integer id);

    /**
     * 更新会话
     * @param id 会话id
     * @param lSid 最后发送者id
     * @param lc 最后内容
     */
    @Update("update conversation set last_sender_uid=#{last_sender_id},last_content_time=now(),last_content=#{last_content} where id =#{id}")
    void updateConversationById(@Param("id")Integer id,@Param("last_sender_id")Integer lSid,@Param("last_content")String lc);

    /**
     * 添加成员进会话
     * @param id 会话id
     * @param uid 用户id
     */
    @Insert("insert into con_user (conversation_id, user_id) VALUES (#{conversation_id},#{user_id})")
    void saveConversationMember(@Param("conversation_id")Integer id,@Param("user_id")Integer uid);

    /**
     * 将用户移除会话
     * @param id 会话id
     * @param uid 用户id
     */
    @Delete("delete from con_user where conversation_id = #{id} and user_id = #{user_id}")
    void deleteConversationMember(@Param("conversation_id")Integer id,@Param("user_id")Integer uid);

    /**
     * 判断用户在不在会话中
     * @param id 会话id
     * @param uid 用户id
     * @return 结果
     */
    @Select("select id, conversation_id, user_id from con_user where conversation_id = #{conversation_id} and user_id =#{user_id}")
    ConversationMember isConversationMember(@Param("conversation_id")Integer id, @Param("user_id")Integer uid);

    /**
     * 查找用户会话
     * @param fUid 发起会话用户id
     * @param tUid 目标用户id
     * @return 会话
     */
    Conversation findConversation(@Param("from_user_id")Integer fUid,@Param("to_user_id")Integer tUid);

    /**
     * 查找群组会话
     * @param tGid 目标群组id
     * @return 会话
     */
    Conversation findGroupConversation(@Param("to_group_id")Integer tGid);

    /**
     * 查找全部用户会话
     * @param uid 发起会话用户id
     * @return 会话
     */
    List<Conversation> listAllConversation(@Param("from_user_id")Integer uid);


    /**
     * 查找全部群组会话
     * @param uid 用户id
     * @return 会话
     */
    List<Conversation> listAllGroupConversation(@Param("uid")Integer uid);

    /**
     * 根据id查找会话
     * @param id 会话id
     * @return 会话
     */
    @Select("select id, from_user_id, to_user_id, to_group_id, last_sender_uid, last_content, last_content_time, is_group from conversation where id =#{id}")
    Conversation findConversationById(@Param("id")Integer id);

    /**
     * 通过群组id找到会话id
     * @param tGid 群组id
     * @return 会话id
     */
    @Select("select id from conversation where to_group_id = #{to_group_id}")
    int findConversationId(@Param("to_group_id")Integer tGid);

    /**
     * 确认会话是否存在
     * @param tGid 群id
     * @return 是否
     */
    @Select("select id from conversation where to_group_id = #{to_group_id}")
    Conversation findConversation2(@Param("to_group_id")Integer tGid);
}
