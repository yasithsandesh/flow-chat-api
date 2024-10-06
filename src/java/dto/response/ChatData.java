/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto.response;

import java.io.Serializable;

/**
 *
 * @author yasithsandesh
 */
public class ChatData implements Serializable {

    private int otherUserId;
    private String otherUserMobile;
    private String otherUserName;
    private int otherUserStatus;
    private boolean avatarImageFound;
    private String avatarLetters;
    private String message;
    private int chatStatus;

    public ChatData() {
    }

    public int getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(int otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getOtherUserMobile() {
        return otherUserMobile;
    }

    public void setOtherUserMobile(String otherUserMobile) {
        this.otherUserMobile = otherUserMobile;
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public void setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
    }

    public int getOtherUserStatus() {
        return otherUserStatus;
    }

    public void setOtherUserStatus(int otherUserStatus) {
        this.otherUserStatus = otherUserStatus;
    }

    public boolean isAvatarImageFound() {
        return avatarImageFound;
    }

    public void setAvatarImageFound(boolean avatarImageFound) {
        this.avatarImageFound = avatarImageFound;
    }

    public String getAvatarLetters() {
        return avatarLetters;
    }

    public void setAvatarLetters(String avatarLetters) {
        this.avatarLetters = avatarLetters;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(int chatStatus) {
        this.chatStatus = chatStatus;
    }



    
}
