/*
 * FileName  : QAMUpdateActorsHandle.java
 * Version   : 1.0.0
 * Create On : 2008-10-13
 * Copyright : QZ
 * Author    : 
 */

package org.utmost.bpm.jbpm3.handle;

import java.util.HashMap;
import java.util.List;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;
import org.utmost.util.ParamContext;




/**
 * 从“工作流任务分配”表中，获取当前任务的处理角色信息及审批人员信息
 * 并将角色信息写入到工作流的参数中
 * 该类在工作流定义文件中如下：
 * <task name="所有上级领导会签" >
        		 <assignment class='org.utmost.bpm.jbpm3.handle.JBPMActorsHandle' />
        </task>
    若做为泳道，同进引用该类，需设置不同的泳道给不同的TASK引用，否则泳道设置无效
        
 * @author User
 *
 */

public class JBPMActorsHandle  implements AssignmentHandler {

	
	private static final long serialVersionUID = 1L;
	
	public void assign(Assignable assignable, ExecutionContext context)
			throws Exception {
		
		String roleName = "";
		String roleCode = "";
		String taskid = String.valueOf(context.getNode().getId());
		String taskname = context.getNode().getName();
		
		
		AutoService as = new AutoService();
		List listv = as.findByHql("from TASKROLE v where v.taskid='"+taskid+"'");
		HashMap roleMap = (HashMap)listv.get(0);
		
		String users = roleMap.get("usercode").toString();
		roleName = roleMap.get("rolename").toString();
		
		
		String actorIDs[] =  users.split(",");
		
		
		context.setVariable(ParamContext.TASK_curr_RoleName, roleName);
		assignable.setPooledActors(actorIDs);
		
		
	}

	
}
