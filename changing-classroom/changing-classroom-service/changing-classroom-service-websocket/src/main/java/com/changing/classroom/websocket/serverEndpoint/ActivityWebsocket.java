package com.changing.classroom.websocket.serverEndpoint;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import com.changing.classroom.feign.activity.ActivityFeignClient;
import com.changing.classroom.feign.user.UserFeignClient;
import com.changing.classroom.model.entity.websocket.MessageInfo;
import com.changing.classroom.model.vo.h5.UserInfoVo;
import com.changing.classroom.model.vo.websocket.MessageInfoVo;
import com.changing.classroom.websocket.config.WebSocketConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ServerEndpoint(value = "/api/websocket/activity/{activityId}", configurator = WebSocketConfig.class)
public class ActivityWebsocket {

    private UserFeignClient userFeignClient;
    private ActivityFeignClient activityFeignClient;
    // 全局静态变量，保存 ApplicationContext
    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ActivityWebsocket.applicationContext = applicationContext;
    }

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 用户ID
     */
    public Long activityId;
    public UserInfoVo user;
    ObjectMapper objectMapper = new ObjectMapper();

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    public static CopyOnWriteArraySet<ActivityWebsocket> websockets = new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "activityId") String activityId) throws JsonProcessingException {
        // 连接创建的时候，从 ApplicationContext 获取到 Bean 进行初始化
        final String userId = (String) session.getUserProperties().get("userId");
        this.userFeignClient = ActivityWebsocket.applicationContext.getBean(UserFeignClient.class);
        this.activityFeignClient = ActivityWebsocket.applicationContext.getBean(ActivityFeignClient.class);
        this.session = session;
        this.user = this.userFeignClient.getUserInfoById(userId).getBody();
        this.activityId = Long.valueOf(activityId);
        websockets.add(this);
        log.info("【websocket消息】有新的连接，总数为:" + websockets.size());
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            websockets.remove(this);
            log.info("【websocket消息】连接断开，总数为:" + websockets.size());
        } catch (Exception e) {
        }
    }

    @OnMessage
    public void onMessage(String message) throws JsonProcessingException {
        log.info("【websocket消息】收到客户端消息:" + message);
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setActivityId(this.activityId);
        messageInfo.setMessage(objectMapper.readValue(message, String.class));
        messageInfo.setUserId(this.user.getId());
        messageInfo.setUserAvatar(this.user.getAvatar());
        messageInfo.setUserName(this.user.getName());
        activityFeignClient.savaMessageInfo(messageInfo);
        sendAllMessage(message, this.user, this.activityId);
    }

    /**
     * 发送错误时的处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误,原因:" + error.getMessage());
        error.printStackTrace();
    }


    // 此为广播消息
    public void sendAllMessage(String message, UserInfoVo user, Long activityId) {
        log.info("【websocket消息】广播消息:" + message);
        for (ActivityWebsocket webSocket : websockets) {
            try {
                if (webSocket.session.isOpen()) {
                    if (!webSocket.activityId.equals(activityId)) {
                        return;
                    }
                    MessageInfoVo messageInfoVo = new MessageInfoVo(user.getAvatar(), user.getName(), new Date(), objectMapper.readValue(message, String.class), false);
                    if (webSocket.user.getId().equals(user.getId())) {
                        messageInfoVo.setMineMsg(true);
                    }
                    webSocket.session.getAsyncRemote().sendText(objectMapper.writeValueAsString(messageInfoVo));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}