package com.github.mrrigby.trueinvoices.infrastructure.repository.mapper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * A base class for any hibernate-based repository. This implementation favours the approach to
 * use <code>SessionFactory.getCurrentSession()</code> method to acquire the session and
 * perform any datastore operations.
 *
 * @author MrRigby
 */
public abstract class HibernateRepository {

    private SessionFactory sessionFactory;

    public HibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session session() {
        return sessionFactory.getCurrentSession();
    }
}
