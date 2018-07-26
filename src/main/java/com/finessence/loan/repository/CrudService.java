
package com.finessence.loan.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;

/**
 * Definition of crud service utilities.
 *
 * @author kenny
 */

public interface CrudService {

    /**
     * Default query results page when none is specified.
     */
    public final static int DEFAULT_PAGE_SIZE = 5000;

    /**
     * Retrieve an entity using its primary key.
     *
     * @param <T> Entity type
     * @param primaryKey primary id value
     * @param clazz entity class.
     * @return object instance if found, null if none-found.
     */
    public <T> T findEntity(Serializable primaryKey, Class<T> clazz) throws HibernateException;

    /**
     * Find all results of executing a given named query with given params. Each
     * parameter is substituted in the query.
     *
     * @param <T> Actual return type.
     * @param queryName Named query defined on the entity.
     * @param params map pair of param name which is simply the field name of
     * the entity and a search value. The param map maybe empty.
     * @return List of results or empty list if none is found.
     */
    public <T> List<T> fetchWithNamedQuery(String queryName, Map<String, Object> params) throws HibernateException;

      /**
     * Execute hibernate query.
     *
     * @param queryString Named query defined on the entity.
     * @param params map pair of param name which is simply the field name of
     * the entity and a search value. The param map maybe empty.
     * @return List of results or empty list if none is found.
     */
    public int executeHibernateQuery(String queryString, Map<String, Object> params) throws HibernateException;

    /**
     * Find all results of executing a given hibernate query with given params.
     * Each parameter is substituted in the query.
     *
     * @param <T> Actual return type.
     * @param query Hibernate query constructed to be substituted with the
     * actual params below.
     * @param params map pair of param name which is simply the field name of
     * the entity and a search value. The param map maybe empty.
     * @return List of results or empty list if none is found.
     */
    public <T> List<T> fetchWithHibernateQuery(String query, Map<String, Object> params) throws HibernateException;

     /**
     * Find all results of executing a given hibernate query with given params.
     * Each parameter is substituted in the query.
     *
     * @param <T> Actual return type.
     * @param query Hibernate query constructed to be substituted with the
     * actual params below.
     * @param start
     * @param end
     * @param params map pair of param name which is simply the field name of
     * the entity and a search value. The param map maybe empty.
     * @return List of results or empty list if none is found.
     */
    public <T> List<T> fetchWithNativeQuery(String query, int start, int end) throws HibernateException ;
    /**
     * Find paged results of executing a given named query with given params.
     * Each parameter is substituted in the query.
     *
     * @param <T> Actual return type.
     * @param query Hibernate query constructed to be substituted with the
     * actual params below.
     * @param params pair of param name which is simply the field name of the
     * entity and a search value.
     * @param start beginning position, default is 0. First element
     * @param end upto and inclusive
     * @return List of results or empty list if none is found.
     */
    public <T> List<T> fetchWithNamedQuery(String query, Map<String, Object> params, int start, int end) throws HibernateException;

    /**
     * Find paged results of executing a given hibernate query with given
     * params. Each parameter is substituted in the query.
     *
     * @param <T> Actual return type.
     * @param query Named query defined on the entity.
     * @param params pair of param name which is simply the field name of the
     * entity and a search value.
     * @param start beginning position, default is 0. First element
     * @param end upto and inclusive
     * @return List of results or empty list if none is found.
     */
    public <T> List<T> fetchWithHibernateQuery(String query, Map<String, Object> params, int start, int end) throws HibernateException;

    /**
     * Persists or updates an object to the data store.
     *
     * @param <T> Object type.
     * @param entity value to be added or updated.
     * @throws HibernateException
     */
    public <T> void saveOrUpdate(T entity) throws HibernateException;

    /**
     * Persists or updates an object to the data store.
     *
     * @param <T> Object type.
     * @param entity value to be added or updated.
     * @throws HibernateException
     */
    public <T> Object save(T entity) throws HibernateException;

    
    /**
     * Remove an entity permanently from database.
     *
     * @param <T> object type
     * @param entity value to remove
     * @throws HibernateException in case errors occur
     */
    public <T> void remove(T entity) throws HibernateException;

}
