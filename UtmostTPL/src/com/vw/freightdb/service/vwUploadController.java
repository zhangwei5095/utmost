package com.vw.freightdb.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;
import org.utmost.spring.mvc.controller.UploadController;
import org.utmost.util.PathUtil;

public class vwUploadController extends UploadController {
	/**
	 * upload And Return Collection
	 * 
	 * @param rq
	 * @param rp
	 * @throws FileUploadException
	 * @throws IOException
	 */
	@Override
	public void uploadARC(HttpServletRequest rq, HttpServletResponse rp)
			throws FileUploadException, IOException {
		AutoService service = (AutoService) SpringContext
				.getBean("AutoService");
		String type = (String) rq.getParameter("type");
		System.out.println("type:" + type);
		String path = PathUtil.getUploadPath() + "/upload";
		rq.setCharacterEncoding("UTF-8");
		rp.setCharacterEncoding("UTF-8");
		List<FileItem> items = getFileUpload().parseRequest(rq);
		Map map = null;
		for (FileItem item : items) {
			if (!item.isFormField()) {
				try {
					InputStream is = item.getInputStream();
					if (type.equals("road")) {
						map = ToRoadMap.getRoadMainList(is);
					}
					if (type.equals("railway")) {
						map = ToRailyMap.getRailwayMainList(is);
					}
					if (type.equals("seaone")) {
					}
					if (type.equals("seamore")) {
						// service.save(entityName, entity);
						// java.util.UUID.randomUUID().toString();
					}
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		PrintWriter pw = rp.getWriter();
		JSONObject json = JSONObject.fromObject(map);
		// JSONArray json = JSONArray.fromObject(map);
		System.out.println("--" + json.toString());

		// pw.write(new String(json.toString().getBytes(),"UTF-8"));
		pw.write(json.toString());
		pw.flush();
	}
}
