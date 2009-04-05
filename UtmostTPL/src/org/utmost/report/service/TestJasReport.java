package org.utmost.report.service;

import java.util.HashMap;

import org.utmost.etl.core.JDBCFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class TestJasReport {
	public static void main(String[] args) throws JRException {
		HashMap map=new HashMap();
		map.put("title", "Map Title");
		JasperPrint jasperPrint=JasperFillManager.fillReport(TestJasReport.class
				.getResourceAsStream("newReport.jasper"), map, JDBCFactory
				.getConnection(JDBCFactory.getDataSource()));
		JasperExportManager.exportReportToHtmlFile(jasperPrint, "d:/abc.html");
		System.out.println("success!");
	}
}
