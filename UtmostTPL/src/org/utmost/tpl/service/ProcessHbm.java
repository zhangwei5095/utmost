package org.utmost.tpl.service;

import java.util.HashMap;
import java.util.List;

/**
 * hibernate映射文件生成工具类
 * 
 * @author wanglm
 * 
 */
public class ProcessHbm {
	public String process(List<HashMap> list, String tablename) {
		StringBuffer sb = new StringBuffer();
		if (list.size() != 0) {
			sb.append(this.processHeader((HashMap) list.get(0), tablename));
		}
		for (HashMap m : list) {
			String ispk = (String) m.get("ispk");
			// 主键
			if (ispk.equals("true")) {
				sb.append(this.processPK(m));
			}
		}
		for (HashMap m : list) {
			String ispk = (String) m.get("ispk");
			// 非主键
			if (!ispk.equals("true")) {
				sb.append(this.processProperty(m));
			}
		}
		sb.append(this.processEnd());
		System.out.println(sb.toString());
		return sb.toString();
	}

	private String processHeader(HashMap m, String tablename) {
		StringBuffer sb = new StringBuffer();
		String header = "<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\""
				+ " \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">";
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
		sb.append(header + "\n");
		sb.append("<hibernate-mapping>" + "\n");
		sb.append("<class entity-name=\"" + tablename + "\" table=\""
				+ tablename + "\">" + "\n");
		return sb.toString();
	}

	private String processEnd() {
		StringBuffer sb = new StringBuffer();
		sb.append("</class>" + "\n");
		sb.append("</hibernate-mapping>" + "\n");
		return sb.toString();
	}

	private String processProperty(HashMap m) {
		String datacode = (String) m.get("datacode");
		String dataname = (String) m.get("dataname");
		String datalength = (String) m.get("datalength");
		String datatype = (String) m.get("datatype");
		String nodedesc = (String) m.get("nodedesc");
		String unique = (String) m.get("isunique");

		StringBuffer sb = new StringBuffer();
		sb.append("<property name=\"" + datacode.toLowerCase() + "\" type=\""
				+ datatype + "\" unique=\"" + unique + "\" >" + "\n");
		sb.append("<column length=\"" + datalength + "\" name=\""
				+ datacode.toUpperCase() + "\">" + "\n");
		sb.append("<comment>" + nodedesc + "</comment>" + "\n");
		sb.append("</column>" + "\n");
		sb.append("</property>" + "\n");
		return sb.toString();
	}

	private String processPK(HashMap m) {
		String datacode = (String) m.get("datacode");
		String dataname = (String) m.get("dataname");
		String datalength = (String) m.get("datalength");
		String datatype = (String) m.get("datatype");
		String generator = (String) m.get("generator");
		StringBuffer sb = new StringBuffer();
		sb.append("<id column=\"" + datacode.toUpperCase() + "\" name=\""
				+ datacode.toLowerCase() + "\" length=\"" + datalength
				+ "\" type=\"" + datatype + "\">" + "\n");
		sb.append("<generator class=\"" + generator + "\" />" + "\n");
		sb.append("</id>" + "\n");
		return sb.toString();
	}
}
