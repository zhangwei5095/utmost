package org.utmost.tpl.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;
import org.utmost.portal.service.RuleService;

public class TPLTaskJob implements Job {
	private static String JYTHON = "JYTHON";
	private static String ETL = "ETL";
	static Log logger = LogFactory.getLog(TPLTaskJob.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		String jobName = jobDetail.getName();
		logger.info(jobName + " fired at " + new Date());
		JobDataMap dataMap = jobDetail.getJobDataMap();
		String tasktype = dataMap.getString("tasktype");
		String taskuuid = dataMap.getString("taskuuid");
		String ruletable = dataMap.getString("ruletable");

		logger.info("tasktype:" + tasktype);
		if (taskuuid == null || taskuuid.equals("")) {
			logger.error("taskuuid don't " + taskuuid);
			return;
		}
		taskuuid = taskuuid.replaceAll(",,", ",");
		String[] tuuid = taskuuid.split(",");
		if (TPLTaskJob.JYTHON.equals(tasktype)) {
			logger.info("JYTHON taskuuid:" + taskuuid);
			execJYTHON(tuuid, ruletable);
		} else if (TPLTaskJob.ETL.equals(tasktype)) {
			logger.info("ETL taskuuid:" + taskuuid);
			execETL(tuuid);
		} else {
			logger.error(tasktype + " error");
		}
	}

	/**
	 * 默认规则运行机制
	 * 
	 * @param taskuuid
	 */
	public void execJYTHON(String[] taskuuid, String ruletable) {
		this.execJYTHON(taskuuid, ruletable, "expression");
	}

	/**
	 * 
	 * @param taskuuid任务uuid
	 * @param tablename数据表名
	 * @param expression表达式字段
	 */
	public void execJYTHON(String[] taskuuid, String tablename,
			String expression) {
		AutoService as = (AutoService) SpringContext.getBean("AutoService");
		RuleService rs = (RuleService) SpringContext.getBean("RuleService");
		for (String uuid : taskuuid) {
			String[] id = uuid.split("=");
			// List<HashMap> list = as.findByUUID(tablename, id[0]);
			List<HashMap> list = as.findByHql("from " + tablename
					+ " v where v.uuid='" + id[0] + "'");
			for (HashMap hm : list) {
				String exp = (String) hm.get(expression);
				try {
					rs.exec(exp);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(exp);
				}
			}
		}
	}

	public void execETL(String[] taskuuid) {

	}
}
