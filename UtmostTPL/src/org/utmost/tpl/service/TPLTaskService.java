package org.utmost.tpl.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.utmost.common.CommService;

/**
 * 重要：调度器如何销毁 需要实现 重要: 如何重启任务 重要：如何刷新任务
 * 
 * @author wanglm
 * 
 */
@Service("TPLTaskService")
public class TPLTaskService extends CommService {
	private static Log logger = LogFactory.getLog(TPLTaskService.class);
	@Autowired
	public SchedulerFactoryBean schedulerfcy;

	/**
	 * 容器启动调用
	 */
	public void run() {
		logger.info("starting task manager!" + " " + new Date());
		initTask();
	}

	/**
	 * 初始化任务
	 */
	public void initTask() {
		logger.info("start initTask");
		String hql = "from U_TPL_TASKSCHEDULE v where v.state='1' order by v.taskpri desc";
		List<HashMap> list = getDb().findByHql(hql);
		for (HashMap map : list) {
			runTask(map);
		}
		logger.info("end initTask");
	}

	/**
	 * 规则契约(规则表必须包含rulename and expression 字段)
	 * @param map
	 */
	public void runTask(HashMap map) {
		String uuid = (String) map.get("uuid");
		String taskname = (String) map.get("taskname");
		String tasktype = (String) map.get("tasktype");
		String cronexpression = (String) map.get("cronexpression");
		String taskuuid = (String) map.get("taskuuid");
		String ruletable=(String) map.get("ruletable");
		logger.info("runTask:" + taskname);

		Scheduler scheduler = schedulerfcy.getScheduler();

		JobDetail jobDetail = new JobDetail(taskname, Scheduler.DEFAULT_GROUP,
				TPLTaskJob.class);

		jobDetail.getJobDataMap().put("uuid", uuid);
		jobDetail.getJobDataMap().put("taskname", taskname);
		jobDetail.getJobDataMap().put("tasktype", tasktype);
		jobDetail.getJobDataMap().put("cronexpression", cronexpression);
		jobDetail.getJobDataMap().put("taskuuid", taskuuid);
		jobDetail.getJobDataMap().put("ruletable", ruletable);

		CronTrigger trigger;
		try {
			trigger = new CronTrigger("{CronTrigger" + taskname + "}", null,
					cronexpression);
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Trigger trigger = TriggerUtils.makeImmediateTrigger(0, 0);
		// System.out.println(trigger);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmss");
		System.out.println(sdf.format(new Date()));
	}

}
