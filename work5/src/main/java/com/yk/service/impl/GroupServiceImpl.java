package com.yk.service.impl;

import com.yk.mapper.ConversationMapper;
import com.yk.mapper.GroupMapper;
import com.yk.mapper.UserMapper;
import com.yk.pojo.Group;
import com.yk.pojo.Result;
import com.yk.service.ConversationService;
import com.yk.service.GroupService;
import com.yk.utils.SecurityContextHolderUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 12080
 */
@Component
public class GroupServiceImpl implements GroupService {

    @Resource
    private SecurityContextHolderUtils securityContextHolderUtils;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ConversationMapper conversationMapper;

    @Transactional
    @Override
    public Result saveGroup(String groupName) {
        if(groupMapper.findGroupByName(groupName)!=null){
            return Result.error("群名已存在请换一个");
        }
        groupMapper.saveGroup(groupName,securityContextHolderUtils.getUserId());
        Integer id = groupMapper.findGroupByName(groupName).getId();
        groupMapper.saveGroupMember(securityContextHolderUtils.getUserId(),id);
        conversationMapper.saveConversation(null,null,id,0,"0",1);
        return Result.success();
    }

    @Override
    public Result updateGroup(String groupName, Integer id) {
        if (groupMapper.findGroupById(id)==null){
            return Result.error("群不存在");
        }
        if (!groupMapper.findGroupById(id).getGroupLeaderId().equals(securityContextHolderUtils.getUserId())){
            return Result.error("没权限修改群资料");
        }
        if(groupMapper.findGroupByName(groupName)!=null){
            return Result.error("群名已存在请换一个");
        }
        groupMapper.updateGroup(groupName,id);
        return Result.success();
    }

    @Transactional
    @Override
    public Result deleteGroup(Integer id) {
        if (groupMapper.findGroupById(id)==null){
            return Result.error("群不存在");
        }
        if (!groupMapper.findGroupById(id).getGroupLeaderId().equals(securityContextHolderUtils.getUserId())){
            return Result.error("没权限删除群");
        }
        groupMapper.deleteGroup(id);
        conversationMapper.deleteConversation(conversationMapper.findConversationId(id));
        return Result.success();
    }

    @Override
    public Result<Group> findGroupById(Integer id) {
        if (groupMapper.findGroupById(id)==null){
            return Result.error("群不存在");
        }
        return Result.success(groupMapper.findGroupById(id));
    }

    @Transactional
    @Override
    public Result saveGroupMember(Integer userId, Integer groupId) {
        if(userMapper.findByUserId(userId)==null){
            return Result.error("用户不存在");
        }
        if (groupMapper.findGroupById(groupId)==null){
            return Result.error("群不存在");
        }
        groupMapper.addMemberNum(groupId);
        groupMapper.saveGroupMember(userId,groupId);
        conversationMapper.saveConversationMember(conversationMapper.findConversationId(groupId),userId);
        return Result.success();
    }

    @Transactional
    @Override
    public Result deleteGroupMember(Integer userId, Integer groupId) {
        if(!securityContextHolderUtils.getUserId().equals(groupMapper.findGroupById(groupId).getGroupLeaderId())){
            return Result.error("你没权限");
        }
        if(userMapper.findByUserId(userId)==null){
            return Result.error("用户不存在");
        }
        if (groupMapper.findGroupById(groupId)==null){
            return Result.error("群不存在");
        }
        groupMapper.deleteMemberNum(groupId);
        groupMapper.deleteGroupMember(userId,groupId);
        conversationMapper.deleteConversationMember(conversationMapper.findConversationId(groupId),userId);
        return Result.success();
    }

    @Override
    public Result findGroupMemberById(Integer uid, Integer gid) {
        if(userMapper.findByUserId(uid)==null){
            return Result.error("用户不存在");
        }
        if (groupMapper.findGroupById(gid)==null){
            return Result.error("群不存在");
        }
        return Result.success(groupMapper.findGroupMemberById(uid,gid));
    }
}
