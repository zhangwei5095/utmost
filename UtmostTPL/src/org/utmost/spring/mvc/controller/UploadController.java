package org.utmost.spring.mvc.controller;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.utmost.common.SpringContext;
import org.utmost.util.PathUtil;

public class UploadController extends MultiActionController {
	public void deployJbpm3(HttpServletRequest rq, HttpServletResponse rp) {
		System.out.println("rq-->" + rq);
		InputStream inputStream = null;//
		JbpmConfiguration config = (JbpmConfiguration) SpringContext
				.getBean("jbpmConfiguration");
		ProcessDefinition processDefinition = ProcessDefinition
				.parseXmlInputStream(inputStream);
		JbpmContext context = config.createJbpmContext();
		context.deployProcessDefinition(processDefinition);
		context.close();
		// jbpmcon.getCurrentJbpmContext().
	}

	public void upload(HttpServletRequest rq, HttpServletResponse rp)
			throws FileUploadException {
		String path = PathUtil.getUploadPath() + "/upload";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		List<FileItem> items = getFileUpload().parseRequest(rq);
		for (FileItem item : items) {
			if (!item.isFormField()) {
				File fi = new File(path + File.separator + item.getName());
				try {
					item.write(fi);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("upload:" + fi.getAbsolutePath());
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private ServletFileUpload getFileUpload() {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8"); // 加上这句，当上传文件名中含有中文时，可正常显示
		return upload;
	}
}
