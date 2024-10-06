/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

import com.google.gson.Gson;
import dto.request.SignInDTO;
import dto.response.ResponseDTO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "SignIn", urlPatterns = {"/SignIn"})
public class SignIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        SignInDTO signInDTO = gson.fromJson(request.getReader(), SignInDTO.class);
        ResponseDTO<User> responseDTO = new ResponseDTO<>();

        try {

            Session session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("mobile", signInDTO.getMobile()));
            criteria.add(Restrictions.eq("password", signInDTO.getPassword()));

            if (!criteria.list().isEmpty()) {

                User user = (User) criteria.list().get(0);
                responseDTO.setStatus(true);
                responseDTO.setMessage("sucess");
                responseDTO.setData(user);

            } else {
                responseDTO.setMessage("Invalid details! please try again");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseDTO));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {

            String mobile = request.getParameter("mobile");
            Session session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("mobile", mobile));
            if (criteria.list().isEmpty()) {
                responseDTO.setMessage("Invalid details! please create account");
            } else {
                User user = (User) criteria.uniqueResult();
                responseDTO.setStatus(true);
                responseDTO.setMessage("sucess");
                String name = String.valueOf(user.getFirstName().charAt(0))+String.valueOf(user.getLastName().charAt(0));
                responseDTO.setData(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseDTO));
    }

}
