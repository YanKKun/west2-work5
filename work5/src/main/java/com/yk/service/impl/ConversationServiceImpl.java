package com.yk.service.impl;

import com.yk.mapper.ConversationMapper;
import com.yk.pojo.Conversation;
import com.yk.pojo.Result;
import com.yk.service.ConversationService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 12080
 */
@Component
public class ConversationServiceImpl implements ConversationService {

    @Resource
    private ConversationMapper conversationMapper;

    @Transactional
    @Override
    public Result saveConversation(Integer fUid, Integer tUid, Integer tGid, Integer lSid, String lc, Integer iG) {
        if(fUid==null){
            conversationMapper.saveConversation(fUid, tUid, tGid, lSid, lc, 1);
        }else {
            conversationMapper.saveConversation(fUid, tUid, tGid, lSid, lc, 0);
        }
        return Result.success();
    }

    @Transactional
    @Override
    public Result deleteConversation(Integer id) {
        if (conversationMapper.findConversationById(id)!=null){
        conversationMapper.deleteConversation(id);
        return Result.success();
        }else {
            return Result.error("群组会话不存在");
        }
    }


    @Override
    public Result updateConversationById(Integer id, Integer lSid, String lc) {
        if (conversationMapper.findConversationById(id)!=null){
            conversationMapper.deleteConversation(id);
            return Result.success();
        }else {
            return Result.error("群组会话不存在");
        }
    }

    @Transactional
    @Override
    public Result saveConversationMember(Integer id, Integer uid) {
        if (conversationMapper.findConversationById(id)!=null){
            conversationMapper.saveConversationMember(id,uid);
            return Result.success();
        }else {
            return Result.error("群组会话不存在");
        }
    }

    @Override
    public Result<Conversation> findConversation(Integer fUid, Integer tUid) {
        return Result.success(conversationMapper.findConversation(fUid,tUid));
    }

    @Override
    public Result<Conversation> findGroupConversation(Integer tGid) {
        return Result.success(conversationMapper.findGroupConversation(tGid));
    }

    @Override
    public Result<List<Conversation>> listAllConversation(Integer uid) {
        return Result.success(conversationMapper.listAllConversation(uid));
    }

    @Override
    public Result<List<Conversation>> listAllGroupConversation(Integer uid) {
        return Result.success(conversationMapper.listAllGroupConversation(uid));
    }
}
