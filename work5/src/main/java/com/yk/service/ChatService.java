package com.yk.service;

import com.yk.pojo.Content;
import com.yk.pojo.Result;
import com.yk.pojo.vo.UserVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 12080
 */
@Service
public interface ChatService {

    /**
     * 发消息
     * @param toUserId 目标用户id
     * @param toGroupId 目标群id
     * @param content 消息内容
     * @return 结果集
     */
    Result saveContext(Integer toUserId,Integer toGroupId,String content);

    /**
     * 发消息
     * @param fromUserId 用户id
     * @param toUserId 目标用户id
     * @param toGroupId 目标群id
     * @param content 消息内容
     * @return 结果集
     */
    Result saveContext2(Integer fromUserId,Integer toUserId,Integer toGroupId,String content);

    /**
     * 通过时间查找联系人消息
     * @param toUserId 目标用户id
     * @param beganTime 起始时间
     * @param endTime 终止时间
     * @return 消息集合
     */
    Result<List<Content>> findContentByContactIdAndTime(Integer toUserId, LocalDateTime beganTime, LocalDateTime endTime);
    /**
     * 通过时间查找群消息
     * @param toGroupId 目标群id
     * @param beganTime 起始时间
     * @param endTime 终止时间
     * @return 消息集合
     */
    Result<List<Content>> findContentByGroupIdAndTimeAndUserId(Integer toGroupId, LocalDateTime beganTime, LocalDateTime endTime);

    /**
     * 添加联系人
     * @param toUserId 目标用户id
     * @return 结果集
     */
    Result saveContact(Integer toUserId);

    /**
     * 删除联系人
     * @param toUserId 目标用户id
     * @return 结果集
     */
    Result deleteContact(Integer toUserId);

    /**
     * 通过id查询联系人信息
     * @param toUserId 目标用户id
     * @return 目标用户
     */
    Result<UserVo> findContactById(Integer toUserId);

    /**
     * 通过id查询联系人信息
     * @param fromUserId 用户id
     * @param toUserId 目标用户id
     * @return 目标用户
     */
    Result<UserVo> findContactById2(Integer fromUserId, Integer toUserId);

    /**
     * 查找用户所有联系人
     * @return 联系人集合
     */
    Result<List<UserVo>> listAllContactByUserId();

    /**
     * 屏蔽用户
     * @param uid 目标id
     * @return 结果集
     */
    Result blockContact(Integer uid);

    /**
     * 屏蔽用户
     * @param uid 目标id
     * @return 结果集
     */
    Result unblockContact(Integer uid);
}
