package org.utmost.bpm.jbpm3.service;

import org.jbpm.graph.exe.ProcessInstance;

import java.util.Date;

/**
 * 业务层的任务po
 * Author: xinpeng
 * Date: 2008-10-21
 * Time: 15:14:53
 */
public class TaskInstanceDTO {

    private long id = 0l;
    private String name = null;
    private String description = null;
    private String actorId = null;
    private String processDefinitionVersion;
    private String processDefinitionName;
    private String actorName = null;
    private String create = null;
    private String start = null;
    private String end = null;
    private String dueDate = null;
    private long tokenId = 0l;
    private long taskId = 0l;
    private long swimlaneInstanceId = 0l;
    private long taskMgmtInstanceId = 0l;

    /**
     *
     * @return         编号
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id     编号
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return     任务名称
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name   任务名称 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return     任务描述
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description   任务描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return       参与者id
     */
    public String getActorId() {
        return actorId;
    }

    /**
     *
     * @param actorId     参与者id
     */
    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    /**
     *
     * @return        参与者名称
     */
    public String getActorName() {
        return actorName;
    }

    /**
     *
     * @param actorName   参与者名称
     */
    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    /**
     *
     * @return      任务创建的时间
     */
    public String getCreate() {
        return create;
    }

    /**
     *
     * @param create   任务创建的时间
     */
    public void setCreate(String create) {
        this.create = create;
    }

    /**
     *
     * @return      任务签收的时间
     */
    public String getStart() {
        return start;
    }

    /**
     * 
     * @param start     任务签收的时间
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     *
     * @return    任务完成时间
     */
    public String getEnd() {
        return end;
    }

    /**
     *
     * @param end   任务完成时间
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     *
     * @return     任务到期时间
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     *
     * @param dueDate     任务到期时间
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     *
     * @return     令牌
     */
    public long getTokenId() {
        return tokenId;
    }

    /**
     *
     * @param tokenId   令牌
     */
    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    /**
     *
     * @return      任务id
     */
    public long getTaskId() {
        return taskId;
    }

    /**
     *
     * @param taskId   任务id
     */
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    /**
     *
     * @return     泳道实例id
     */
    public long getSwimlaneInstanceId() {
        return swimlaneInstanceId;
    }

    /**
     *
     * @param swimlaneInstanceId   泳道实例id
     */
    public void setSwimlaneInstanceId(long swimlaneInstanceId) {
        this.swimlaneInstanceId = swimlaneInstanceId;
    }

    /**
     *
     * @return      任务管理实例id
     */
    public long getTaskMgmtInstanceId() {
        return taskMgmtInstanceId;
    }

    /**
     *
     * @param taskMgmtInstanceId    任务管理实例id
     */
    public void setTaskMgmtInstanceId(long taskMgmtInstanceId) {
        this.taskMgmtInstanceId = taskMgmtInstanceId;
    }

    /**
     *
     * @return  流程定义版本号
     */
    public String getProcessDefinitionVersion() {
        return processDefinitionVersion;
    }

    /**
     *
     * @param processDefinitionVersion   流程定义版本号
     */
    public void setProcessDefinitionVersion(String processDefinitionVersion) {
        this.processDefinitionVersion = processDefinitionVersion;
    }

    /**
     *
     * @return     流程定义名称
     */
    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    /**
     *
     * @param processDefinitionName    流程定义名称
     */
    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }
}
