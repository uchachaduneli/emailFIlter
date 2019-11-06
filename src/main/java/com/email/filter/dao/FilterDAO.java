package com.email.filter.dao;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by ME.
 */

@Repository
public class FilterDAO extends AbstractDAO {

    @PersistenceContext(unitName = "emailer")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
