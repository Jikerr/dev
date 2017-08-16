package org.zhdev.controller;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zhdev.bean.User;
import org.zhdev.bean.WiselyMessage;
import org.zhdev.bean.WiselyResponse;
import org.zhdev.bean.message.ResponseMessage;
import org.zhdev.config.WebSocketConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class WsController {

    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping(value="/welcome")
    @SendTo("/topic/getResponse")
    public WiselyResponse say(WiselyMessage message) throws Exception{
        return new WiselyResponse("welcome , "+message.getName()+" !");
    }

    @MessageMapping(value="/getOnlinePlayer")
    @SendTo("/topic/getResponse")
    public ResponseMessage getOnlinePlayer() throws Exception{
        ResponseMessage responseMessage = new ResponseMessage();

        JSONObject message = new JSONObject();
        List<User> onlineUsers = WebSocketConfig.onlineUsers;

        JSONArray usersJsonArray = new JSONArray();
        for(User user : onlineUsers){
            usersJsonArray.put(new JSONObject(user));
        }
        message.put("onlineUsers",usersJsonArray);
        responseMessage.setMessage(message.toString());
        responseMessage.setMessageType(ResponseMessage.Type.PAGE_ONLINE_USERLIST.toString());
        responseMessage.setRemark("Is onLine user lists");
        //responseMessage.setUser((User)request.getSession().getAttribute("user"));
        responseMessage.setToUser(null);

        return responseMessage;
    }

    @RequestMapping(value="/login")
    public @ResponseBody String login(HttpServletRequest request, String userName, String password){
        User user = new User();
        user.setId(Long.valueOf(new Date().getTime()).intValue());
        user.setUserName(userName);
        user.setPwd(password);

        HttpSession session  = request.getSession();
        session.setAttribute("user",user);

        JSONObject response = new JSONObject(user);
        return response.toString();
    }

}
