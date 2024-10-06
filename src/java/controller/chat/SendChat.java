/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.chat;

import dto.response.ResponseDTO;
import entity.Chat;
import entity.Status;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author yasithsandesh
 */
@WebServlet(name = "SendChat", urlPatterns = {"/SendChat"})
public class SendChat extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logUserId = request.getParameter("logUserId");
        String otherUserId = request.getParameter("otherUserId");
        String message = request.getParameter("message");

        ResponseDTO responseDTO = new ResponseDTO();

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            // get logUser
            User logUser = (User) session.get(User.class, Integer.parseInt(logUserId));

            // other User
            User otherUser = (User) session.get(User.class, Integer.parseInt(otherUserId));

            // status
            Status status = (Status) session.get(Status.class, 2);

            // create new chat
            Chat chat = new Chat();
            chat.setDateTime(new Date());
            chat.setFromUser(logUser);
            chat.setToUser(logUser);
            chat.setStatus(status);

            session.save(chat);

            session.beginTransaction().commit();

            responseDTO.setMessage("sucess");
            responseDTO.setStatus(true);

        } catch (Exception e) {
            responseDTO.setMessage("error");
        }
    }

}
