package com.yk.controller;

import com.yk.pojo.Content;
import com.yk.pojo.Group;
import com.yk.pojo.Result;
import com.yk.pojo.User;
import com.yk.pojo.dto.ContentTimeDto;
import com.yk.pojo.dto.GroupTimeDto;
import com.yk.pojo.vo.TimeVo;
import com.yk.service.ChatService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 聊天模块接口
 * @author 12080
 */
@RestController
@RequestMapping("/chat")
@Validated
public class ChatController {

    @Resource
    private ChatService chatService;

    /**
     * 通过时间查找联系人的聊天记录
     * @param contentTimeDto 消息时间DTO对象
     * @return 结果集
     */
    @GetMapping("/findContactByTime")
    public Result findContentByContactIdAndTime(@Valid @RequestBody ContentTimeDto contentTimeDto){
        return chatService.findContentByContactIdAndTime(contentTimeDto.getContent().getToUserId(),contentTimeDto.getTimeVo().getBeganTime(),contentTimeDto.getTimeVo().getEndTime());
    }

    /**
     * 通过时间查找群的聊天记录
     * @param groupTimeDto 群时间DTO对象
     * @return 结果集
     */
    @GetMapping("/findGroupByTime")
    public Result findContentByGroupIdAndTimeAndUserId(@Valid @RequestBody GroupTimeDto groupTimeDto){
        return chatService.findContentByGroupIdAndTimeAndUserId(groupTimeDto.getGroup().getId(), groupTimeDto.getTimeVo().getBeganTime(), groupTimeDto.getTimeVo().getEndTime());
    }

    /**
     * 添加联系人
     * @param user 用户
     * @return 结果集
     */
    @PostMapping("/addContact")
    public Result addContact(@Valid @RequestBody User user){
        return chatService.saveContact(user.getId());
    }

    /**
     * 删除联系人
     * @param user 用户
     * @return 结果集
     */
    @PostMapping("/deleteContact")
    public Result deleteContact(@Valid @RequestBody User user){
        return chatService.deleteContact(user.getId());
    }

    /**
     * 通过id查找联系人
     * @param user 用户
     * @return 结果集
     */
    @GetMapping("/findContact")
    public Result findContactById(@Valid @RequestBody User user){
        return chatService.findContactById(user.getId());
    }

    /**
     * 列出全部联系人
     * @return 结果集
     */
    @GetMapping("/listContacts")
    public Result listContacts(){
        return chatService.listAllContactByUserId();
    }

    /**
     * 屏蔽用户
     * @param uid 用户id
     * @return 结果集
     */
    @PostMapping("/block")
    public Result blockContact(@RequestParam(value = "uid")Integer uid){
        return chatService.blockContact(uid);
    }

    /**
     * 取消屏蔽用户
     * @param uid 用户id
     * @return 结果集
     */
    @PostMapping("/unblock")
    public Result unblockContact(@RequestParam(value = "uid")Integer uid){
        return chatService.unblockContact(uid);
    }

}
