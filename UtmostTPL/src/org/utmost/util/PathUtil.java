package org.utmost.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.utmost.report.service.ReportService;

public class PathUtil {
	public static String getWebPath() {
		String path = ReportService.class.getResource("/").getPath().toString();
		path = path.substring(1, path.lastIndexOf("WEB-INF"));
		path = path.replaceAll("%20", " ");
		return path;
	}

	public static String getClassPath() {
		String path = PathUtil.class.getResource("/").toString();
		return path.substring(6, path.length());
	}

	public static String getClassFilePath() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL sourceUrl = loader.getResource("");
		try {
			return URLDecoder.decode(sourceUrl.getFile(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getRootPath() {
		return System.getProperty("RootPath");
	}

	public static String getUploadPath() {
		String path = PathUtil.getRootPath() + "UPLOAD";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}

	public static void main(String[] args) {
		System.out.println(PathUtil.getClassFilePath());
		System.out.println(PathUtil.getClassPath());
	}
}
