package com.francis.facebook.controller;

import com.francis.facebook.data_access_object.UserDAO;
import com.francis.facebook.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "FrontEndServlet", value = "/front")
public class FrontEndServlet extends HttpServlet {
    UserDAO userDAO = null;

    public FrontEndServlet() {
        this.userDAO = new UserDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        page.toLowerCase();
        switch (page) {
            case "register":
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;
            case "login":
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "destroy":
                request.getSession().invalidate();
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        switch (page) {
            case "register":
                register(request, response);
                break;
            case "login":
                login(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath()+"/front?page=login");
                break;
        }
    }
    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isError = false;
        String firsName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");

        boolean status = validateRegistration(request,firsName, lastName, email, password, gender);

        if (!status) {
            User user = new User(firsName, lastName, email, password,gender);
            userDAO.register(user);
            User userDetails = userDAO.login(user.getEmail(), user.getPassword());
            if (userDetails == null) {

            } else {
                addSession(userDetails, request);
                response.sendRedirect(request.getContextPath()+"/dashboard");
            }

        } else {
            request.setAttribute("page", "register");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean status = validateLogin(request, email, password);

        if(!status) {
            User user = userDAO.login(email, password);

            if (user == null) {

            } else {
                addSession(user, request);
                response.sendRedirect(request.getContextPath()+"/dashboard");
            }
        } else {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    public void addSession(User user,HttpServletRequest request) {
        request.getSession().invalidate();
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(10*60);
        session.setAttribute("user_session", user);
    }

    public boolean validateRegistration(HttpServletRequest request, String firstName, String lastName, String email, String password, String gender) {
        boolean isError = false;
        request.removeAttribute("firstName");
        request.removeAttribute("lastName");
        request.removeAttribute("email");
        request.removeAttribute("password");
        request.removeAttribute("gender");
        if (firstName == null || firstName == "") {
            request.setAttribute("firstName", "First name field is required!");
            isError = true;
        } else {
            request.setAttribute("first_name", request.getParameter("first_name"));
        }

        if (lastName == null || lastName == "") {
            request.setAttribute("lastName", "Last name field is required!");
            isError = true;
        } else {
            request.setAttribute("last_name", request.getParameter("last_name"));

        }
        isError = isError(request, email, password, isError);
        if (gender == null) {
            request.setAttribute("gender", "Gender field is required!");
            isError = true;
        }
        return isError;

    }
    public boolean validateLogin(HttpServletRequest request, String email, String password) {
        boolean isError = false;
        isError = isError(request, email, password, isError);

        return isError;
    }
    private boolean isError(HttpServletRequest request, String email, String password, boolean isError) {
        if (email == null || email == "") {
            request.setAttribute("email", "Email field is required!");
            isError = true;
        } else {
            request.setAttribute("email1", request.getParameter("email"));
        }
        if (password == null || password == "") {
            request.setAttribute("password", "Password field is required!");
            isError = true;
        }
        return isError;
    }

}
