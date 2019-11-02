package com.email.filter.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractDAO<T> {

    public abstract EntityManager getEntityManager();

    public <T> T create(T object) {
        getEntityManager().persist(object);
        return object;
    }

    public <T> T update(T object) {
        return getEntityManager().merge(object);
    }

    public <T> void delete(T object) {
        getEntityManager().remove(object);
    }

    public <T> T find(Class object, Object identifier) {
        return (T) getEntityManager().find(object, identifier);
    }

    public <T> List<T> getAll(Class<T> clazz) {
        TypedQuery<T> query = getEntityManager().createQuery("Select a from " + clazz.getSimpleName() + " a", clazz);
        return query.getResultList();
    }

    public <T> List<T> getAllByParamValue(Class<T> clazz, List<ParamValuePair> paramValues, List<ParamValuePair> orderValues) {

        StringBuilder q = new StringBuilder();
        q.append("Select tbl From ").append(clazz.getSimpleName()).append("  tbl Where 1 = 1 ");

        // build dynamic query
        for (ParamValuePair pair : paramValues) {
            q.append(" and  tbl.").append(pair.getParamName()).append("  = :").append(pair.getParamName());
        }

        // build dynamic order clause
        if (orderValues != null && !orderValues.isEmpty()) {
            q.append(" order by ");

            for (int i = 0; i < orderValues.size(); i++) {
                q.append(" tbl.").append(orderValues.get(i).getParamName()).append(" ").append(orderValues.get(i).getParamValue().toString());
                if (i != orderValues.size() - 1) {
                    q.append(", ");
                }
            }
        }

        TypedQuery<T> query = getEntityManager().createQuery(q.toString(), clazz);

        // set dynamic query values
        for (ParamValuePair pair : paramValues) {
            query.setParameter(pair.getParamName(), pair.getParamValue());
        }

        return query.getResultList();
    }

}

