package com.yk.utils;

import com.alibaba.fastjson.JSON;
import com.yk.mapper.ChatMapper;
import com.yk.mapper.ConversationMapper;
import com.yk.mapper.GroupMapper;
import com.yk.pojo.Content;
import com.yk.pojo.LoginUser;
import com.yk.pojo.vo.UserVo;
import com.yk.service.ChatService;
import com.yk.service.GroupService;
import com.yk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class WebSocketUtils {

    @Resource
    private UserService userService;
    @Resource
    private RedisCache redisCache;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ChatService chatService;
    @Resource
    private ChatMapper chatMapper;
    @Resource
    private GroupService groupService;
    @Resource
    private GroupMapper groupMapper;
    @Resource
    private ConversationMapper conversationMapper;
    private static final Map<String, Session> ON_LINE_USERS = new ConcurrentHashMap<>();

    public void addSession(Session session) throws IOException {
        Integer id = ((LoginUser) session.getUserProperties().get("loginUser")).getUser().getId();
        String key = "unreadCount:"+id;
        if(Boolean.FALSE.equals(redisTemplate.hasKey(key))){
            redisTemplate.opsForValue().set(key,0);
            session.getBasicRemote().sendText("你有"+redisTemplate.opsForValue().get(key)+"条未读信息");
        }else {
            session.getBasicRemote().sendText("你有"+redisTemplate.opsForValue().get(key)+"条未读信息");
        }
        ON_LINE_USERS.put(id.toString(),session);
    }

    public void removeSession(Session session){
        ON_LINE_USERS.remove(((LoginUser)session.getUserProperties().get("loginUser")).getUser().getId().toString(),session);
    }

    public void broadcastAllPeople(String message) throws IOException {
        for (Map.Entry<String, Session> entry : ON_LINE_USERS.entrySet()) {
            Session session = entry.getValue();
            session.getBasicRemote().sendText(message);
        }

    }
    public int onlineNum(){
        return ON_LINE_USERS.size();
    }
    public void broadcastAllContacts(Integer useId,String message) throws IOException {
        //遍历map集合
        for (Map.Entry<String, Session> entry : ON_LINE_USERS.entrySet()) {
            //获取到所有用户对应的session对象
            Session session = entry.getValue();
            Map<String, Object> userProperties = session.getUserProperties();
            LoginUser loginUser = (LoginUser) userProperties.get("loginUser");
            if(!useId.equals(loginUser.getUser().getId()) &&chatService.findContactById2(useId,loginUser.getUser().getId())!=null){
                //发送消息
                session.getBasicRemote().sendText(message);
            }
        }

    }

    public void sendMessage(Session session,String message) throws IOException {
        Content content = JSON.parseObject(message, Content.class);
        Integer toUserId = content.getToUserId();
        Integer toGroupId = content.getToGroupId();
        String content1 = content.getContent();
        String content2 = content.getContent();
        LoginUser loginUser = (LoginUser) session.getUserProperties().get("loginUser");
        Integer id = loginUser.getUser().getId();
        String nickname = loginUser.getUser().getNickname();
        if(toUserId!=null&&toGroupId==null){
            if(Base64.getDecoder().decode(content1)!=null){
                content2= Base64Utils.generateImage(content1);
            }
            log.info("可发");
            if (chatMapper.findContactById(id,toUserId)!=null){
                Session session1 = ON_LINE_USERS.get(toUserId.toString());
                if(Boolean.TRUE.equals(redisTemplate.hasKey("block:" + id)&&redisTemplate.opsForValue().get("block:" + id)==toUserId)){
                    log.info("封禁不发");
                    chatService.saveContext2(id,toUserId,null,content2);
                }else{
                    if (session1!=null){
                        log.info("在线发送");
                        ON_LINE_USERS.get(toUserId.toString()).getBasicRemote().sendText(loginUser.getUser().getNickname()+"对你说:"+content1);
                    }else {
                        log.info("不在线");
                        String key = "unreadCount:"+toUserId;
                        String Key = "unreadMessage:"+toUserId;
                        int num = (int) redisTemplate.opsForValue().get(key);
                        if(redisCache.getCacheList(Key)==null){
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(nickname+"--"+content.getContent()+"--"+ LocalDateTime.now());
                            redisCache.setCacheList(Key,list);
                        }else {
                            List<Object> cacheList = redisCache.getCacheList(Key);
                            cacheList.add(nickname+"--"+content.getContent()+"--"+ LocalDateTime.now());
                            redisCache.deleteObject(Key);
                            redisCache.setCacheList(Key,cacheList);
                        }
                        redisTemplate.opsForValue().set(key,num+1);
                        log.info("存入redis");
                        session.getBasicRemote().sendText("已发送,该联系人不在线");
                    }
                    chatService.saveContext2(id,toUserId,null,content2);
                    log.info("存入数据库");
                }
            }else {
                session.getBasicRemote().sendText("你没有该联系人");
            }
        }else if(toGroupId!=null&&toUserId==null){
            if(Base64.getDecoder().decode(content1)!=null){
                content2=Base64Utils.generateImage(content1);
            }
            log.info("进入群聊处理");
            if(groupMapper.findGroupMemberById(id,toGroupId)!=null){
                log.info("为该群成员");
                String groupName = groupMapper.findGroupById(toGroupId).getGroupName();
                log.info("获得群聊名称");
                for (UserVo userVo : groupMapper.listAllGroupMember(toGroupId)) {
                    Integer toUserId2 = userVo.getId();
                    int num=0;
                    String key = "unreadCount:" + toUserId2;
                    String Key = "unreadMessage:" + toUserId2;
                    if(Boolean.FALSE.equals(redisTemplate.hasKey("block:" + id))||redisTemplate.opsForValue().get("block:" + id)!=toUserId2) {
                        num = (int) redisTemplate.opsForValue().get(key);
                        if (redisCache.getCacheList(Key) == null) {
                            ArrayList<Object> list = new ArrayList<>();
                            list.add(nickname + "--" + groupName + "--" + content.getContent() + "--" + LocalDateTime.now());
                            redisCache.setCacheList(Key, list);
                        } else {
                            List<Object> cacheList = redisCache.getCacheList(Key);
                            cacheList.add(nickname + "--" + groupName + "--" + content.getContent() + "--" + LocalDateTime.now());
                            redisCache.deleteObject(Key);
                            redisCache.setCacheList(Key, cacheList);
                        }
                        redisTemplate.opsForValue().set(key,num+1);
                    }
                }
                log.info("将消息全部投入未读取");
                for (Map.Entry<String, Session> entry : ON_LINE_USERS.entrySet()) {
                    Integer key = Integer.valueOf(entry.getKey());
                    if (groupMapper.findGroupMemberById(key, toGroupId)!=null) {
                        if (Boolean.FALSE.equals(redisTemplate.hasKey("block:" + key))||redisTemplate.opsForValue().get("block:" + key)!=id) {
                            ON_LINE_USERS.get(entry.getKey()).getBasicRemote().sendText(loginUser.getUser().getNickname() + "在" + groupName + "里说:" + content1);
                            log.info("发消息");
                            String key2 = "unreadCount:" + key;
                            String Key2 = "unreadMessage:" + key;
                            int num = (int) redisTemplate.opsForValue().get(key2);
                            List<Object> cacheList = redisCache.getCacheList(Key2);
                            if(cacheList.size()-1==0){
                                redisCache.deleteObject(Key2);
                                redisTemplate.opsForValue().set(key2, 0);
                            }else {
                                cacheList.remove(cacheList.size() - 1);
                                redisCache.deleteObject(Key2);
                                redisCache.setCacheList(Key2, cacheList);
                                redisTemplate.opsForValue().set(key2, num - 1);
                            }

                        }
                    }
                }
                chatService.saveContext2(id,null,toGroupId,content2);
            }else {
                session.getBasicRemote().sendText("你没有加入该群聊");
            }
        }else if("checkUnread".equals(content1)){
            String key = "unreadMessage:" + id;
            String key2 = "unreadCount:" +id;
            List<Object> cacheList = redisCache.getCacheList(key);
            for (Object s : cacheList) {
                session.getBasicRemote().sendText((String) s);
            }
            redisCache.setCacheObject(key2,0);
            redisCache.deleteObject(key);
        }else {
            session.getBasicRemote().sendText("操作有误");
        }
    }

    @Scheduled(cron = " 30 * * * * ?   ")
    public void heartBeat() throws IOException {
        broadcastAllPeople("heartBeat");
    }



}
