package com.yk.controller;

import com.yk.pojo.Conversation;
import com.yk.pojo.Result;
import com.yk.service.ConversationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 会话模块接口
 * @author 12080
 */
@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Resource
    private ConversationService conversationService;

    /**
     * 添加会话
     * @param conversation 会话对象
     * @return 结果集
     */
    @PostMapping("/addc")
    Result saveConversation(@Valid @RequestBody Conversation conversation){
        return conversationService.saveConversation(conversation.getFromUserId(),conversation.getToUserId(),conversation.getToGroupId(),0,"0",0);
    }

    /**
     * 查找用户会话
     * @param fUid 主用户id
     * @param tUid 目标用户id
     * @return 结果集
     */
    @GetMapping("/findConversation")
    Result<Conversation> findConversation(@RequestParam(value = "uid") Integer fUid,@RequestParam(value = "tid") Integer tUid){
        return conversationService.findConversation(fUid, tUid);
    }

    /**
     * 查找群会话
     * @param tGid 群id
     * @return 结果集
     */
    @GetMapping("/findGroupConversation")
    Result<Conversation> findGroupConversation(@RequestParam(value = "gid")Integer tGid){
        return conversationService.findGroupConversation(tGid);
    }

    /**
     * 列出该用户所有联系人会话
     * @param uid 用户id
     * @return 结果集
     */
    @GetMapping("/findAllConversation")
    Result<List<Conversation>> listAllConversation(@RequestParam(value = "uid")Integer uid){
        return conversationService.listAllConversation(uid);
    }

    /**
     * 列出该用户参与的所有群会话
     * @param uid 用户id
     * @return 结果集
     */
    @GetMapping("/findAllGroupConversation")
    Result<List<Conversation>> listAllGroupConversation(@RequestParam(value = "uid")Integer uid){
        return conversationService.listAllGroupConversation(uid);
    }

}
