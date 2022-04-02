package com.ecneb.Hibernate.daos;

import com.ecneb.Hibernate.utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class AbstractDAO<T,ID extends Serializable> implements DAO<T,ID> {

    private Class<T> persistentClass;
    private Session session;

    public AbstractDAO(){
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void setSession(Session session){
        this.session = session;
    }

    protected Session getSession(){
        if(this.session == null){
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        }
        return this.session;
    }

    public Class<T> getPersistentClass(){
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criterion... criterion){
        Criteria crit = this.getSession().createCriteria(this.getPersistentClass());

        for(Criterion c: criterion){
            crit.add(c);
        }
        return (List<T>) crit.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(ID id) {
        return (T) getSession().load(this.getPersistentClass(), id);
    }

    @Override
    public List<T> findAll() {
        return this.findByCriteria();
    }

    @Override
    public T save(T entity) {
        this.getSession().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        this.getSession().delete(entity);
    }

    @Override
    public void flush() {
        this.getSession().flush();
    }

    @Override
    public void clear() {
        this.getSession().clear();
    }
}
