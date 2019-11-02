package com.email.filter.security;

import com.email.filter.dto.UsersDTO;
import com.email.filter.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author
 */
@Controller
@RequestMapping
public class AuthController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(HttpServletRequest request) {
        try {
            Integer loginedUserId = (Integer) request.getSession().getAttribute("userId");
            if (loginedUserId == null) {
                return "login";
            } else {
                return "redirect:emails";
            }
        } catch (Exception ex) {
            return "login";
        }
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String verify(@RequestParam(value = "uri", required = false) String originalUri, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsersDTO foundedUser = usersService.login(username, password);
        if (foundedUser != null) {
            request.getSession().setAttribute("userId", foundedUser.getUserId());
            request.getSession().setAttribute("userDesc", foundedUser.getUserDesc());
            request.getSession().setAttribute("typeId", foundedUser.getType().getUserTypeId());
            request.getSession().setAttribute("typeName", foundedUser.getType().getUserTypeName());
            response.sendRedirect("emails");
            return null;
        } else {
            response.sendError(400, "Incorrect Username Or Password");
            return null;
        }
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(HttpSession session) {
        session.removeAttribute("userId");
        session.removeAttribute("userDesc");
        session.removeAttribute("typeId");
        session.invalidate();
        return "redirect:login";
    }
}
