/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto.response;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author yasithsandesh
 */
public class AllChatDataDTO implements  Serializable{
    
    private boolean status;
    private String message;
    private List<ChatData> chatDataList;

    public AllChatDataDTO() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChatData> getChatDataList() {
        return chatDataList;
    }

    public void setChatDataList(List<ChatData> chatDataList) {
        this.chatDataList = chatDataList;
    }
    
    
}
