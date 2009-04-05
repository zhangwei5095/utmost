package org.utmost.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.utmost.tpl.service.TemplateService;

/**
 *Spring上下文接管服务类
 * 
 * @author wanglm
 * 
 */
@Component("SpringContext")
public class SpringContext implements ApplicationContextAware {
	private static Log logger = LogFactory.getLog(SpringContext.class);
	private static ApplicationContext applicationContext;

	/**
	 * 自动注入上下文
	 */
	@Autowired
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		applicationContext = context;
	}

	public static ApplicationContext getContext() {
		if (applicationContext == null) {
			System.out.println("init applicationContext...");
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					"/conf/applicationContext.xml");
			applicationContext = ctx;
			
		}
		return applicationContext;
	}

	/**
	 * 获得Spring管理的bean
	 * 
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return SpringContext.getContext().getBean(name);
	}

	/**
	 * 重新加载Spring服务并重新生成hbm映射文件
	 */
	public static void reloadSpring() {
		// 重新生成Hibernate配置文件
		TemplateService ts = (TemplateService) SpringContext
				.getBean("TemplateService");
		ts.processAllHbm();
		// 重新加载初始化Spring
		AbstractApplicationContext aac = (AbstractApplicationContext) SpringContext
				.getContext();
		aac.refresh();

	}
}
