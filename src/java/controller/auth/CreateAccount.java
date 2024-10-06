/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

import com.google.gson.Gson;
import dto.request.UserRequestDTO;
import dto.response.ResponseDTO;
import entity.Type;
import entity.User;
import entity.UserStatus;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

/**
 *
 * @author yasithsandesh
 */
@WebServlet(name = "CreateAccount", urlPatterns = {"/CreateAccount"})
public class CreateAccount extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        ResponseDTO responseDTO = new ResponseDTO();
        UserRequestDTO userRequestDTO = gson.fromJson(request.getReader(), UserRequestDTO.class);
        try {

            if (userRequestDTO.getFirstName().isEmpty()) {
                responseDTO.setMessage("Please type your First Name");
            } else if (userRequestDTO.getLastName().isEmpty()) {
                responseDTO.setMessage("Please type your Last Name");
            } else if (userRequestDTO.getEmail().isEmpty()) {
                responseDTO.setMessage("Please type your email");
            } else if (userRequestDTO.getMobile().isEmpty()) {
                responseDTO.setMessage("Please type your mobile");
            } else if (userRequestDTO.getPassword().isEmpty()) {
                responseDTO.setMessage("Please type your password");
            } else if (userRequestDTO.getTypeId().isEmpty()) {
                responseDTO.setMessage("Please select job title");
            } else {

                Session session = HibernateUtil.getSessionFactory().openSession();
                Criteria criteria = session.createCriteria(User.class);
                criteria.add(Restrictions.eq("email", userRequestDTO.getEmail()));
                criteria.add(Restrictions.eq("mobile", userRequestDTO.getMobile()));
                
                if (!criteria.list().isEmpty()) {
                    responseDTO.setMessage("User with this email already exists");
                } else {

                    Type jobType = (Type) session.get(Type.class, Integer.parseInt(userRequestDTO.getTypeId()));

                    UserStatus status = (UserStatus) session.get(UserStatus.class, 1);

                    User user = new User();
                    user.setEmail(userRequestDTO.getEmail());
                    user.setFirstName(userRequestDTO.getFirstName());
                    user.setLastName(userRequestDTO.getLastName());
                    user.setMobile(userRequestDTO.getMobile());
                    user.setPassword(userRequestDTO.getPassword());
                    user.setRegisteredDate(new Date());
                    user.setType(jobType);
                    user.setUserStatus(status);

                    session.save(user);
                    session.beginTransaction().commit();

                    session.close();

                    responseDTO.setStatus(true);
                    responseDTO.setMessage("sucess");

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseDTO));
    }

}
