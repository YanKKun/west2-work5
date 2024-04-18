package com.yk.service.impl;

import com.yk.mapper.ChatMapper;
import com.yk.mapper.ConversationMapper;
import com.yk.mapper.GroupMapper;
import com.yk.mapper.UserMapper;
import com.yk.pojo.Content;
import com.yk.pojo.Result;
import com.yk.pojo.vo.UserVo;
import com.yk.service.ChatService;
import com.yk.utils.RedisCache;
import com.yk.utils.SecurityContextHolderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 12080
 */
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatMapper chatMapper;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ConversationMapper conversationMapper;

    @Resource
    private SecurityContextHolderUtils securityContextHolderUtils;

    @Resource
    private RedisCache redisCache;

    @Transactional
    @Override
    public Result saveContext(Integer toUserId, Integer toGroupId, String content) {
        if(userMapper.findByUserId(toUserId)==null){
            return Result.error("用户不存在");
        }
        if(groupMapper.findGroupById(toGroupId)==null){
            return Result.error("群不存在");
        }
        chatMapper.saveContext(securityContextHolderUtils.getUserId(),toUserId,toGroupId,content);
        return Result.success();
    }

    @Transactional
    @Override
    public Result saveContext2(Integer fromUserId, Integer toUserId, Integer toGroupId, String content) {
        log.info("开始存消息");
        chatMapper.saveContext(fromUserId,toUserId,toGroupId,content);
        log.info("存入数据库");
        LocalDateTime now = LocalDateTime.now();
        log.info("开始判断会话");
        if(conversationMapper.findConversation(fromUserId,toUserId)!=null){
            log.info("会话存在");
            Integer id = conversationMapper.findConversation(fromUserId, toUserId).getId();
            log.info("id:{}",id);
            conversationMapper.updateConversationById(id,fromUserId,content);
        }else if(conversationMapper.findConversation2(toGroupId)!=null){
            log.info("群聊会话存在");
            int conversationId = conversationMapper.findConversationId(toGroupId);
            if(conversationMapper.isConversationMember(conversationId,fromUserId)==null){
                conversationMapper.saveConversationMember(conversationId,fromUserId);
            }
            conversationMapper.updateConversationById(conversationId,fromUserId,content );
        }else{
            log.info("会话不存在");
            if (toUserId!=null){
                Integer id = conversationMapper.findConversation(fromUserId, toUserId).getId();
                conversationMapper.saveConversation(fromUserId,toUserId,null,fromUserId,content,0);
                conversationMapper.saveConversationMember(id,fromUserId);
                conversationMapper.saveConversationMember(id,toUserId);
            }else if(toGroupId!=null){
                conversationMapper.saveConversation(null,null,toGroupId,fromUserId,content,1);
            }
        }
        return Result.success();
    }

    @Override
    public Result<List<Content>> findContentByContactIdAndTime(Integer toUserId, LocalDateTime beganTime, LocalDateTime endTime) {
        if(userMapper.findByUserId(toUserId)==null){
            return Result.error("用户不存在");
        }
        return Result.success(chatMapper.findContentByContactIdAndTime(securityContextHolderUtils.getUserId(),toUserId,beganTime,endTime));
    }

    @Override
    public Result<List<Content>> findContentByGroupIdAndTimeAndUserId(Integer toGroupId, LocalDateTime beganTime, LocalDateTime endTime) {
        if(groupMapper.findGroupById(toGroupId)==null){
            return Result.error("群不存在");
        }
        return Result.success(chatMapper.findContentByGroupIdAndTimeAndUserId(toGroupId,beganTime,endTime));
    }

    @Transactional
    @Override
    public Result saveContact(Integer toUserId) {
        if(userMapper.findByUserId(toUserId)==null){
            return Result.error("用户不存在");
        }
        chatMapper.saveContact(securityContextHolderUtils.getUserId(),toUserId);
        return Result.success();
    }

    @Transactional
    @Override
    public Result deleteContact(Integer toUserId) {
        if(chatMapper.findContactById(securityContextHolderUtils.getUserId(),toUserId)==null){
            return Result.error("用户不是你的联系人");
        }
        chatMapper.deleteContact(securityContextHolderUtils.getUserId(),toUserId);
        return null;
    }

    @Override
    public Result<UserVo> findContactById(Integer toUserId) {
        if(userMapper.findByUserId(toUserId)==null){
            return Result.error("用户不存在");
        }
        return Result.success(chatMapper.findContactById(securityContextHolderUtils.getUserId(),toUserId));
    }
    @Override
    public Result<UserVo> findContactById2(Integer fromUserId,Integer toUserId) {
        if(userMapper.findByUserId(toUserId)==null){
            return Result.error("用户不存在");
        }
        return Result.success(chatMapper.findContactById(fromUserId,toUserId));
    }
    @Override
    public Result<List<UserVo>> listAllContactByUserId() {
        return Result.success(chatMapper.listAllContactByUserId(securityContextHolderUtils.getUserId()));
    }

    @Override
    public Result blockContact(Integer uid) {
        Integer userId = securityContextHolderUtils.getUserId();
        if (chatMapper.findContactById(userId, uid) == null) {
            chatMapper.saveContact(userId, uid);
        }
        chatMapper.blockContact(userId,uid);
        redisCache.setCacheObject("block:"+uid,userId);
        return Result.success();
    }

    @Override
    public Result unblockContact(Integer uid) {
        Integer userId = securityContextHolderUtils.getUserId();
        if(chatMapper.findContactById(userId,uid)!=null){
            return Result.error("该用户未被屏蔽");
        }
        chatMapper.unblockContact(userId,uid);
        redisCache.deleteObject("block:"+uid);
        return Result.success();
    }
}
