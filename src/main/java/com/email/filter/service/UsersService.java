package com.email.filter.service;

import com.email.filter.dao.ParamValuePair;
import com.email.filter.dao.UserDAO;
import com.email.filter.dto.UsersDTO;
import com.email.filter.dto.UsersTypeDTO;
import com.email.filter.model.UserTypes;
import com.email.filter.model.Users;
import com.email.filter.request.AddUserRequest;
import com.email.filter.utils.MD5Provider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
@Service
public class UsersService {
	Logger logger = Logger.getLogger(UsersService.class);

	@Autowired
	private UserDAO userDAO;

	public List<UsersDTO> getUsers(Integer typeId, Integer userId) {
		if (typeId != UsersDTO.SUPER_ADMIN) {
			return UsersDTO.parseToList(userDAO.getUsersByTypeId(typeId, userId));
		} else {
			return UsersDTO.parseToList(userDAO.getAll(Users.class));
		}
	}

	public Users getUserByUserName(String userName) {
		List<ParamValuePair> paramValues = new ArrayList<>();
		paramValues.add(new ParamValuePair("userName", userName));
		List<Users> res = userDAO.getAllByParamValue(Users.class, paramValues, null);
		if (res.isEmpty()) {
			return null;
		} else {
			return res.get(0);
		}
	}

	@Transactional(rollbackFor = Throwable.class)
	public Users restorePassword(String oneTimePass, String newpass) throws Exception {
		List<ParamValuePair> paramValues = new ArrayList<>();
		paramValues.add(new ParamValuePair("tempPassword", oneTimePass));
		List<Users> res = userDAO.getAllByParamValue(Users.class, paramValues, null);
		if (res.isEmpty()) {
			throw new Exception("Wrong One Time Password");
		} else {
			Users usr = res.get(0);
			usr.setUserPassword(MD5Provider.doubleMd5(newpass));
			return (Users) userDAO.update(usr);
		}
	}

	@Transactional(rollbackFor = Throwable.class)
	public Users saveUser(AddUserRequest request) throws Exception {

		Users user = new Users();

		user.setUserDesc(request.getUserDesc());
		user.setUserName(request.getUserName());
		if (request.getUserId() == null) {
			user.setUserPassword(MD5Provider.doubleMd5(request.getUserPassword()));
		}
		user.setType((UserTypes) userDAO.find(UserTypes.class, request.getTypeId() == null ? UsersDTO.OPERATOR : request.getTypeId()));
		user.setDeleted(request.getDeleted());
		user.setEmail(request.getEmail());
		user.setEmailPassword(request.getEmailPassword());

		if (request.getUserId() != null) {
			user.setUserId(request.getUserId());
			Users tmp = (Users) userDAO.find(Users.class, request.getUserId());
			if (!request.getUserPassword().equals(tmp.getUserPassword())) {
				user.setUserPassword(MD5Provider.doubleMd5(request.getUserPassword()));
			} else {
				user.setUserPassword(request.getUserPassword());
			}
			user = (Users) userDAO.update(user);
		} else {
			user = (Users) userDAO.create(user);
		}
		return user;
	}

	@Transactional(rollbackFor = Throwable.class)
	public UsersDTO changePassword(Integer userId, String pass, String newpass) throws IOException {

		Users user = userDAO.getEntityManager().find(Users.class, userId);
		if (user.getUserPassword().equals(MD5Provider.doubleMd5(pass))) {

			if (user.getUserId() != null) {
				user.setUserPassword(MD5Provider.doubleMd5(newpass));
				user = (Users) userDAO.update(user);
			}

			return UsersDTO.parse(user);

		} else {
			return null;
		}
	}

	@Transactional(rollbackFor = Throwable.class)
	public void saveUserModel(Users user) {
		userDAO.update(user);
	}

	@Transactional(rollbackFor = Throwable.class)
	public void delete(int id) {
		Users user = (Users) userDAO.find(Users.class, id);
		if (user != null) {
			userDAO.delete(user);
		}
	}

	public UsersDTO login(String username, String password) throws Exception {
		return UsersDTO.parse(userDAO.login(username, password));
	}

	public List<UsersTypeDTO> getUserTypes() {
		return UsersTypeDTO.parseToList(userDAO.getAll(UserTypes.class));
	}
}
