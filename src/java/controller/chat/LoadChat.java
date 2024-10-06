/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.chat;

import com.google.gson.Gson;
import dto.response.Message;
import entity.Chat;
import entity.Status;
import entity.User;
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
@WebServlet(name = "LoadChat", urlPatterns = {"/LoadChat"})
public class LoadChat extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logUserId = request.getParameter("userId");
        String otherUserId = request.getParameter("otherUserId");

        Gson gson = new Gson();

        try {

            Session session = HibernateUtil.getSessionFactory().openSession();

            //get User
            User logUser = (User) session.get(User.class, Integer.parseInt(logUserId));

            //get other User
            User otherUser = (User) session.get(User.class, Integer.parseInt(logUserId));

            // get chats
            Criteria getChats = session.createCriteria(Chat.class);
            getChats.add(Restrictions.or(
                    Restrictions.and(Restrictions.eq("fromUser", logUser), Restrictions.eq("toUser", otherUser)),
                    Restrictions.and(Restrictions.eq("fromUser", otherUser), Restrictions.eq("toUser", logUser))
            ));

            getChats.addOrder(Order.asc("dateTime"));

            List<Chat> chatList = getChats.list();

            //get status
            Status status = (Status) session.get(Status.class, 1);

            List<Message> messages = new ArrayList<>();

            for (Chat chat : chatList) {

                if (chat.getFromUser().getId() == Integer.parseInt(otherUserId)) {
                    if (chat.getStatus().getId() == 2) {
                        chat.setStatus(status);
                        session.update(chat);
                    }
                }
                

                Message message = new Message();
                message.setChatId(chat.getId());
                message.setDateTime(chat.getDateTime());
                message.setMessage(chat.getMessage());
                message.setStatus(chat.getStatus());

                if (chat.getFromUser().getId() == logUser.getId()) {
                    message.setSide("Right");
                } else {
                    message.setSide("left");
                }

                messages.add(message);
                
                

            }
            
            session.getTransaction().commit();

            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(messages));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
