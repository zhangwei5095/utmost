package org.utmost.portal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Service;
import org.utmost.common.CommService;

/**
 * 数据访问核心服务类 所有CURD及其它数据库操作都经由此类提供服务
 * 
 * @author wanglm
 * 
 */
@Service("AutoService")
public class AutoService extends CommService {
	private static Log logger = LogFactory.getLog(AutoService.class);

	/**
	 * 数据类型转换(string,long,double)
	 * 
	 * @param entity
	 * @return
	 */
	public HashMap<String, Object> transformType(String entityName,
			HashMap<String, Object> entity) {
		ClassMetadata cm = getDb().getSessionFactory().getClassMetadata(
				entityName);
		Iterator<Map.Entry<String, Object>> it = entity.entrySet().iterator();
		HashMap<String, Object> newentity = new HashMap<String, Object>();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Entry<String, Object>) it.next();
			String key = entry.getKey();
			Object ovalue = entry.getValue();
			String value = (ovalue == null ? "" : ovalue.toString());
			String type = "";
			try {
				type = cm.getPropertyType(key).getName();
			} catch (Exception ex) {
				logger.info("could not resolve property: " + key + " of "
						+ entityName);
			}
			System.out.println(key + "->" + value + "-->" + type);
			if (type.equals("long")) {
				newentity.put(key, Long.parseLong(value));
			} else if (type.equals("double")) {
				newentity.put(key, Double.parseDouble(value));
			} else {
				newentity.put(key, value);
			}
		}
		return newentity;
	}

	/**
	 * 保存
	 * 
	 * @param entityName
	 * @param entity
	 */
	public void save(String entityName, HashMap<String, Object> entity) {
		getDb().save(entityName, transformType(entityName, entity));
	}

	/**
	 * 外部保存 不受Spring事物管制
	 * 
	 * @param entityName
	 * @param entity
	 */
	public void exteriorSave(String entityName, HashMap<String, Object> entity) {
		getDb().exteriorSave(entityName, transformType(entityName, entity));
	}

	/**
	 * 保存所有
	 * 
	 * @param entityName
	 * @param list
	 */
	public void saveAll(String entityName, List<HashMap> list) {
		for (HashMap entity : list) {
			this.save(entityName, entity);
		}
	}

	/**
	 * 更新所有
	 * 
	 * @param entityName
	 * @param list
	 */
	public void updateAll(String entityName, List<HashMap> list) {
		for (HashMap entity : list) {
			this.update(entityName, entity);
		}
	}

	/**
	 * 更新
	 * 
	 * @param entityName
	 * @param entity
	 */
	public void update(String entityName, HashMap<String, Object> entity) {
		getDb().update(entityName, transformType(entityName, entity));
	}

	/**
	 * 保存或更新
	 * 
	 * @param entityName
	 * @param entity
	 */
	public void saveOrUpdate(String entityName, HashMap<String, Object> entity) {
		getDb().saveOrUpdate(entityName, transformType(entityName, entity));
	}

	/**
	 * 保存或更新所有
	 * 
	 * @param entityName
	 * @param list
	 */
	public void saveOrUpdateAll(String entityName, List<HashMap> list) {
		for (HashMap entity : list) {
			this.saveOrUpdate(entityName, entity);
		}
	}

	/**
	 * 删除
	 * 
	 * @param entityName
	 * @param entity
	 */
	public void delete(String entityName, HashMap<String, Object> entity) {
		getDb().delete(entityName, entity);
	}

	/**
	 * 删除所有
	 * 
	 * @param entityName
	 * @param entity
	 */
	public void deleteAll(String entityName, List<HashMap> list) {
		for (HashMap entity : list) {
			this.delete(entityName, entity);
		}
	}

	/**
	 * 根据UUID删除
	 * 
	 * @param tableName
	 * @param uuid
	 */
	@SuppressWarnings("unchecked")
	public void deleteByUUID(String tableName, String uuid) {
		List list = this.findByUUID(tableName, uuid);
		for (Object o : list) {
			getDb().delete(o);
		}
	}

	/**
	 * 根据HQL查询 删除返回结果集
	 * 
	 * @param hql
	 */
	@SuppressWarnings("unchecked")
	public void deleteByHql(String hql) {
		getDb().deleteByHql(hql);
	}

	/**
	 * 根据UUID查询
	 * 
	 * @param tableName
	 * @param uuid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findByUUID(String tableName, String uuid) {
		return getDb().findByHql(
				"from " + tableName + " v where v.uuid='" + uuid + "'");
	}

	/**
	 * 根据UUID删除所有
	 * 
	 * @param tableName
	 * @param uuid
	 */
	@SuppressWarnings("unchecked")
	public void deleteByAllUUID(String tableName, ArrayList<String> list) {
		for (String s : list) {
			deleteByUUID(tableName, s);
		}
	}

	/**
	 * 根据HQL查询结果集
	 * 
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findByHql(String hql) {
		return getDb().findByHql(hql);
	}

	/**
	 * 根据表名查询所有
	 * 
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findAll(String tableName) {
		return getDb().findAll(tableName);
	}

	/**
	 * 命名查询
	 * 
	 * @return
	 */
	public List findByNamedQuery(String queryName) {
		return getDb().findByNamedQuery(queryName);
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
	public List pagination(int pageNo, int pageSize, String hql) {
		return getDb().pagination(pageNo, pageSize, hql);
	}
}
