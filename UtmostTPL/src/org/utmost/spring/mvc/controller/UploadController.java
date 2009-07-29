package org.utmost.spring.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.util.JSONUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.utmost.common.SpringContext;
import org.utmost.util.PathUtil;

public class UploadController extends MultiActionController {

	/**
	 * 将上传的工作流流程数据发布到工作流上。
	 * 
	 * @param rq
	 * @param rp
	 * @throws FileUploadException
	 */
	// public void deployJBPMProc(HttpServletRequest rq, HttpServletResponse rp)
	// throws FileUploadException {
	//
	// JBPMService jbpmS = (JBPMService) SpringContext.getBean("JBPMService");
	// List<FileItem> items = getFileUpload().parseRequest(rq);
	// for (FileItem item : items) {
	// if (!item.isFormField()) {
	// try {
	// jbpmS.deployProcDef(item.getInputStream());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	/**
	 * 
	 * @param rq
	 * @param rp
	 * @throws FileUploadException
	 */
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
	 * upload And Return Collection
	 * 
	 * @param rq
	 * @param rp
	 * @throws FileUploadException
	 * @throws IOException
	 */
	public void uploadARC(HttpServletRequest rq, HttpServletResponse rp)
			throws FileUploadException, IOException {
		String path = PathUtil.getUploadPath() + "/upload";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		List<FileItem> items = getFileUpload().parseRequest(rq);

		List rlist = new ArrayList();
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
		// HashMap map = new HashMap();
		// map.put("1", "22");
		// map.put("33", "42");
		// rlist.add(map);
		// map = new HashMap();
		// map.put("15", "22");
		// map.put("33", "42");
		// rlist.add(map);
		PrintWriter pw = rp.getWriter();
		// JSONArray json = JSONArray.fromObject(rlist);
		// System.out.println("--" + json.toString());
		pw.write("hello test");
		pw.flush();
	}

	/**
	 * 
	 * @return
	 */
	public ServletFileUpload getFileUpload() {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8"); // 加上这句，当上传文件名中含有中文时，可正常显示
		return upload;
	}
}
