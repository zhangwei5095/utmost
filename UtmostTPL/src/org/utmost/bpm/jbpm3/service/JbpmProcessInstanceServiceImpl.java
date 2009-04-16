package org.utmost.bpm.jbpm3.service;

import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.db.GraphSession;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

import java.util.Map;

/**
 * 流程实例服务 Author: xinpeng Date: 2008-10-20 Time: 21:22:29
 */
public class JbpmProcessInstanceServiceImpl {

	private static final Log log = LogFactory
			.getLog(JbpmProcessInstanceServiceImpl.class);

	/**
	 * 开始流程
	 * 
	 * @param userId
	 *            用户userid
	 * @param processName
	 *            流程名称
	 */
	public void startProcess(String userId, String processName) {
		this.startProcess(userId, processName, null);
	}

	/**
	 * 开始流程（流程名称，启动流程的参与者，流程变量MAP）
	 * 
	 * @param userId
	 *            用户userid
	 * @param processName
	 *            流程名称
	 * @param processParameterMap
	 *            流程参数列表
	 */
	public void startProcess(String userId, String processName,
			Map<String, Object> processParameterMap) {
		JbpmContext jbpmContext = null;
		try {
			JbpmConfiguration jbpmConfiguration = JbpmConfiguration
					.getInstance();
			jbpmContext = jbpmConfiguration.createJbpmContext();
			GraphSession graphSession = jbpmContext.getGraphSession();
			// 加载流程定义
			ProcessDefinition processDefinition = graphSession
					.findLatestProcessDefinition(processName);
			ProcessInstance processInstance = new ProcessInstance(
					processDefinition);
			// 设置流程变量
			Assert.assertNotNull(processParameterMap);
			ContextInstance contextInstance = processInstance
					.getContextInstance();
			if (processParameterMap != null)
				for (String key : processParameterMap.keySet()) {
					Object value = processParameterMap.get(key);
					log.debug("key:" + key + ",value:" + value);
					contextInstance.setVariable(key, value);
				}
			log.debug("expense_money="
					+ contextInstance.getVariable("expense_money"));

			processInstance.signal();
			jbpmContext.save(processInstance);
		} catch (Exception e) {
			if (log.isDebugEnabled())
				e.printStackTrace();
			else
				log.error("startProcess is failed", e);
			throw new RuntimeException(e);
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
	}

	/**
	 * 已阅
	 * 
	 * @param taskInstanceId
	 *            任务实例id
	 */
	public void signInTaskInstance(long taskInstanceId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			TaskInstance taskInstance = taskMgmtSession
					.loadTaskInstance(taskInstanceId);
			taskInstance.start();
			jbpmContext.save(taskInstance);
		} catch (Exception e) {
			if (log.isDebugEnabled())
				e.printStackTrace();
			else
				log.error("signInTaskInstance is failed", e);
			throw new RuntimeException(e);
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
	}

	/**
	 * 完成任务实例
	 * 
	 * @param taskInstanceId
	 *            任务实例id
	 */
	public void completeTaskInstance(long taskInstanceId) {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = JbpmConfiguration.getInstance().createJbpmContext();
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			TaskInstance taskInstance = taskMgmtSession
					.loadTaskInstance(taskInstanceId);
			taskInstance.end();
			jbpmContext.save(taskInstance);
		} catch (Exception e) {
			if (log.isDebugEnabled())
				e.printStackTrace();
			else
				log.error("completeTaskInstance is failed", e);
			throw new RuntimeException(e);
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
	}

	/**
	 * 设置变量
	 * 
	 * @param processInstanceId
	 *            流程实例id
	 * @param name
	 *            流程变量名称
	 * @param value
	 *            流程变量对应的值
	 */
	public void setVariable(long processInstanceId, String name, Object value) {
		JbpmContext jbpmContext = JbpmConfiguration.getInstance()
				.createJbpmContext();
		ProcessInstance processInstance = jbpmContext
				.getProcessInstance(processInstanceId);
		ContextInstance contextInstance = processInstance.getContextInstance();
		contextInstance.setVariable(name, value);// 设置到了rootToken
		jbpmContext.save(processInstance);
		jbpmContext.close();
	}

	/**
	 * @param processInstanceId
	 *            流程实例id
	 * @param variableMap
	 *            流程变量的key和value的map
	 */
	public void setVariable(long processInstanceId,
			Map<String, Object> variableMap) {
		Assert.assertNotNull(variableMap);
		for (String varName : variableMap.keySet()) {
			Object varValue = variableMap.get(varName);
			this.setVariable(processInstanceId, varName, varValue);
		}
	}

	/**
	 * 根据tokenid获取流程实例对象
	 * 
	 * @param rootTokenId
	 *            根令牌
	 * @return 流程实例对象
	 */
	public ProcessInstance getProcessInstance(long rootTokenId) {
		JbpmContext jbpmContext = JbpmConfiguration.getInstance()
				.createJbpmContext();
		return jbpmContext.getToken(rootTokenId).getProcessInstance();
	}

	/**
	 * 回退
	 * 
	 * @param completedTaskInstanceId
	 *            完成的任务实例id
	 */
	public void callbackTaskInstance(long completedTaskInstanceId) {

	}
}
