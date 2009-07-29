package com.vw.freightdb.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.utmost.util.InputStreamUtil;

public class ToRailyMap {

	/**
	 * 铁路运输主表信息
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Map getRailwayMainList(InputStream _is)
			throws FileNotFoundException, Exception {

		List railList = new ArrayList();
		// File file = new File("Railway_Request_form_testdata.xls");
		byte[] b = InputStreamUtil.toByte(_is);
		InputStream is = InputStreamUtil.toInputStream(b);// copy
		ExcelToMap etm = new ExcelToMap(is);
		// 获得指定的表
		HSSFSheet sheet = etm.workbook.getSheetAt(0);
		// 获得数据总行数
		int rowCount = sheet.getLastRowNum();
		Map map = new HashMap();
		map.put("uuid", "2");
		String validityDate = etm.getDatasInSheet(0, 3, 8);
		validityDate = etm.formatDate(validityDate);
		map.put("providename", etm.getDatasInSheet(0, 3, 2));
		map.put("validitydate", validityDate);
		String dateUtil = etm.getDatasInSheet(0, 5, 8);
		dateUtil = etm.formatDate(dateUtil);
		map.put("until", dateUtil);
		map.put("remark", etm.getDatasInSheet(0, 2, 66));
		// 整合主子表为一个map
		Map railwayMap = new HashMap();
		railwayMap.put("U_FREIGHTINFO", map);
		is = InputStreamUtil.toInputStream(b);// copy
		railwayMap.put("U_RAILWAYFREIGHT", RailwayToMap(is));
		return railwayMap;
	}

	/**
	 * 直接将铁路运输数据封装到HashMap中
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List RailwayToMap(InputStream is) throws Exception {
		List list = new ArrayList();
		List railList = new ArrayList();
		// File file = new File("F:\\Road_request_form_testdata.xls");
		ExcelToMap etm = new ExcelToMap(is);
		// 获得指定的表
		HSSFSheet sheet = etm.workbook.getSheetAt(0);
		// 获得数据总行数
		int rowCount = sheet.getLastRowNum();

		int i = 0;
		for (int r = 14; r < rowCount; r++, i++) {
			String origincity = etm.getDatasInSheet(0, r, 1);
			String destination = etm.getDatasInSheet(0, r, 2);
			String uuid = etm.getDatasInSheet(0, r, 0).toString();
			String distance = etm.getDatasInSheet(0, r, 3);
			if (uuid != "" && origincity != "" && destination != ""
					&& distance != "") {
				Map map = new HashMap();
				map.put("uuid", uuid);
				map.put("pid", etm.formatId(uuid));
				map.put("origincity", origincity);
				map.put("destination", etm.getDatasInSheet(0, r, 2));
				map.put("distance", distance);
				map.put("ltawl_oc10kg", etm.getDatasInSheet(0, r, 4));
				map.put("ltawl_tc10kgkm", etm.getDatasInSheet(0, r, 5));
				map.put("ltawl_mincost", etm.getDatasInSheet(0, r, 6));
				map.put("ltl_timelimit", etm.getDatasInSheet(0, r, 7));
				map.put("fwl_wagontype", etm.getDatasInSheet(0, r, 8));
				map.put("fwl_octon", etm.getDatasInSheet(0, r, 9));
				map.put("fwl_tctonkm", etm.getDatasInSheet(0, r, 10));
				map.put("fwl_timelimit", etm.getDatasInSheet(0, r, 11));
				map.put("cl_1ton_ocbox", etm.getDatasInSheet(0, r, 12));
				map.put("cl_1ton_tcboxkm", etm.getDatasInSheet(0, r, 13));
				map.put("cl_10ton_ocbox", etm.getDatasInSheet(0, r, 14));
				map.put("cl_10ton_tcboxkm", etm.getDatasInSheet(0, r, 15));
				map.put("cl_20gp_ocbox", etm.getDatasInSheet(0, r, 16));
				map.put("cl_20gp_tcboxkm", etm.getDatasInSheet(0, r, 17));
				map.put("cl_40gp_ocbox2", etm.getDatasInSheet(0, r, 18));
				map.put("cl_40gp_tcboxkm2", etm.getDatasInSheet(0, r, 19));
				map.put("cl_timelimit", etm.getDatasInSheet(0, r, 20));
				map.put("fbu_timelimit", etm.getDatasInSheet(0, r, 21));
				map.put("fbu_50car_ocbox", etm.getDatasInSheet(0, r, 22));
				map.put("fbu_50car_tcboxkm", etm.getDatasInSheet(0, r, 23));
				map.put("fbu_25car_ocbox", etm.getDatasInSheet(0, r, 24));
				map.put("fbu_25car_tcboxkm", etm.getDatasInSheet(0, r, 25));
				map.put("fbu_20car_ocbox", etm.getDatasInSheet(0, r, 26));
				map.put("fbu_20car_tcboxkm", etm.getDatasInSheet(0, r, 27));
				System.out.println("数据记录号：第" + (r - 13) + "条:map:" + map);
				list.add(map);
			}
		}
		return list;
	}
}
