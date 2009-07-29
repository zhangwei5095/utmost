package com.vw.freightdb.service;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.Format;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ParseExcelUtil {
	/**
	 * 得到一个WorkBook
	 * 
	 * @param xlsPath
	 *            excel文件的路径
	 * @return 正常HSSFWorkbook，如果出现异常返回null
	 */
	public HSSFWorkbook getWorkBook(String xlsPath) {
		try {
			File f = new File(xlsPath);
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(f));
			return workbook;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到一个单元格的值如：BA2
	 * 
	 * @param sheet
	 * @param rowColStr
	 * @return 正常返回单元格的值，出现异常返回null
	 */
	public String getCellValue(HSSFSheet sheet, int rowNum, int colNum)
			throws Exception {
		HSSFRow row = sheet.getRow(rowNum - 1);
		HSSFCell cell = row.getCell(colNum - 1);
		String str = null;
		// 根据不同的类型返回不同的值
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			DecimalFormat df = new DecimalFormat("#.0000");
			str = df.format(cell.getNumericCellValue());
			boolean suc = checkNum(str);
			if (!suc) {
				return null;
			}
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			str = String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			str = cell.getRichStringCellValue().toString();
		} else {
			str = cell.getRichStringCellValue().toString();
		}
		return str;
	}

	/**
	 * 把A AA BC转化为整数
	 * 
	 * @param str
	 * @return 正常返回大于0的整数，出现异常返回0
	 */
	public int getColNum(String str) {
		try {
			String word = str.toUpperCase();
			int digit = word.length();
			int colNum = 0;
			for (int i = 0; i < digit; i++) {
				int num = word.charAt(i) - 64;
				int power = digit - i - 1;
				colNum += num * Math.pow(26, power);
			}
			return colNum;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * 有A3得出行号和列号
	 * 
	 * @param str
	 * @return 正常返回包含行号列号的整形数组异常返回null
	 */
	public int[] getRowCol(String str) {
		try {
			int len = str.length();
			int temp = 0;
			for (int i = 0; i < len; i++) {
				int num = str.charAt(i);
				// 0的ascii码为48 9为57
				if (num >= 48 && num <= 57) {
					temp = i;
					break;
				}
			}
			int rowNum = Integer.parseInt(str.substring(temp));
			int colNum = getColNum(str.substring(0, temp));
			int[] arr = { rowNum, colNum };
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 检查是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public boolean checkNum(String str) {
		boolean suc = false;
		for (int i = 0; i < str.length(); i++) {
			int num = str.charAt(i);
			// 0的ascii码为48 9为57
			if (num >= 48 && num <= 57 || num == 46) {
				suc = true;
			} else {
				suc = false;
			}
		}
		return suc;
	}

}
