/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import org.hibernate.HibernateException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericCrudService implements CrudService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    private SessionFactory sessionFactory;

    @PostConstruct
    protected void init() {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    private final static Logger LOG = LoggerFactory.getLogger(GenericCrudService.class.getSimpleName());

    @Override
    public <T> T findEntity(Serializable primaryKey, Class<T> clazz) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            IdentifierLoadAccess loadAccess = session.byId(clazz);
            return (T) loadAccess.load(primaryKey);
        } catch (HibernateException e) {
            LOG.error("Failed to find entity {} by id {}. {}", clazz, primaryKey, e.getMessage());
            throw e;
        }
    }

    @Override
    public <T> List<T> fetchWithNamedQuery(String queryName, Map<String, Object> params) {
        return fetchWithNamedQuery(queryName, params, 0, DEFAULT_PAGE_SIZE);
    }

    @Override
    public <T> List<T> fetchWithHibernateQuery(String query, Map<String, Object> params) throws HibernateException {
        return fetchWithHibernateQuery(query, params, 0, DEFAULT_PAGE_SIZE);
    }

    @Override
    public <T> List<T> fetchWithHibernateQuery(String query, Map<String, Object> params, int start, int end) throws HibernateException {
        LOG.info("Executing Hibernate={}, start={},end={} params=[{}]", query, start, end, params);
        try (Session session = sessionFactory.openSession()) {
            Query q = session.createQuery(query);
            params.entrySet().forEach((param) -> {
                if (param.getValue() instanceof Collection) {
                    q.setParameterList(param.getKey(), (Collection) param.getValue());
                } else {
                    q.setParameter(param.getKey(), param.getValue());
                }
            });
//            start = start < 0 ? 0 : start;
//            if (end >= start && start >= 0) {
//                q.setFirstResult(start);
//                q.setMaxResults(end - start);
//            }
            return q.<T>list();
        } catch (HibernateException e) {
            LOG.error("Failed in executing hibernate query {} with params [{}]. {}.", query, params, e.getMessage());
            throw e;
        }
    }

    /**
     *
     * @param <T>
     * @param query
     * @param start
     * @param end
     * @return
     * @throws HibernateException
     */
    @Override
    public <T> List<T> fetchWithNativeQuery(String query, int start, int end) throws HibernateException {
        LOG.info("Executing Hibernate={}, start={},end={} params=[{}]", query, start, end);
        try (Session session = sessionFactory.openSession()) {
            Query q = session.createSQLQuery(query);

//            start = start < 0 ? 0 : start;
//            if (end >= start && start >= 0) {
//                q.setFirstResult(start);
//                q.setMaxResults(end - start);
//            }
            return q.<T>list();
        } catch (HibernateException e) {
            LOG.error("Failed in executing hibernate query {} with params [{}]. {}.", query, e.getMessage());
            throw e;
        }
    }

    @Override
    public <T> List<T> fetchWithNamedQuery(String queryName, Map<String, Object> params, int start, int end) {
        LOG.debug("Executing NamedQuery={}, start={},end={} params=[{}]", queryName, start, end, params);
        try (Session session = sessionFactory.openSession()) {
            Query query = session.getNamedQuery(queryName);
            params.entrySet().forEach((param) -> {
                if (param.getValue() instanceof Collection) {
                    query.setParameterList(param.getKey(), (Collection) param.getValue());
                } else {
                    query.setParameter(param.getKey(), param.getValue());
                }
            });
            start = start < 0 ? 0 : start;
            if (end >= start && start >= 0) {
                query.setFirstResult(start);
                query.setMaxResults(end - start);
            }
            return query.<T>list();
        } catch (HibernateException e) {
            LOG.error("Failed in executing named query {} with params [{}]. {}.", queryName, params, e.getMessage());
            throw e;
        }
    }

    @Override
    public <T> void saveOrUpdate(T entity) throws HibernateException {
        LOG.debug("Perist or merge {}", entity);
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(entity);
            tx.commit();
        } catch (HibernateException e) {
            LOG.error("Failed to persist or merge {}. {}", entity, e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public <T> Object save(T entity) throws HibernateException {
        LOG.debug("Perist or merge {}", entity);
        Session session = null;
        Session session2 = null;
        Transaction tx = null;
        Object savedEntity = null;
        try {
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            savedEntity = session.save(entity);
            tx.commit();
            savedEntity =  (session.createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult());
      
        } catch (HibernateException e) {
            LOG.error("Failed to persist or merge {}. {}", entity, e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return savedEntity;
    }

    @Override
    public <T> void remove(T entity) throws HibernateException {
        LOG.debug("Deleting {}", entity);
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            session.delete(entity);
            tx.commit();
        } catch (HibernateException e) {
            LOG.error("Failed to delete {}. {}", entity, e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public int executeHibernateQuery(String queryString, Map<String, Object> params) throws HibernateException {
        LOG.info("Executing Hibernate={}, params=[{}]", queryString, params);
        try (Session session = sessionFactory.openSession()) {
            Query q = session.createQuery(queryString);
            params.entrySet().forEach((param) -> {
                if (param.getValue() instanceof Collection) {
                    q.setParameterList(param.getKey(), (Collection) param.getValue());
                } else {
                    q.setParameter(param.getKey(), param.getValue());
                }
            });

            return q.executeUpdate();
        } catch (HibernateException e) {
            LOG.error("Failed in executing hibernate query {} with params [{}]. {}.", queryString, params, e.getMessage());
            throw e;
        }
    }

}
