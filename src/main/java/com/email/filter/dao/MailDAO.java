package com.email.filter.dao;


import com.email.filter.model.Email;
import com.email.filter.request.MailRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ME.
 */

@Repository
public class MailDAO extends AbstractDAO {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @PersistenceContext(unitName = "emailer")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public List<Email> getEMails(int start, int limit, MailRequest srchRequest) throws ParseException {
        StringBuilder q = new StringBuilder();
        q.append("Select e From ").append(Email.class.getSimpleName()).append(" e Where 1=1 ");

        if (srchRequest.getId() != null && srchRequest.getId() > 0) {
            q.append(" and e.id ='").append(srchRequest.getId()).append("'");
        }
        if (srchRequest.getFolderId() != null && srchRequest.getFolderId() > 0) {
            q.append(" and e.folder.id ='").append(srchRequest.getFolderId()).append("'");
        }
        if (srchRequest.getFrom() != null) {
            q.append(" and e.from like '%").append(srchRequest.getFrom()).append("%'");
        }
        if (srchRequest.getTo() != null) {
            q.append(" and e.to like '%").append(srchRequest.getTo()).append("%'");
        }
        if (srchRequest.getSubject() != null) {
            q.append(" and e.subject like '%").append(srchRequest.getSubject()).append("%'");
        }
        if (srchRequest.getContent() != null) {
            q.append(" and e.content like '%").append(srchRequest.getContent()).append("%'");
        }
        if (srchRequest.getSendDate() != null && srchRequest.getSendDateTo() != null) {
            q.append(" and e.sendDate between '").append(new Timestamp(sdf.parse(srchRequest.getSendDate()).getTime()))
                    .append("' and '").append(new Timestamp(sdf.parse(srchRequest.getSendDateTo()).getTime())).append("'");
        }
        if (srchRequest.getReceiveDate() != null && srchRequest.getReceiveDateTo() != null) {
            q.append(" and e.receiveDate between '").append(srchRequest.getReceiveDate())
                    .append("' and '").append(srchRequest.getReceiveDateTo()).append("'");
        }
        q.append(" and e.user.userId ='").append(srchRequest.getUserId()).append("'");

        TypedQuery<Email> query = entityManager.createQuery(q.toString() + " order by e.id desc", Email.class);
        return query.setFirstResult(start).setMaxResults(limit).getResultList();
    }
}
