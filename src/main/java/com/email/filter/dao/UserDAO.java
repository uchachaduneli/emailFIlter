package com.email.filter.dao;


import com.email.filter.dto.UsersDTO;
import com.email.filter.model.Users;
import org.springframework.stereotype.Repository;

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

    public Users login(String username, String password) throws Exception {
        try {

            StringBuilder q = new StringBuilder();
            q.append("Select e From ").append(Users.class.getSimpleName())
                    .append(" e Where e.userName ='").append(username).append("'")
                    .append(" and e.userPassword ='").append(password).append("'");

            TypedQuery<Users> query = entityManager.createQuery(q.toString(), Users.class);
            List<Users> res = query.getResultList();
            if (res.get(0).getDeleted() == UsersDTO.DELETED)
                throw new Exception("Your Account Is Disabled, Contact Administrator");
            return res.get(0);
        } catch (NoResultException | IndexOutOfBoundsException ex) {
            throw new Exception("User Not Found, Check Credentials");
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Users> getUsersByTypeId(Integer stageId) {
        StringBuilder q = new StringBuilder();
        q.append("Select e From ").append(Users.class.getSimpleName())
                .append(" e Where e.type.userTypeId ='").append(stageId).append("'");
        TypedQuery<Users> query = entityManager.createQuery(q.toString(), Users.class);
        List<Users> res = query.getResultList();
        return res;
    }
}
