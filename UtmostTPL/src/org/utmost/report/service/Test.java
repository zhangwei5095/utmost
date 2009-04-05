package org.utmost.report.service;

import java.beans.Encoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.jfree.chart.encoders.EncoderUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.view.JasperViewer;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJChart;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DJChartBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

public class Test {
	public static void main(String[] args) throws Exception {
		Test test = new Test();
		test.buildReport();
		// System.out.println(String.class.getName());
	}

	public DynamicReport buildReport() throws Exception {
		FastReportBuilder drb = new FastReportBuilder();
		// DynamicReport dr = drb.addColumn("State", "state",
		// String.class.getName(), 30).addColumn("Branch", "branch",
		// String.class.getName(), 30).addColumn("Product Line",
		// "productLine", String.class.getName(), 50).addColumn("Item",
		// "item", String.class.getName(), 50).addColumn("Item Code",
		// "id", Long.class.getName(), 30, true).addColumn("Quantity",
		// "quantity", Long.class.getName(), 60, true).addColumn("Amount",
		// "amount", Float.class.getName(), 70, true).addGroups(2)
		// .setTitle("06年度销售报表").setSubtitle(
		// "生成日期 " + new Date())
		// .setPrintBackgroundOnOddRows(true).setUseFullPageWidth(true)
		// .build();

		DynamicReport dr = drb.addColumn("State", "state",
				String.class.getName(), 30).addColumn("Branch", "branch",
				String.class.getName(), 30).addColumn("Product CCLine",
				"productCCLine", String.class.getName(), 50).addColumn("Item",
				"item", String.class.getName(), 50).addColumn("Item Code",
				"id", Long.class.getName(), 30, true).addColumn("Quantity",
				"quantity", Long.class.getName(), 60, true).addColumn("Amount",
				"amount", Float.class.getName(), 70, true).setTitle("06年度销售报表")
				.setSubtitle("生成日期 " + new Date()).setPrintBackgroundOnOddRows(
						true).setUseFullPageWidth(true).build();

		DJChartBuilder cb = new DJChartBuilder();

		ArrayList list = new ArrayList();
		for (int x = 0; x < 100; x++) {
			HashMap map = new HashMap();
			map.put("state", "11");
			if (x < 10)
				map.put("productLine", "mapProductLine");
			else
				map.put("productLine", "productLine");
			map.put("amount", new Float(11 + x));
			list.add(map);
		}
		AbstractColumn columnState = ColumnBuilder.getInstance()
				.setColumnProperty("productLine", String.class.getName()).setTitle(
						"productLine").setWidth(new Integer(85)).build();
		drb.addColumn(columnState);
		
		//
		GroupBuilder gb1 = new GroupBuilder();
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnState)
				.setGroupLayout(GroupLayout.VALUE_IN_HEADER).build();
		drb.addGroup(g1);
		JRDataSource ds = new JRBeanCollectionDataSource(list);
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr,
				new ClassicLayoutManager(), ds);

		// JasperExportManager.exportReportToHtmlFile(jp, "D:/ddc.html");
		// JasperExportManager.exportReportToPdfFile(jp, "D:/ddc.pdf");

		// rtf
		// JRRtfExporter exporter = new JRRtfExporter();
		// exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		// exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
		// "D:/ddc.rtf");
		// exporter.exportReport();

		// xls
		// JRXlsExporter exporterXls = new JRXlsExporter();
		// exporterXls.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		// exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
		// "D:/ddc.xls");
		// exporterXls.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
		// Boolean.TRUE);
		// exporterXls.exportReport();
		// JasperViewer.view
		JasperViewer.viewReport(jp); // finally display the report report
		// JasperViewer.
		return dr;
	}
}
