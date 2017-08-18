package org.zhdev.bean.message;

/**
 * Created by MACHENIKE on 2017/8/14.
 */
public class UserSendMessage {
    private Integer msgId;
    private String msg;
    private String userId;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
