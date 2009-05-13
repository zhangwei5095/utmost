package org.utmost.report.service;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

import org.springframework.stereotype.Service;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;
import org.utmost.util.PathUtil;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DJChartBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

/**
 * 动态报表服务类
 * 
 * @author wanglm
 * 
 */
@Service("ReportService")
public class ReportService {
	/**
	 * 构建JasperPrint对象
	 * 
	 * @param reportInfo
	 * @param reportData
	 * @return
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 * @throws JRException
	 */
	public JasperPrint buildJasperPrint(HashMap reportInfo,
			Collection<HashMap> reportData) throws ColumnBuilderException,
			ClassNotFoundException, JRException {
		String title = (String) reportInfo.get("title");//
		String subtitle = (String) reportInfo.get("subtitle");//
		ArrayList<HashMap> columns = (ArrayList) reportInfo.get("columns");//
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(title);
		drb.setSubtitle(subtitle + " " + new Date());
		// 设置样式
		Style titleStyle = new Style();
		titleStyle.setFont(new Font(18, Font._FONT_ARIAL, true));
		// titleStyle.setFont(new Font(18,
		// Font.PDF_ENCODING_UniGB_UCS2_H_Chinese_Simplified, true));
		// titleStyle.setFont(new Font(18,
		// Font._FONT_ARIAL,
		// "STSong-Light", "UniGB-UCS2-H",
		// false));
		drb.setTitleStyle(titleStyle);
		drb.setDetailHeight(10).setMargins(30, 20, 30, 15);
		drb.setColumnsPerPage(1);
		drb.setUseFullPageWidth(true);
		drb.setPrintColumnNames(true);
		drb.setHeaderHeight(30);
		Integer margin = new Integer(5);
		drb.setSubtitleHeight(new Integer(20));
		drb.setDetailHeight(new Integer(10));
		drb.setLeftMargin(margin);
		drb.setRightMargin(margin);
		drb.setTopMargin(margin);
		drb.setBottomMargin(margin);
		drb.setPrintBackgroundOnOddRows(true);
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER);
		Color veryLightGrey = new Color(230, 230, 230);
		oddRowStyle.setBackgroundColor(veryLightGrey);
		oddRowStyle.setTransparency(Transparency.OPAQUE);
		drb.setOddRowBackgroundStyle(oddRowStyle);
		drb.setColumnsPerPage(new Integer(1));
		drb.setColumnSpace(new Integer(5));
		// 设置页大小
		Page page = new Page();
		page.setWidth(800);
		page.setHeight(600);
		page.setOrientationPortrait(true);
		drb.setPageSizeAndOrientation(page);

		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_FOOTER,
				AutoText.ALIGMENT_RIGHT);
		// drb.addAutoText(AutoText.AUTOTEXT_CREATED_ON,
		// AutoText.POSITION_FOOTER,
		// AutoText.ALIGMENT_LEFT,AutoText.PATTERN_DATE_DATE_TIME);
		for (HashMap map : columns) {
			String dataColumnProperty = (String) map.get("dataColumnProperty");//
			String dataClassTypeName = (String) map.get("dataClassTypeName");//
			String dataTitle = (String) map.get("dataTitle");//
			String dataWidth = (String) map.get("dataWidth");//
			String isgroup = (String) map.get("isgroup");//

			// System.out.println("map:" + map);
			ColumnBuilder cb = ColumnBuilder.getInstance();
			// 设置列样式
			Style headerStyle = new Style();
			headerStyle.setFont(Font.ARIAL_BIG_BOLD);
			// headerStyle.setFont(new Font(18,
			// Font.PDF_ENCODING_UniGB_UCS2_H_Chinese_Simplified, true));
			// titleStyle.setFont(new Font(18,
			// Font._FONT_ARIAL,
			// "STSong-Light", "UniGB-UCS2-H",
			// false));
			headerStyle.setBorderBottom(Border.PEN_2_POINT);
			headerStyle.setBorder(Border.THIN);//
			headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
			headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
			headerStyle.setBackgroundColor(Color.LIGHT_GRAY);
			headerStyle.setTextColor(Color.WHITE);
			headerStyle.setTransparency(Transparency.OPAQUE);
			cb.setHeaderStyle(headerStyle);

			Style style = new Style();
			style.setHorizontalAlign(HorizontalAlign.CENTER);
			style.setVerticalAlign(VerticalAlign.MIDDLE);
			style.setBorderColor(Color.black);
			style.setBorder(Border.THIN);
			cb.setStyle(style);

			if (dataTitle != null)
				cb.setTitle(dataTitle);
			if (dataClassTypeName != null)
				cb.setColumnProperty(dataColumnProperty, dataClassTypeName);
			if (dataWidth != null)
				cb.setWidth(Integer.parseInt(dataWidth));

			AbstractColumn column = cb.build();
			if (isgroup != null && isgroup.equalsIgnoreCase("true")) {
				GroupBuilder gb = new GroupBuilder();
				DJGroup g = gb.setCriteriaColumn((PropertyColumn) column)
						.setGroupLayout(GroupLayout.DEFAULT).build();
				drb.addGroup(g);
			}
			drb.addColumn(column);
		}

		DynamicReport dr = drb.build();
		JRDataSource ds = new JRBeanCollectionDataSource(reportData);
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr,
				new ClassicLayoutManager(), ds);

		// JasperViewer.viewReport(jp);
		return jp;
	}

	/**
	 * 导出HTML文件
	 * 
	 * @param jp
	 * @param filename
	 * @return
	 * @throws JRException
	 */
	public String exportReportToHtmlFile(JasperPrint jp, String filename)
			throws JRException {
		JasperExportManager.exportReportToHtmlFile(jp, filename);
		return filename;
	}

	/**
	 * 导出PDF文件
	 * 
	 * @param jp
	 * @param filename
	 * @return
	 * @throws JRException
	 */
	public String exportReportToPdfFile(JasperPrint jp, String filename)
			throws JRException {
		JasperExportManager.exportReportToPdfFile(jp, filename);
		return filename;
	}

	/**
	 * 导出RTF文件
	 * 
	 * @param jp
	 * @param filename
	 * @return
	 * @throws JRException
	 */
	public String exportReportToRtfFile(JasperPrint jp, String filename)
			throws JRException {
		JRRtfExporter exporter = new JRRtfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filename);
		exporter.exportReport();
		return filename;
	}

	/**
	 * 导出XSL文件
	 * 
	 * @param jp
	 * @param filename
	 * @return
	 * @throws JRException
	 */
	public String exportReportToXlsFile(JasperPrint jp, String filename)
			throws JRException {
		JRXlsExporter exporterXls = new JRXlsExporter();
		exporterXls.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporterXls
				.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filename);
		exporterXls.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
				Boolean.TRUE);
		exporterXls.exportReport();
		return filename;
	}

	public HashMap buildReportInfo(String reportcode) {
		AutoService as = (AutoService) SpringContext.getBean("AutoService");
		String hql = "from U_TPL_REPORT v where v.reportcode='" + reportcode
				+ "'";
		List reportlist = as.findByHql(hql);
		// System.out.println(reportlist);
		HashMap reportmap = (HashMap) reportlist.get(0);
		String uuid = (String) reportmap.get("uuid");
		hql = "from U_TPL_REPORT_ITEM v where v.cid='" + uuid
				+ "' order by v.fieldorder asc";
		List itemlist = as.findByHql(hql);
		// 构建中
		HashMap reportInfo = new HashMap();
		reportInfo.put("title", (String) reportmap.get("reporttitle"));
		reportInfo.put("subtitle", (String) reportmap.get("reportsubtitle"));

		ArrayList columns = new ArrayList();
		for (int x = 0; x < itemlist.size(); x++) {
			HashMap map = (HashMap) itemlist.get(x);
			map.put("dataTitle", (String) map.get("columntitle"));
			map.put("dataColumnProperty", (String) map.get("columnproperty"));
			map.put("dataClassTypeName", (String) map.get("columntype"));
			columns.add(map);
		}
		reportInfo.put("columns", columns);
		return reportInfo;
	}

	public List buildReportDataByHql(String hql) {
		AutoService as = (AutoService) SpringContext.getBean("AutoService");
		List list = as.findByHql(hql);
		// System.out.println("list--------->"+list);
		return list;
	}

	/**
	 * 入口方式
	 * 
	 * @param reportcode
	 * @param hql
	 * @param filename
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 * @throws JRException
	 */
	public String buildHtmlByHql(String reportcode, String hql)
			throws ColumnBuilderException, ClassNotFoundException, JRException {
		List reportData = this.buildReportDataByHql(hql);
		HashMap reportInfo = this.buildReportInfo(reportcode);
		JasperPrint jp = this.buildJasperPrint(reportInfo, reportData);

		String path = PathUtil.getWebPath();
		path += "REPORT/";
		String uuid = java.util.UUID.randomUUID().toString();
		String filename = path + uuid;
		System.out.println("export-->filename:" + filename);
		this.exportReportToHtmlFile(jp, filename + ".html");
		System.out.println("exportReportToHtmlFile-->end");
		this.exportReportToXlsFile(jp, filename + ".xls");
		System.out.println("exportReportToXlsFile-->end");
		this.exportReportToRtfFile(jp, filename + ".rtf");
		System.out.println("exportReportToRtfFile-->end");
		this.exportReportToPdfFile(jp, filename + ".pdf");
		System.out.println("exportReportToPdfFile-->end");
		return uuid + ".html";
	}

	// 测试方法
	public static void main(String[] args) throws ColumnBuilderException,
			ClassNotFoundException, JRException {
		String path = PathUtil.getWebPath();
		path += "REPORT/";
		System.out.println(path);

		// String hql =
		// "select reportItem.uuid as uuid,reportItem.columntitle as title from U_TPL_REPORT_ITEM reportItem";
		// String hql =
		// "select new map(item.uuid as rname,item.columntitle as rcode) from U_TPL_REPORT_ITEM item";
		// List reportData = rs.buildReportDataByHql(hql);
		// HashMap reportInfo = rs.buildReportInfo("reporttest");
		// rs.buildJasperPrint(reportInfo, reportData);
		/*
		 * ReportService rs = new ReportService(); HashMap reportInfo =
		 * rs.buildReportInfo("reporttest"); System.out.println("reportInfo:" +
		 * reportInfo); ArrayList reportData = new ArrayList(); for (int x = 0;
		 * x < 50; x++) { HashMap imap = new HashMap(); imap.put("rname",
		 * "wlm"); imap.put("rcode", "20"); imap.put("sex", "男");
		 * reportData.add(imap); } rs.buildJasperPrint(reportInfo, reportData);
		 */
		/*
		 * // ReportService rs = new ReportService(); HashMap reportInfo = new
		 * HashMap(); reportInfo.put("title", "title");
		 * reportInfo.put("subtitle", "subtitle");
		 * 
		 * // 列名 ArrayList columns = new ArrayList(); HashMap map = new
		 * HashMap(); map.put("dataTitle", "name");
		 * map.put("dataColumnProperty", "name"); map.put("dataClassTypeName",
		 * String.class.getName()); columns.add(map); map = new HashMap();
		 * map.put("dataTitle", "age"); map.put("dataColumnProperty", "age");
		 * map.put("dataClassTypeName", String.class.getName());
		 * columns.add(map); map = new HashMap(); map.put("dataTitle", "sex");
		 * map.put("dataColumnProperty", "sex"); map.put("dataClassTypeName",
		 * String.class.getName()); columns.add(map); reportInfo.put("columns",
		 * columns); // 填充数据 ArrayList reportData = new ArrayList(); for (int x
		 * = 0; x < 50; x++) { HashMap imap = new HashMap(); imap.put("name",
		 * "wlm"); imap.put("age", "20"); imap.put("sex", "男");
		 * reportData.add(imap); }
		 * 
		 * rs.exportReportToRtfFile(rs.buildJasperPrint(reportInfo, reportData),
		 * "D:/ddc.rtf");
		 */
	}
}
