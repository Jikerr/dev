package org.zhdev.bean.message;

import org.zhdev.bean.User;

public class ResponseMessage {

    public enum Type{
        PAGE_ONLINE_USERLIST,
        PAGE_USER_ONLINE_NOTIFY,
        PAGE_USER_OFFLINE_NOTIFY,
        PAGE_USER_SENDMESSAGE,
    }

    private String messageType;
    private String message;
    private User user;
    private User toUser;
    private String remark;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
