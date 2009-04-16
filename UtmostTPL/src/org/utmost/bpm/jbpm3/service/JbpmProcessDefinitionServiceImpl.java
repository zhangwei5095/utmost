package org.utmost.bpm.jbpm3.service;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.JbpmException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * 流程定义服务 User: xinpeng Date: 2008-10-20 Time: 22:09:59
 */
public class JbpmProcessDefinitionServiceImpl {
	private static Log log = LogFactory
			.getLog(JbpmProcessDefinitionServiceImpl.class);
	// private JbpmBaseJdbcDAO jbpmBaseJdbcDAO;
	//
	// public JbpmProcessDefinitionServiceImpl() {
	// jbpmBaseJdbcDAO = new JbpmBaseJdbcDAO();
	// }

	/**
	 * findAllProcessDefinitions获取所有的流程定义
	 */
	private static final String JBPMSIDE_QUERY_ALL_PROCESSDEFINITIONS = "GraphSession.findAllProcessDefinitions";

	/**
	 * 获取所有版本流程定义列表
	 * 
	 * @return 流程定义的列表
	 */
	// public List<ProcessDefinition> getAllLatestProcessDefinitionLists(Page
	// page) {
	// return (List) jbpmBaseJdbcDAO.findByOutQuery(
	// JBPMSIDE_QUERY_ALL_PROCESSDEFINITIONS, null, page);
	// }

	/**
	 * 根据page获取版本最新的流程定义列表
	 * 
	 * @param page
	 *            分页
	 * @return 流程定义列表
	 */
//	public List<ProcessDefinition> getLatestProcessDefinitionListsByPage(
//			Page page) {
//		List<ProcessDefinition> processDefinitions = new ArrayList<ProcessDefinition>();
//		Map<String, ProcessDefinition> processDefinitionsByName = new HashMap<String, ProcessDefinition>();
//		try {
//			Session hibernateSession = jbpmBaseJdbcDAO.getSession();
//			Query query = hibernateSession
//					.getNamedQuery(JBPMSIDE_QUERY_ALL_PROCESSDEFINITIONS);
//			List list = query.list();
//			for (Object obj : list) {
//				if (obj instanceof ProcessDefinition) {
//					ProcessDefinition processDefinition = (ProcessDefinition) obj;
//					String processDefinitionName = processDefinition.getName();
//					ProcessDefinition previous = processDefinitionsByName
//							.get(processDefinitionName);
//					if ((previous == null)
//							|| (previous.getVersion() < processDefinition
//									.getVersion())) {
//						processDefinitionsByName.put(processDefinitionName,
//								processDefinition);
//					}
//				}
//				if (page == null)
//					throw new NullPointerException(
//							"page is null in class=[JbpmProcessDefinitionServiceImpl]"
//									+ "method=[getLatestProcessDefinitionListsByPage]");
//				processDefinitions = new ArrayList<ProcessDefinition>(
//						processDefinitionsByName.values());
//
//				int pageSize = page.getPageSize();// 每页显示的条数
//				log.debug("the pageSize is :" + pageSize);
//				int fromIndex = page.getFromIndex();// 从第多少条记录开始查询
//				log.debug("the fromIndex is :" + fromIndex);
//				int toIndex = fromIndex + pageSize; //
//				log.debug("the toIndex is :" + toIndex);
//				int length = processDefinitions.size(); // 原list的大小
//				toIndex = toIndex >= length ? length : toIndex; // 子list的结尾
//				fromIndex = fromIndex > toIndex ? toIndex : fromIndex;
//				processDefinitions = processDefinitions.subList(fromIndex,
//						toIndex); // 分页以后的list
//			}
//		} catch (Exception e) {
//			if (log.isDebugEnabled())
//				e.printStackTrace();
//			else
//				log.error(
//						"couldn't find latest versions of process definitions",
//						e);
//			throw new JbpmException(
//					"couldn't find latest versions of process definitions", e);
//		}
//		return processDefinitions;
//	}
}
