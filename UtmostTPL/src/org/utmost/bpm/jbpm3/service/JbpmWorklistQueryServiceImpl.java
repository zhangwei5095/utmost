package org.utmost.bpm.jbpm3.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.graph.exe.ProcessInstance;

import java.util.*;

/**
 * 任务实例查询服务 Author: xinpeng Date: 2008-10-21 Time: 11:08:37
 */
public class JbpmWorklistQueryServiceImpl {

	private JbpmProcessInstanceServiceImpl jbpmProcessInstanceService;

	private static final Log log = LogFactory
			.getLog(JbpmWorklistQueryServiceImpl.class);
	/**
	 * 参与者 (查询条件)
	 */
	private static final String JBPMSIDE_QUERY_ACTOR_ID = "actorId";
	/**
	 * 待阅列表sql语句
	 */
	private static final String JBPMSIDE_QUERY_TODO_SIGN_LIST = "TaskMgmtSession.findTodoSignTaskInstancesByActorId";
	/**
	 * 已阅列表sql语句
	 */
	private static final String JBPMSIDE_QUERY_ALREADY_SIGN_LIST = "TaskMgmtSession.findAlreadySignTaskInstancesByActorId";
	/**
	 * 待办列表sql语句
	 */
	private static final String JBPMSIDE_QUERY_TODO_LIST = "TaskMgmtSession.findTaskInstancesByActorId";
	/**
	 * 已办列表sql语句
	 */
	private static final String JBPMSIDE_QUERY_COMPLETED_LIST = "TaskMgmtSession.findCompletedTaskInstancesByActorId";
	/**
	 * 办结列表sql语句
	 */
	private static final String JBPMSIDE_QUERY_PROCESS_ALREADY_COMPLETED_LIST = "TaskMgmtSession.findProcessCompeletedTaskInstancesByActorId";

	/**
	 * 取到待阅列表
	 * 
	 * @param userId
	 *            用户userid
	 * @param page
	 *            分页
	 * @return TaskInstanceDTO的集合
	 */
	public Collection<TaskInstanceDTO> queryTodoSignInListByPage(String userId,
			Page page) {
		String sqlPath = JBPMSIDE_QUERY_TODO_SIGN_LIST;
		return this.queryListBySqlType(sqlPath, userId, page);
	}

	/**
	 * 取到已阅列表
	 * 
	 * @param userId
	 *            用户userid
	 * @param page
	 *            分页
	 * @return TaskInstanceDTO的集合
	 */
	public Collection<TaskInstanceDTO> queryAlreadySignInListByPage(
			String userId, Page page) {
		String sqlPath = JBPMSIDE_QUERY_ALREADY_SIGN_LIST;
		return this.queryListBySqlType(sqlPath, userId, page);
	}

	/**
	 * 取到待办列表
	 * 
	 * @param userId
	 *            用户userid
	 * @param page
	 *            分页
	 * @return TaskInstanceDTO的集合
	 */
	public Collection<TaskInstanceDTO> queryTodoListByPage(String userId,
			Page page) {
		String sqlPath = JBPMSIDE_QUERY_TODO_LIST;
		return this.queryListBySqlType(sqlPath, userId, page);
	}

	/**
	 * 取到已办列表
	 * 
	 * @param userId
	 *            用户userid
	 * @param page
	 *            分页
	 * @return TaskInstanceDTO的集合
	 */
	public Collection<TaskInstanceDTO> queryCompletedListByPage(String userId,
			Page page) {
		String sqlPath = JBPMSIDE_QUERY_COMPLETED_LIST;
		return this.queryListBySqlType(sqlPath, userId, page);
	}

	/**
	 * 取到办结列表
	 * 
	 * @param userId
	 *            用户userid
	 * @param page
	 *            分页
	 * @return TaskInstanceDTO的集合
	 */
	public Collection<TaskInstanceDTO> queryProcessCompletedListByPage(
			String userId, Page page) {
		String sqlPath = JBPMSIDE_QUERY_PROCESS_ALREADY_COMPLETED_LIST;
		return this.queryListBySqlType(sqlPath, userId, page);
	}

	/**
	 * 通过roottokenid获取当前流程的所有任务
	 * 
	 * @param tokenid
	 *            根tokenid
	 * @return TaskInstanceDTO的集合
	 */
	public Collection<TaskInstanceDTO> getDetailTaskListByTokenid(long tokenid) {
		ProcessInstance processInstance = jbpmProcessInstanceService
				.getProcessInstance(tokenid);
		Collection list = processInstance.getTaskMgmtInstance()
				.getTaskInstances();
		return this.converToDTO(list);
	}

	/*
	 * 根据查询的类别进行查询，并将数据dto格式化
	 * 
	 * @param sqlPath sql或者是hql
	 * 
	 * @param userId 用户userid
	 * 
	 * @param page 当前页
	 * 
	 * @return TaskInstanceDTO的集合
	 */
	private Collection<TaskInstanceDTO> queryListBySqlType(String sqlPath,
			String userId, Page page) {
		// JbpmBaseJdbcDAO baseJdbcDao = new JbpmBaseJdbcDAO();
		// Map paraMap = new HashMap();
		// paraMap.put(JBPMSIDE_QUERY_ACTOR_ID, userId);
		// List list = baseJdbcDao.findByOutQuery(sqlPath, paraMap, page);
		// return this.converToDTO(list);
		return null;
	}

	/**
	 * 做TaskInstance对象的集合和TaskInstanceDTO对象的集合DTO转换
	 * 
	 * @param list
	 *            TaskInstance对象的集合
	 * @return TaskInstanceDTO对象的集合
	 */
	private Collection<TaskInstanceDTO> converToDTO(
			Collection<TaskInstance> list) {
		Collection taskDTOlist = new ArrayList();
		try {
			for (Object taskInstance : list) {
				if (taskInstance instanceof TaskInstance) {
					TaskInstanceDTO taskInstanceDTO = new TaskInstanceDTO();
					TaskInstance newtaskInstance = (TaskInstance) taskInstance;
					taskInstanceDTO.setId(newtaskInstance.getId());
					taskInstanceDTO
							.setName(newtaskInstance.getTask().getName());
					taskInstanceDTO.setActorId(newtaskInstance.getActorId());
					Date date = newtaskInstance.getStart();
					String strdate = "";
					strdate = date == null ? strdate : date.toString();
					taskInstanceDTO.setStart(strdate);
					date = newtaskInstance.getCreate();
					strdate = date == null ? strdate : date.toString();
					taskInstanceDTO.setCreate(strdate);
					date = newtaskInstance.getEnd();
					strdate = date == null ? strdate : date.toString();
					taskInstanceDTO.setEnd(strdate);
					taskInstanceDTO.setTokenId(newtaskInstance.getToken()
							.getId());
					taskInstanceDTO.setTaskId(newtaskInstance.getId());
					taskInstanceDTO.setTaskMgmtInstanceId(newtaskInstance
							.getTaskMgmtInstance().getId());
					ProcessInstance processIntance = jbpmProcessInstanceService
							.getProcessInstance(newtaskInstance.getToken()
									.getId());
					taskInstanceDTO.setProcessDefinitionName(processIntance
							.getProcessDefinition().getName());
					taskInstanceDTO.setProcessDefinitionVersion(processIntance
							.getProcessDefinition().getVersion()
							+ "");
					taskDTOlist.add(taskInstanceDTO);
				} else {
					log.error("返回的类型不是【org.jbpm.taskmgmt.exe.TaskInstance】");
				}
			}
		} catch (Exception e) {
			if (log.isDebugEnabled())
				e.printStackTrace();
			else
				log.error("startProcess is failed", e);
			throw new RuntimeException(e);
		}
		return taskDTOlist;
	}

	public void setJbpmProcessInstanceService(
			JbpmProcessInstanceServiceImpl jbpmProcessInstanceService) {
		this.jbpmProcessInstanceService = jbpmProcessInstanceService;
	}

}
