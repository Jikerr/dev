package org.zhdev.config;


import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.zhdev.bean.User;
import org.zhdev.bean.message.ResponseMessage;
import org.zhdev.web.websocket.SessionAuthHandshakeInterceptor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    private final static Logger logger = Logger.getLogger(WebSocketConfig.class);

    public static List<User> onlineUsers = new ArrayList<User>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {

        stompEndpointRegistry.addEndpoint("/endpointWisely").addInterceptors(new SessionAuthHandshakeInterceptor()).setHandshakeHandler(new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                User user = (User) attributes.get("user");
                //System.out.println("新用户 : "+user.getUserName());
                return new MyPrincipal(user);
            }

        }).withSockJS();

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                System.out.println("recv : " + message);
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

                User user = (User) accessor.getSessionAttributes().get("user");
                System.out.println("status : "+accessor.getCommand());
                System.out.println("who : "+user.getUserName());

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    logger.info("connect....");

                    onlineUsers.add(user);//在线列表增加用户
                    accessor.setUser(user);//

                    //有人上线 , 构造上线提醒
                    ResponseMessage responseMessage = new ResponseMessage();
                    responseMessage.setMessage(new JSONObject(user).toString());
                    responseMessage.setRemark("Is user online notify");
                    responseMessage.setToUser(null);
                    responseMessage.setUser(null);
                    responseMessage.setMessageType(ResponseMessage.Type.PAGE_USER_ONLINE_NOTIFY.toString());

                    //有人上线 , 通知所有订阅人
                    simpMessagingTemplate.convertAndSend("/topic/getResponse",responseMessage);
                }
                if (StompCommand.CONNECTED.equals(accessor.getCommand())) {
                    System.out.println("connected....");
                }
                if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                    //从在线列表删除
                    onlineUsers.remove(user);
                    //有人下线 , 构造下线提醒
                    ResponseMessage responseMessage = new ResponseMessage();
                    responseMessage.setMessage(new JSONObject(user).toString());
                    responseMessage.setRemark("Is user Offline notify");
                    responseMessage.setToUser(null);
                    responseMessage.setUser(null);
                    responseMessage.setMessageType(ResponseMessage.Type.PAGE_USER_OFFLINE_NOTIFY.toString());

                    //有人上线 , 通知所有订阅人
                    simpMessagingTemplate.convertAndSend("/topic/getResponse",responseMessage);
                }

                if (StompCommand.ERROR.equals(accessor.getCommand())) {
                    logger.info("error..........");
                    //有人上线 , 通知所有订阅人
                    //simpMessagingTemplate.convertAndSend("/topic/getResponse",responseMessage);
                }

                return super.preSend(message, channel);
            }
        });
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                System.out.println("send: " + message);
                return super.preSend(message, channel);
            }
        });
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }

    class MyPrincipal implements Principal {

        private User user;

        public MyPrincipal(User user) {
            this.user = user;
        }

        @Override
        public String getName() {
            return String.valueOf(user.getId());
        }
    }
}
