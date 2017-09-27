package org.art.web.command.impl;

import org.art.entities.User;
import org.art.services.UserService;
import org.art.services.exceptions.ServiceBusinessException;
import org.art.services.exceptions.ServiceSystemException;
import org.art.services.impl.UserServiceImpl;
import org.art.web.command.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class RatingController implements Controller {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = req.getSession();
        try {
            List<User> userList = userService.getTopUsers(10);
            session.setAttribute("topList", userList);
        } catch (ServiceBusinessException e) {
            req.setAttribute("errorMsg", "Can't find any users in the application!");
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
            return;
        } catch (ServiceSystemException e) {
            req.setAttribute("errorMsg", SERVER_ERROR_MESSAGE);
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
            return;
        }
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
        return;
    }
}