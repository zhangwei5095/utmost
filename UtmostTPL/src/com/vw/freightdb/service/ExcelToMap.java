package com.vw.freightdb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 解析铁运陆运数据封装到HashMap中
 * 
 * @author quning
 * 
 */
public class ExcelToMap {
	public Logger logger = Logger.getLogger(ExcelToMap.class);
	public static HSSFRow row = null;
	public static HSSFCell cell = null;
	public static HSSFWorkbook workbook;

	public ExcelToMap(File excelFile) throws FileNotFoundException, IOException {
		workbook = new HSSFWorkbook(new FileInputStream(excelFile));
	}

	public ExcelToMap(InputStream is) throws FileNotFoundException, IOException {
		workbook = new HSSFWorkbook(is);
	}

	/**
	 * 获得表中的数据
	 * 
	 * @param sheetNumber
	 *            表格索引(EXCEL 是多表文档,所以需要输入表索引号)
	 * @return 由LIST构成的行和表
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<List> getDatasInSheet(int sheetNumber)
			throws FileNotFoundException, Exception {
		List<List> result = new ArrayList<List>();
		// 获得指定的表
		HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
		// 获得数据总行数
		int rowCount = sheet.getLastRowNum();
		logger.info("found excel rows count: " + rowCount);
		if (rowCount < 1) {
			return result;
		}
		// 逐行读取数据
		for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
			// 获得行对象
			HSSFRow row = sheet.getRow(rowIndex);

			if (row != null) {
				List<Object> rowData = new ArrayList<Object>();
				// 获得本行中单元格的个数
				int columnCount = row.getLastCellNum();
				// 获得本行中各单元格中的数据
				for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					HSSFCell cell = row.getCell(columnIndex);
					// 获得指定单元格中数据
					Object cellStr = this.getCellString(cell);
					rowData.add(cellStr);
				}
				result.add(rowData);
			}
		}
		return result;
	}

	/**
	 * 获取指定sheet中指定位置的单元格的值
	 * 
	 * @param sheetNumber
	 * @param i
	 * @param j
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getDatasInSheet(int sheetNumber, int i, int j)
			throws FileNotFoundException, Exception {
		List<List> result = new ArrayList<List>();
		// i 行，j列
		// 获得指定的表
		HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
		String stringreturn = "";
		Object cellStr = null;
		// 获得数据总行数
		int rowCount = sheet.getLastRowNum();
		// logger.info("found excel rows count: " + rowCount);
		// 逐行读取数据
		for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {

			// 获得行对象
			HSSFRow row = sheet.getRow(rowIndex);

			if (row != null && rowIndex == i) {
				List<Object> rowData = new ArrayList<Object>();
				// 获得本行中单元格的个数
				int columnCount = row.getLastCellNum();
				// 获得本行中各单元格中的数据
				for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					HSSFCell cell = row.getCell(columnIndex);
					if (columnIndex == j) {
						// 获得指定单元格中数据
						cellStr = getCellString(cell);
					}
				}
			}
		}
		if (cellStr == null) {
			stringreturn = "";
		} else {
			stringreturn = cellStr.toString();
		}
		return stringreturn;
	}

	/**
	 * 获得单元格中的内容
	 * 
	 * @param cell
	 * @return
	 */
	public static Object getCellString(HSSFCell cell) throws Exception {
		Object result = null;
		if (cell != null) {
			int cellType = cell.getCellType();
			switch (cellType) {
			case HSSFCell.CELL_TYPE_STRING:// 单元格中的内容 ——字符
				result = cell.getRichStringCellValue().getString();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:// 单元格中的内容 ——数字
				DecimalFormat df = new DecimalFormat("#.0000");
				String str = df.format(cell.getNumericCellValue());
				boolean isNum = isNumeric(str);
				if (isNum)
					result = df.format(cell.getNumericCellValue());
				else
					return "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA:// 单元格中的内容 ——公式
				result = cell.getNumericCellValue();
				break;
			case HSSFCell.CELL_TYPE_ERROR:// 单元格中的内容 ——错误
				result = null;
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 单元格中的内容 ——空格
				result = null;
				break;
			}
		}
		return result;
	}

	/**
	 * 判断输入字符串是否为数值型(包含浮点数)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) throws Exception {
		try {
			for (int i = str.length(); --i >= 0;) {
				if (!(Character.isDigit(str.charAt(i)) || str.charAt(i) == 46)) {
					throw new Exception("您输入的不是数字，请输入数字！");
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return true;
	}

	/**
	 * 处理整型数据类型
	 * 
	 * @param str
	 * @return
	 */
	public static String formatId(String str) {
		String formatStr = str;
		if ((str != null && !str.equals("")) && str.length() > 1) {
			int i = str.indexOf(".");
			formatStr = str.substring(i - 1);
		}
		return formatStr;
	}

	/**
	 * 处理年度
	 * 
	 * @param str
	 * @return
	 */
	public static String formatDate(String str) {
		String formatStr = str;
		int len = str.length();
		if ((str != null && !str.equals("")) && len > 1) {
			int i = str.lastIndexOf(".");
			String lastStr = "";
			String beforeStr = "";
			beforeStr = str.substring(0, i + 1);
			lastStr = str.substring(i + 1, len);
			lastStr = "20" + lastStr;
			formatStr = beforeStr + lastStr;
			formatStr = formatStr.replace(".", "/");
		}
		return formatStr;
	}

	/**
	 * main函数
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		long l1 = System.currentTimeMillis();
		Map roadMap = new HashMap();
		Map railMap = new HashMap();
		File file = null;
		InputStream is = null;
		
		String railwayFileName = "C:/Documents and Settings/Administrator/桌面/testExcel/testExcel/Railway_Request_form_testdata.xls";
		String roadFileName = "C:/Documents and Settings/Administrator/桌面/testExcel/testExcel/Road_request_form_testdata.xls";
		file = new File(railwayFileName);
		is = new FileInputStream(file);
		System.out.println("====铁运信息数据解析如下:");
		railMap = ToRailyMap.getRailwayMainList(is);// 铁路运输主信息
		System.out.println("====铁运信息map:" + railMap + "\r\n");

		file = new File(roadFileName);
		is = new FileInputStream(file);

		System.out.println("===陆运信息数据解析如下:");
		roadMap = ToRoadMap.getRoadMainList(is);// 陆运主信息
		System.out.println("====陆运信息map:" + roadMap);
		long l2 = System.currentTimeMillis();
		System.out.println(l2 - l1);
	}
}
