/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.chat;

import com.google.gson.Gson;
import dto.response.AllChatDataDTO;
import dto.response.ChatData;
import entity.Chat;
import entity.User;
import entity.UserStatus;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

/**
 *
 * @author yasithsandesh
 */
@WebServlet(name = "AllChat", urlPatterns = {"/AllChat"})
public class AllChat extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String userId = req.getParameter("id");
        Gson gson = new Gson();
        AllChatDataDTO allChatDataDTO = new AllChatDataDTO();
        try {

            Session session = HibernateUtil.getSessionFactory().openSession();
            //get user status upadete online
            UserStatus userStatus = (UserStatus) session.get(UserStatus.class, 1);
            // get user
            User user = (User) session.get(User.class, Integer.parseInt(userId));
            //update status this user
            user.setUserStatus(userStatus);
            session.update(user);

            //search other users
            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.ne("id", user.getId()));

         

                List<User> otherUsers = criteria1.list();

                List<ChatData> chatDataList = new ArrayList<>();
                //GET OTEHR USER ONE BY ONE
                for (User otherUser : otherUsers) {

                    //GET LAST CONVERSATION
                    Criteria getChatList = session.createCriteria(Chat.class);
                    getChatList.add(Restrictions.or(
                            Restrictions.and(Restrictions.eq("fromUser", user), Restrictions.eq("toUser", otherUser)),
                            Restrictions.and(Restrictions.eq("fromUser", otherUser), Restrictions.eq("toUser", user))
                    ));
                    getChatList.addOrder(Order.desc("id"));
                    getChatList.setMaxResults(1);

                    ChatData chatData = new ChatData();
                    chatData.setAvatarLetters(otherUser.getFirstName().charAt(0) + "" + otherUser.getLastName().charAt(0));
                    chatData.setOtherUserMobile(otherUser.getMobile());
                    chatData.setOtherUserName(otherUser.getFirstName() + " " + otherUser.getLastName());
                    chatData.setOtherUserStatus(otherUser.getUserStatus().getId());
                    List<Chat> chatList = getChatList.list();

                    if (chatList.isEmpty()) {
                        //no chat
                        chatData.setChatStatus(1);
                        chatData.setMessage("Let's Start New Conversation");

                    } else {
                        // chat found
                        chatData.setChatStatus(chatList.get(0).getStatus().getId());
                        chatData.setMessage(chatList.get(0).getMessage());

                    }

                    chatDataList.add(chatData);

                }

                allChatDataDTO.setStatus(true);
                allChatDataDTO.setMessage("sucess");
                allChatDataDTO.setChatDataList(chatDataList);

            session.beginTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(allChatDataDTO));

    }

}
