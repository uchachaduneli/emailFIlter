package com.email.filter.dao;

import com.email.filter.dto.UsersDTO;
import com.email.filter.model.Users;
import com.email.filter.utils.MD5Provider;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by ME.
 */

@Repository
public class UserDAO extends AbstractDAO {

	@PersistenceContext(unitName = "emailer")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Transactional(rollbackFor = Throwable.class)
	public Users login(String username, String password) throws Exception {
		try {

			String q = "Select e From " + Users.class.getSimpleName() + " e Where e.userName ='" + username + "'  and (e.userPassword = '"
					+ MD5Provider.doubleMd5(password) + "' or " + " e.userPassword = '" + password + "')";

			TypedQuery<Users> query = entityManager.createQuery(q, Users.class);
			Users usr = query.getSingleResult();
			if (usr.getDeleted() == UsersDTO.DELETED) throw new Exception("Your Account Is Disabled, Contact Administrator");
			if (usr.getTempPassword() != null) {// remove One Time Password after using
				usr.setTempPassword(null);
				usr = (Users) this.update(usr);
			}
			return usr;
		} catch (NoResultException | IndexOutOfBoundsException ex) {
			throw new Exception("User Not Found, Check Credentials");
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Users> getUsersByTypeId(Integer typeId, Integer userId) {
		String qr = "Select e From " + Users.class.getSimpleName() + " e Where ";
		if (typeId == UsersDTO.ADMIN) {
			qr += " e.type.userTypeId ='" + UsersDTO.OPERATOR + "' or e.userId ='" + userId + "'";
		} else {
			qr += " e.type.userTypeId ='" + typeId + "'";
		}
		TypedQuery<Users> query = entityManager.createQuery(qr, Users.class);
		List<Users> res = query.getResultList();
		return res;
	}
}
