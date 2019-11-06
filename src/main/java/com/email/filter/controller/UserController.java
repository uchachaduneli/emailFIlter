package com.email.filter.controller;

import com.email.filter.dto.UsersDTO;
import com.email.filter.misc.Response;
import com.email.filter.request.AddUserRequest;
import com.email.filter.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author
 */
@RequestMapping("/users")
@Controller
public class UserController {

    @Autowired
    private UsersService userService;

    @RequestMapping("/get-users")
    @ResponseBody
    private Response getUsers() throws Exception {
        return Response.withSuccess(userService.getUsers());
    }

    @RequestMapping("/get-user-types")
    @ResponseBody
    private Response getUserTypes() throws Exception {
        return Response.withSuccess(userService.getUserTypes());
    }

    @RequestMapping({"/save-user"})
    @ResponseBody
    public Response saveUser(@RequestBody AddUserRequest request) throws Exception {
        try {
            return Response.withSuccess(UsersDTO.parse(userService.saveUser(request)));
        } catch (DataIntegrityViolationException e) {
            return Response.withError("Username Already Used, Please Try Another One");
        }
    }

    @RequestMapping({"/change-password"})
    @ResponseBody
    public Response saveUser(HttpServletRequest servletRequest, @RequestParam String pass, @RequestParam String newpass) throws Exception {
        Integer userId = (Integer) servletRequest.getSession().getAttribute("userId");
        if (userId != null) {
            return Response.withSuccess(userService.changePassword(userId, pass, newpass));
        } else {
            return Response.withError("Please Login To For Changing Password");
        }
    }

    @RequestMapping({"/delete-user"})
    @ResponseBody
    public Response deleteUser(@RequestParam int id) {
        userService.delete(id);
        return Response.withSuccess(true);
    }

}
