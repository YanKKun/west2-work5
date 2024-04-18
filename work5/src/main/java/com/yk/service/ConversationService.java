package com.yk.service;

import com.yk.pojo.Conversation;
import com.yk.pojo.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConversationService {
    /**
     * 保存会话
     * @param fUid 用户id
     * @param tUid 目标用户id
     * @param tGid 目标群id
     * @param lSid 最后发送者id
     * @param lc 最后信息内容
     * @param iG 是否是群聊会话
     * @return 结果集
     */
    Result saveConversation(Integer fUid, Integer tUid, Integer tGid, Integer lSid, String lc, Integer iG);

    /**
     * 删除会话
     * @param id 会话标识
     * @return 结果集
     */
    Result deleteConversation(Integer id);


    /**
     * 更新会话
     * @param id 会话id
     * @param lSid 最后发送者id
     * @param lc 最后内容
     * @return 结果集
     */
    Result updateConversationById(Integer id, Integer lSid,String lc);


    /**
     * 保存会话用户
     * @param id 会话id
     * @param uid 用户id
     * @return 结果集
     */
    Result saveConversationMember(Integer id,Integer uid);

    /**
     * 查找用户会话
     * @param fUid 发起会话用户id
     * @param tUid 目标用户id
     * @return 会话
     */
    Result<Conversation> findConversation(Integer fUid,Integer tUid);

    /**
     * 查找群组会话
     * @param tGid 目标群组id
     * @return 会话
     */
    Result<Conversation> findGroupConversation(Integer tGid);

    /**
     * 查找全部用户会话
     * @param uid 发起会话用户id
     * @return 会话
     */
    Result<List<Conversation>> listAllConversation(Integer uid);

    /**
     * 查找全部群组会话
     * @param uid 用户id
     * @return 会话
     */
    Result<List<Conversation>> listAllGroupConversation(Integer uid);
}
