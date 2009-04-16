package org.utmost.bpm.jbpm3.service;

import org.jbpm.JbpmContext;
import org.jbpm.JbpmConfiguration;
import org.jbpm.graph.def.ProcessDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 发布流程服务 User: yuchen Date: 2008-11-25 Time: 16:53:16
 */
public class DeployJbpmProcessServiceImpl {
	private static Log log = LogFactory
			.getLog(DeployJbpmProcessServiceImpl.class);
	private static String filePath;

	/**
	 * 发布所有的流程定义
	 */
	public void deployAll() {
		log.info("开始导入JBPM流程定义......");
		// 此包id为初始化数据中的示例流程包的id
		List<String> lstProcessFile = getProcessFileList(filePath);
		if (!lstProcessFile.isEmpty()) {
			for (String jbpmProcessFileName : lstProcessFile) {
				importJbpmTemplate(jbpmProcessFileName);
			}
			log.info("示例流程导入成功.");
		} else {
			log.info("在[JBPMSIDE_HOME]/jbpmsideprocesses目录下没有发现JBPM流程定义xml文件");
		}
	}

	/**
	 * 发布传入参数名的流程定义
	 * 
	 * @param name
	 */
	public void deployByname(String name) {
		String filePathAndName = filePath + name + ".xml";
		this.importJbpmTemplate(filePathAndName);
	}

	private void importJbpmTemplate(String jbpmsideProcessFileName) {

		if (jbpmsideProcessFileName == null)
			throw new RuntimeException(
					"The templateFileName could not be null!");

		JbpmContext jbpmContext = null;
		try {
			File file = new File(jbpmsideProcessFileName);
			if (file.exists()) {
				InputStream ins = new FileInputStream(file);
				JbpmConfiguration jbpmConfiguration = JbpmConfiguration
						.getInstance();
				jbpmContext = jbpmConfiguration.createJbpmContext();
				ProcessDefinition processDefinition = ProcessDefinition
						.parseXmlInputStream(ins);
				jbpmContext.deployProcessDefinition(processDefinition);
			} else {
				throw new RuntimeException("The definition xml file ["
						+ jbpmsideProcessFileName + "] doesn't exist!");
			}
		} catch (Exception e) {
			if (log.isDebugEnabled())
				e.printStackTrace();
			else
				log.error("The definition xml file [" + jbpmsideProcessFileName
						+ "] doesn't exist!", e);
			throw new RuntimeException(e);
		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}
	}

	/*
	 * 获取流程定义文件列表
	 * 
	 * @param filePath 文件路径
	 * 
	 * @return 参数文件夹路径的流程文件的列表
	 */
	private List<String> getProcessFileList(String filePath) {
		List<String> list = new ArrayList<String>();
		// 读取jbpmsideprocesses下的*.xml文件
		File folder = new File(filePath);
		String[] pagemetaFiles = folder.list();
		for (String pagemetaFile : Arrays.asList(pagemetaFiles)) {
			if (pagemetaFile.endsWith(".xml")) {
				list.add(filePath + pagemetaFile);
			}
		}
		return list;
	}

	static {
		filePath = System.getProperty("JBPMSIDE_HOME");
		if (filePath == null) {
			log
					.info("没有指定环境变量JBPMSIDE_HOME。进入%tomcat%的目录下的bin中的startup.bat中设置set JBPMSIDE_HOME=../../../../conf/jbpmside-home（你的路径）");
		} else {
			filePath += File.separator + "jbpmsideprocesses" + File.separator;
		}
	}
}
