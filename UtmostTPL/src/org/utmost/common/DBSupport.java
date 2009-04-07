package org.utmost.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * 框架数据访问封装
 * 
 * @author wanglm
 * 
 */
@SuppressWarnings("unchecked")
// @Scope("prototype") 非单例
@Repository("DBSupport")
public class DBSupport extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(DBSupport.class);

	@Autowired
	public DBSupport(@Qualifier("SessionFactory") SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	/**
	 * 根据HQL查询返回List
	 * 
	 * @param hql
	 * @return
	 */

	public List findByHql(String hql) {
		log.debug("findByHql List with hql: " + hql);
		try {
			return this.getSession().createQuery(hql).list();
		} catch (RuntimeException re) {
			log.error("findByHql failed", re);
			throw re;
		}
	}

	public Query createSQLQuery(String sql) {
		log.debug("createSQLQuery List with hql: " + sql);
		try {
			return this.getSession().createSQLQuery(sql);
		} catch (RuntimeException re) {
			log.error("createSQLQuery failed", re);
			throw re;
		}
	}

	/**
	 * 根据条件查询
	 * 
	 * @param exampleEntity
	 * @return
	 */
	public List findByExample(Object exampleEntity) {
		return this.getHibernateTemplate().findByExample(exampleEntity);
	}

	/**
	 * 返回第一个对象
	 * 
	 * @param hql
	 * @return
	 */
	public Object findByHqlToObj(String hql) {
		Iterator it = this.findByHql(hql).iterator();
		if (it.hasNext()) {
			return it.next();
		}
		return null;
	}

	/**
	 * 查找所有
	 */
	public List findAll(String tableName) {
		log.debug("findAll List with tableName: " + tableName);
		try {
			return this.getSession().createQuery("from " + tableName).list();
		} catch (RuntimeException re) {
			log.error("findAll failed", re);
			throw re;
		}
	}

	/**
	 * 添加
	 * 
	 * @param entity
	 */
	public void save(Object entity) {
		log.debug("save void with entity: " + entity);
		try {
			this.getHibernateTemplate().save(entity);
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/**
	 * 添加
	 * 
	 * @param entity
	 */
	public void save(String entityName, Map entity) {
		log.debug("save void with entity by entityName: " + entity);
		try {
			this.getHibernateTemplate().save(entityName, entity);
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/**
	 * 外部保存
	 * 
	 * @param entity
	 */
	public void exteriorSave(String entityName, Map entity) {
		log.debug("save void with entity by entityName: " + entity);
		Session session = this.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(entityName, entity);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			log.error("save failed", re);
			throw re;
		} finally {
			session.close();
		}
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 */
	public void update(Object entity) {
		log.debug("update void with entity: " + entity);
		try {
			this.getHibernateTemplate().update(entity);
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 */
	public void update(String entityName, Map entity) {
		log.debug("update void with entity by entityName: " + entity);
		try {
			this.getHibernateTemplate().update(entityName, entity);
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	/**
	 * 添加or修改
	 */
	public void saveOrUpdate(Object entity) {
		log.debug("saveOrUpdate void with entity: " + entity);
		try {
			this.getHibernateTemplate().saveOrUpdate(entity);
		} catch (RuntimeException re) {
			log.error("saveOrUpdate failed", re);
			throw re;
		}
	}

	/**
	 * 添加or修改
	 */
	public void saveOrUpdate(String entityName, Map entity) {
		log.debug("saveOrUpdate void with entitie: " + entity);
		try {
			// uuid 空值处理
			if (entity.containsKey("uuid")) {
				Object obj = entity.get("uuid");
				if (obj == null || obj.toString().equalsIgnoreCase("null")
						|| obj.toString().equals("")) {
					entity.remove("uuid");
				}
			}
			this.getHibernateTemplate().saveOrUpdate(entityName, entity);
		} catch (RuntimeException re) {
			log.error("saveOrUpdate failed", re);
			throw re;
		}
	}

	/**
	 * 添加or修改集合
	 */
	public void saveOrUpdateAll(Collection<Object> entities) {
		log.debug("saveOrUpdateAll void with entities: " + entities);
		try {
			this.getHibernateTemplate().saveOrUpdateAll(entities);
		} catch (RuntimeException re) {
			log.error("saveOrUpdateAll failed", re);
			throw re;
		}
	}

	/**
	 * 删除
	 * 
	 * @param entity
	 */
	public void delete(String entityName, Map entity) {
		log.debug("delete void with entity by entityName: " + entity);
		try {
			this.getHibernateTemplate().delete(entityName, entity);
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * 删除
	 * 
	 * @param entity
	 */
	public void delete(Object entity) {
		log.debug("delete void with entity: " + entity);
		try {
			this.getHibernateTemplate().delete(entity);
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * 根据hql删除
	 * 
	 * @param hql
	 */
	public void deleteByHql(String hql) {
		List list = this.findByHql(hql);
		for (Object o : list) {
			this.delete(o);
		}
	}

	/**
	 * 删除集合
	 * 
	 * @param entities
	 */
	public void deleteAll(Collection<Object> entities) {
		log.debug("deleteAll void with entities: " + entities);
		try {
			this.getHibernateTemplate().deleteAll(entities);
		} catch (RuntimeException re) {
			log.error("deleteAll failed", re);
			throw re;
		}
	}

	/**
	 * 命名查询
	 * 
	 * @return
	 */
	public List findByNamedQuery(String queryName) {
		log.debug("findByNamedQuery void with queryName: " + queryName);
		try {
			return this.getHibernateTemplate().findByNamedQuery(queryName);
		} catch (RuntimeException re) {
			log.error("findByNamedQuery failed", re);
			throw re;
		}
	}

	/** */
	/**
	 * 设置分页, pageNo或pageList<=0时返回所有记录
	 * 
	 * @param pageNo
	 *            页数, 从1开始, <=0时返回所有记录
	 * @param pageList
	 *            每页记录数, <=0时返回所有记录
	 * @param query
	 */
	public void pagination(int pageNo, int pageList, Query query) {
		if (pageList > 0 && pageNo > 0) {
			query.setMaxResults(pageList);
			int beginIndex = (pageNo - 1) * pageList;
			query.setFirstResult(beginIndex);
		}
	}

	/**
	 * 设置分页, pageNo或pageList<=0时返回所有记录
	 * 
	 * @param pageNo
	 *            页数, 从1开始, <=0时返回所有记录
	 * @param pageList
	 *            每页记录数, <=0时返回所有记录
	 * @param hql
	 */
	public List pagination(int pageNo, int pageList, String hql) {
		Query query = this.getSession().createQuery(hql);
		if (pageList > 0 && pageNo > 0) {
			query.setMaxResults(pageList);
			int beginIndex = (pageNo - 1) * pageList;
			query.setFirstResult(beginIndex);
		}
		return query.list();
	}

	/**
	 * 设置分页, pageNo或pageList<=0时返回所有记录
	 * 
	 * @param pageNo
	 *            页数, 从1开始, <=0时返回所有记录
	 * @param pageList
	 *            每页记录数, <=0时返回所有记录
	 * @param criteria
	 */
	public void pagination(int pageNo, int pageList, Criteria criteria) {
		if (pageList > 0 && pageNo > 0) {
			criteria.setMaxResults(pageList);
			int beginIndex = (pageNo - 1) * pageList;
			criteria.setFirstResult(beginIndex);
		}
	}
}