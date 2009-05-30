package org.utmost.bpm.jbpm3.service;



import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.Comment;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.stereotype.Component;
import org.utmost.common.SpringContext;
import org.utmost.util.DateUtil;
import org.utmost.util.ParamContext;



@Component("JBPMService")
public class JBPMService  {
	private static Log logger = LogFactory.getLog(JBPMService.class);
	private String dateformatstr = "yyyy-MM-dd HH:mm:ss";
	
	public boolean deployAll(){
		DeployJbpmProcessServiceImpl djpsi = new DeployJbpmProcessServiceImpl();
		djpsi.deployAll();
		return false;
	}
	/**
	 * 发布流程定义
	 * @param ins 
	 */
	public void deployProcDef(InputStream ins) {
		JbpmContext jbpmContext = null;
		try {
			//JbpmConfiguration jbpmConfiguration = JbpmConfiguration
			//.getInstance(); //用这种方式，报找不到hibernate.cfg.xml文件，奇怪
			JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
			
			jbpmContext = jbpmConfiguration.createJbpmContext();
			ProcessDefinition processDefinition = ProcessDefinition
						.parseXmlInputStream(ins);
			jbpmContext.deployProcessDefinition(processDefinition);
			
			
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				e.printStackTrace();
			else
				logger.error("数据有误，请确认", e);
			throw new RuntimeException(e);
		} finally {
			System.out.println(jbpmContext==null);
			if (jbpmContext != null)
				jbpmContext.close();
		}
	}
	/**
	 * 获取所有的流程定义
	 * @return
	 */
	public List<HashMap<String,Object>> findProcessDefinition(){
		//JbpmConfiguration jbpmConfiguration = JbpmConfiguration
		//.getInstance();
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		
		JbpmContext jbpmContext =null;
		try{
			jbpmContext = jbpmConfiguration.createJbpmContext();
			GraphSession graphSession = jbpmContext.getGraphSession();
			List<ProcessDefinition> procDeflist=graphSession.findAllProcessDefinitions();
			
			List<HashMap<String,Object>> returnList = new ArrayList<HashMap<String,Object>>();
			HashMap<String,Object> procDefMap = null;
			for(ProcessDefinition procDef:procDeflist){
				procDefMap = new HashMap<String,Object>();
				procDefMap.put("id", procDef.getId());
				procDefMap.put("name", procDef.getName());
				procDefMap.put("version", procDef.getVersion());
				returnList.add(procDefMap);
			}
			return returnList;
		}catch(Exception e){
			e.printStackTrace();
			
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
		return null;
		
	}
	/**
	 * 删除流程定义
	 * @param procdefID 流程定义ID
	 */
	public void removeProceDef(String procdefID){
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		try{
			GraphSession graphSession = jbpmContext.getGraphSession();
			graphSession.deleteProcessDefinition(Long.parseLong(procdefID));
			
		}catch(Exception e){
			e.printStackTrace();
			
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
	
		
	}
	/**
	 * 提前终止当前任务节点的流程实例
	 * @param taskInstanceID 任务节点ID
	 */
	public String endProcessInstan(String taskInstanceID){
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		try{
			
	
			TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.parseLong(taskInstanceID));
		    taskInstance.getProcessInstance().end();//结束流程实例的同时，还要结束当前任务节点
		    taskInstance.end();
	
			return "1";
		}catch(Exception e){
			e.printStackTrace();
			return "0";
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
	
	
		
	}
	/**
	 * 更改指定任务节点的处理人
	 * @param taskInstanceID 任务节点ID
	 * @param newActorIDS 新处理人 若为多个时，用‘；’分隔
	 */
	public String updateActorIDs(String taskInstanceID,String newActorIDS){
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		try{
			String newActorsArray[] = newActorIDS.split(";");
			
			Set<PooledActor>  actorSet = new HashSet <PooledActor>();  
			for(String actorId:newActorsArray){
				PooledActor objPooledActor = new PooledActor(); 
				objPooledActor.setActorId(actorId);
				actorSet.add(objPooledActor);
				
			}
			TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.parseLong(taskInstanceID));
			taskInstance.setPooledActors(actorSet);
			
			return "1";
		}catch(Exception e){
			e.printStackTrace();
			return "0";
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
	
	
		
	}
	/**
	 *  查找指定流程定义的流程实例，用于系统管理员对流程实例的处理
	 * @param procDefName 工作流定义ID
	 * @param isEnd 是否包含已结束流程实例，若isEnd=false，返回未结束流程实例，isEnd=true返回所有流程实例，包括已结束和未结束
	 * 
	 * @return
	 */
	public List<HashMap<String,Object>> findProcessInstanList(String procdefID,boolean isEnd){
		
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		try{
			
			
			GraphSession graphSession = jbpmContext.getGraphSession();
			List<HashMap<String,Object>> list0 = new ArrayList<HashMap<String,Object>>();
			
			
			List<ProcessInstance> listProceInstance = jbpmContext.getGraphSession().findProcessInstances(Long.parseLong(procdefID));
			
			System.out.println(listProceInstance.size());
			
			//jbpmContext.getTaskMgmtSession().findTaskInstancesByProcessInstance(arg0)
			
			HashMap<String,Object> tempMap = null;
			for(ProcessInstance processInstance:listProceInstance){
				
				tempMap = new HashMap<String,Object>();
				
				
				if(isEnd){//查找结果包含已结束流程
					if(processInstance.getEnd()!=null){//流程已结束
						tempMap.put("processInstanceID", processInstance.getId());//流程实例ID
						tempMap.put("startDate", DateUtil.formatDate(processInstance.getStart(),dateformatstr));//流程开始时间	
						tempMap.put("endDate", DateUtil.formatDate(processInstance.getEnd(),dateformatstr));//流程结束时间	
						tempMap.put("pro_status", "已结束");
						tempMap.put("checkOutActor", "已结束");
					}
					
				}
				if(processInstance.getEnd()==null){//流程未结束
					
					tempMap.put("processInstanceID", processInstance.getId());//流程实例ID
					tempMap.put("startDate", DateUtil.formatDate(processInstance.getStart(),dateformatstr));//流程开始时间	
					tempMap.put("pro_status", "进行中");
					if(processInstance.getContextInstance().getVariable(ParamContext.TASK_CURRENT_CHECKOUT)!=null){
						tempMap.put("checkOutActor", processInstance.getContextInstance().getVariable(ParamContext.TASK_CURRENT_CHECKOUT).toString()+"已检出");
					}else{
						tempMap.put("checkOutActor","未检出");
						if(processInstance.getContextInstance().getVariable(ParamContext.TASK_curr_RoleName)!=null){
							//从流程变更中获取当前任务（未完成）处理角色
							tempMap.put("currRoleName",processInstance.getContextInstance().getVariable(ParamContext.TASK_curr_RoleName).toString());
						}
					}
					Collection taskcoll=processInstance.getTaskMgmtInstance().getTaskInstances();
					for(Iterator it = taskcoll.iterator(); it.hasNext();){
						TaskInstance ti = (TaskInstance)it.next();
						if(ti.getEnd()==null){//获取流程实例的当前任务信息；
							Set paset = ti.getPooledActors();
							String actorids = "";
							for(Object obj:paset){
								PooledActor pa = (PooledActor)obj;
								actorids = actorids+pa.getActorId()+";";
							}
							tempMap.put("currActorIDs",actorids);
							tempMap.put("taskname",ti.getName());
							tempMap.put("taskID",ti.getId());
							tempMap.putAll(ti.getVariables());
							break;
						}
					}
				
				}	
				if(tempMap.size()>0)
					list0.add(tempMap);
				
			}	
			return list0;
			
		}catch(Exception e){
			e.printStackTrace();
			
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
	
		return null;
		
	}
	/**
	 * 启动工作流 ,
	 * procDefName 流程定义名称
	 *  busiHM 业务数据  busiHM应包括：审批注释、审请人等业务以外的数据
	 *  
	 *  return String 返回流程实例号
	 */
	public String startProcessInstance(HashMap<String,Object> busiHM,String procdefName){
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		try{
			
			String comment = "新提交"; //审批意见
			String actorid = ""; //处理人
			
			
			//获取名称为procDefName工作流定义
			ProcessDefinition processDefinition = jbpmContext.getGraphSession()
			.findLatestProcessDefinition(procdefName);
			
			//生成一个工作流实例
			ProcessInstance processInstance = processDefinition.createProcessInstance();
		
			
			if(busiHM!=null && busiHM.get("regteller")!=null)
				actorid = busiHM.get("regteller").toString();
			
			//将任务的发起者保存到任务实例变量中
			processInstance.getContextInstance().setVariable(ParamContext.Request_User, actorid);
			
			//生成一个任务实例
			TaskInstance ti=processInstance.getTaskMgmtInstance().createStartTaskInstance();
			
			if(busiHM!=null && busiHM.get("comment")!=null)
				comment = busiHM.get("comment").toString();
			
			
			
			ti.setVariables(busiHM);//将业务数据写入到流程实例的变量中
			
			
			ti.addComment(comment);
			ti.addComment("提交");
			ti.setActorId(actorid);
			ti.setVariable("approvetype", "提交");
			ti.end();
    		jbpmContext.save(processInstance);
			return ""+processInstance.getId();
		}catch(Exception e){
			e.printStackTrace();
			
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
		return null;
	}
	
	/**
	 * 根据流程定义ID号，获取该定义
	 * @param proceInstanceID
	 */
	public List<HashMap<String,Object>> findTaskNodename(String procdefName){
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		try{
			
			String comment = "新提交"; //审批意见
			String actorid = ""; //处理人
			
			
			//获取名称为procDefName工作流定义
			ProcessDefinition processDefinition = jbpmContext.getGraphSession()
			.findLatestProcessDefinition(procdefName);
			Map<String,Node> tasknodeMap = processDefinition.getNodesMap();
			
			Set tasknodesketSet = tasknodeMap.keySet();
			Node tasknode = null;
			List<HashMap<String,Object>> retTaskNodeList = new ArrayList<HashMap<String,Object>>();
			HashMap<String,Object> tempMap =null;
			for(Iterator it = tasknodesketSet.iterator(); it.hasNext();){
				Object key = it.next();
				
				tasknode = tasknodeMap.get(key);
				System.out.println(key);
				System.out.println(tasknode.getId());
				tempMap = new HashMap<String,Object>();
				tempMap.put("taskId", tasknode.getId());
				tempMap.put("taskName", tasknode.getName());
				tempMap.put("taskDescription",tasknode.getDescription());
				retTaskNodeList.add(tempMap);
			}
				
			return retTaskNodeList;
			
		}catch(Exception e){
			e.printStackTrace();
			
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
		return null;
	}
	/**
	 * 删除流程实例
	 * @param proceInstanceID 流程实例ID
	 */
	public void removeProceInstance(String proceInstanceID){
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		
		try{
			
			long longProceInstanceID = Long.parseLong(proceInstanceID);
	
			jbpmContext.getGraphSession().deleteProcessInstance(longProceInstanceID);

		}catch(Exception e){
			e.printStackTrace();
			
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
	
		
	}
	
	/**
	 * 获取指定用户的待办业务
	 * @param actorIds 用户ID
	 */
	public List<Map<String,Object>> findProcTasksList(String actorIds){
		
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
	
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		
		try{
			List<Map<String,Object>> list0 = new ArrayList<Map<String,Object>>();
			//获取actorIds的待办任务
			List<TaskInstance> tilist = jbpmContext.getTaskMgmtSession().findPooledTaskInstances(actorIds);
			
			System.out.println(jbpmContext.getTaskMgmtSession().findTaskInstances("a5")+"==============tilist");
			
			//同时须要获取下一任务驳回的任务
			List<TaskInstance> backlist = jbpmContext.getTaskMgmtSession().findTaskInstances(actorIds);
			if(backlist!=null && backlist.size()>0){
				for(TaskInstance ti :backlist){
					
					tilist.add(ti);
				}	
			}

			String checkOutActor = "";
			String desc ="";
				
			for(int i=0;tilist!=null && i<tilist.size();i++){
				TaskInstance ti = tilist.get(i);
				
				Map<String,Object> bm = ti.getVariables();
				bm.put("taskname", ti.getName());//任务名称
				bm.put("taskid", ti.getId());//任务ID
				bm.put("processInstanceID", ti.getProcessInstance().getId());//流程实例ID
				bm.put("description",ti.getDescription());//任务描述
				bm.put("startDate", ti.getCreate());//任务创建时间
				//从流程实例中获取TASK_curr_RoleName值，
				if(ti.getContextInstance().getVariable(ParamContext.TASK_curr_RoleName)!=null){
					bm.put(ParamContext.TASK_curr_RoleName,ti.getContextInstance().getVariable(ParamContext.TASK_curr_RoleName));
				}
				if(ti.getContextInstance().getVariable(ParamContext.TASK_CURRENT_CHECKOUT)!=null){
					checkOutActor = ti.getContextInstance().getVariable(ParamContext.TASK_CURRENT_CHECKOUT).toString()+"已检出";
					
				}else{
					checkOutActor = "未检出";
					
				}
				bm.put("checkOutActor", checkOutActor);
				
				//任务没有检出或当前检出人是登录人时，才可查看
				if(checkOutActor.equals("未检出") || checkOutActor.startsWith(actorIds)){
					list0.add(bm);
				}
			}
			return list0;
		}catch(Exception e){
			e.printStackTrace();
			
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
	
		return null;
		
	}
	/**
	 * //根据流程ID号查找审批日志
	 *@param proceInstanceID 流程实例ID
	 */
	public List<HashMap<String,Object>> findLogsByProcessInstanceID(String proceInstanceID){
		
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		try{
			
			List<HashMap<String,Object>> logList = new ArrayList<HashMap<String,Object>>();
			
			Map logs =jbpmContext.getLoggingSession().findLogsByProcessInstance(Long.parseLong(proceInstanceID));
			ProcessInstance proc = jbpmContext.getProcessInstance(Long.parseLong(proceInstanceID));
			System.out.println("jbpmContext.getTaskList(actorId);=="+jbpmContext.getTaskList("testing..."));
			
			Collection taskcoll=proc.getTaskMgmtInstance().getTaskInstances();
			
			HashMap<String,Object> tempMap = null;
			for(Iterator it = taskcoll.iterator(); it.hasNext();){
				TaskInstance ti = (TaskInstance)it.next();
			
				if(ti.getEnd()!=null){//日志中只显示已结束的任务
					tempMap = new HashMap<String,Object>();
					Comment coms  = null;
					if(ti.getComments()!=null && ti.getComments().size()>0){
						coms = (Comment)ti.getComments().get(0);//第一个是审批意见，第二个是审批类型
						tempMap.put("comment", coms.getMessage());
					}
					tempMap.put("actorID", ti.getActorId());
					tempMap.put("taskname",ti.getName());
					
					if(ti.getComments().size()>1){
						coms =(Comment)ti.getComments().get(1);
						tempMap.put("approvetype",coms.getMessage());
						
					}
					tempMap.put("date",ti.getEnd());
					logList.add(tempMap);
				}	
	
			}
	
			return logList;
		}catch(Exception e){
			e.printStackTrace();
			
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
		return null;
	
	}
	/**
	 * 进入下一任务 
	 * @param busiHM 业务数据，还应包括：taskid 任务ID，route 路由名称,actorid处理人
	 *
	 **/	
	public String toNextProce(HashMap<String,Object> busiHM){
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		try{
			String taskInstanceID = "";
			System.out.println(busiHM);
			if(busiHM!=null && busiHM.get("taskid")!=null)
				taskInstanceID = busiHM.get("taskid").toString();
			else{
				new Exception("工作流参数传递出错");
			}
		    TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.parseLong(taskInstanceID));
		    
		    String comment = "驳回去了"; //审批意见
			String actorid = "abcd"; //处理人
			String errid = "0001";//差错号
			String busitype = "现金业务";//业务类型
			String route ="back";//路由名称
			String trandate = "";//交易日期
			String serseqno = "";//流水号
			
			if(busiHM!=null && busiHM.get("route")!=null)
				route = busiHM.get("route").toString();
			
			if(busiHM!=null && busiHM.get("comment")!=null)
				comment = busiHM.get("comment").toString();
			
			
			if(busiHM!=null && busiHM.get("actorid")!=null)
				actorid = busiHM.get("actorid").toString();

			if (taskInstance.getEnd() != null)
				throw new Exception("任务己经结束，不能做任何修改!");
			
			taskInstance.setActorId(actorid);
			taskInstance.addComment(comment);
			taskInstance.setVariables(busiHM);
			
			
			//taskInstance.end();
			if(route.equals("back"))
				taskInstance.addComment("驳回");
			else if(route.equals("next"))
				taskInstance.addComment("同意");
			else{
				taskInstance.addComment("取消");
			}
			//taskInstance.end();
			taskInstance.end(route);
			if(taskInstance.getContextInstance().getVariable(ParamContext.TASK_CURRENT_CHECKOUT)!=null)
				taskInstance.getContextInstance().deleteVariable(ParamContext.TASK_CURRENT_CHECKOUT);
			jbpmContext.save(taskInstance);
			
			
			return "1";
		}catch(Exception e){
			e.printStackTrace();
			
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
	
		return "0";

	}
	/**
	 * 根据流定义名称查找流程定义ID
	 * @param processDefinitionName
	 * @return
	 */
	public long findProcessDefinitionByID(final String processDefinitionName){
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		long processDefinitionID = 0;
		try{
			processDefinitionID = jbpmContext.getGraphSession()
			.findLatestProcessDefinition(processDefinitionName)
			.getId();
		}catch(Exception e){
			System.out.println("未定义工作流，请联系统管理员发布流程定义!");
			e.printStackTrace();
		}finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
			return processDefinitionID;
	}
	/**
	 * 检出任务，即分配任务
	 * @param taskInstanceID
	 * @param uid
	 * @throws Exception 
	 */
	public void checkOutTaskInstance(long taskInstanceID, String userCode) throws Exception {
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		try{
			TaskInstance taskInstance = jbpmContext.getTaskInstance(taskInstanceID);
			String currentActor = "";
			
			if( taskInstance.getContextInstance().getVariable(ParamContext.TASK_CURRENT_CHECKOUT)!=null){
				currentActor = taskInstance.getContextInstance().getVariable(ParamContext.TASK_CURRENT_CHECKOUT).toString();
			
			}
			if(currentActor.equals("")){
				taskInstance.getContextInstance().setVariable(ParamContext.TASK_CURRENT_CHECKOUT,userCode);
			
			}else{
				throw new Exception("任务已经被"+ currentActor+ "检出,不允许再次检出!");
			
			}
		}	finally {
			if (jbpmContext != null)
				jbpmContext.close();
			}
		
	
	}
	/**
	 * 检入任务，即将任务放回任务池
	 * @param taskInstanceID
	 * @param uid
	 * @throws Exception 
	 */
	
	public void checkInTaskInstance(long taskInstanceID, String userCode) throws Exception {
		JbpmConfiguration jbpmConfiguration =(JbpmConfiguration) SpringContext.getBean("jbpmConfiguration");
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		TaskInstance taskInstance = jbpmContext.getTaskInstance(taskInstanceID);
		String currentActor = "";
		try{
			if( taskInstance.getContextInstance().getVariable(ParamContext.TASK_CURRENT_CHECKOUT)!=null){
				currentActor = taskInstance.getContextInstance().getVariable(ParamContext.TASK_CURRENT_CHECKOUT).toString();
				
			}
			if(currentActor.equals("")){
				throw new Exception("该任务没有被检出，无需检入!");
			}else if(!currentActor.equals(userCode)){ 
				throw new Exception("无权限检入，请检查是否为当前检出人");
			}else{
				 taskInstance.getContextInstance().deleteVariable(ParamContext.TASK_CURRENT_CHECKOUT);
			}	
		}	finally {
			if (jbpmContext != null)
				jbpmContext.close();
	}	
			
	}
	
}
