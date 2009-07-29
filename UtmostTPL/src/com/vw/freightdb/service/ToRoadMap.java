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

public class ToRoadMap {

	/**
	 * 陆运主表信息
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Map getRoadMainList(InputStream _is)
			throws FileNotFoundException, Exception {
		List railList = new ArrayList();
		// File file = new File("Road_request_form_testdata.xls");
		byte[] b = InputStreamUtil.toByte(_is);
		InputStream is = InputStreamUtil.toInputStream(b);//copy
		ExcelToMap etm = new ExcelToMap(is);
		// 获得指定的表
		HSSFSheet sheet = etm.workbook.getSheetAt(0);
		// 获得数据总行数
		int rowCount = sheet.getLastRowNum();
		Map map = new HashMap();
		//		map.put("uuid", "2");
		String validityDate = etm.getDatasInSheet(0, 3, 8);
		validityDate = etm.formatDate(validityDate);
		map.put("providername", etm.getDatasInSheet(0, 3, 2));
		map.put("validfrom", validityDate);
		String dateUtil = etm.getDatasInSheet(0, 5, 8);
		dateUtil = etm.formatDate(dateUtil);
		map.put("until", dateUtil);
		map.put("remark", etm.getDatasInSheet(0, 2, 65));
		// 整合主子表为一个map
		Map roadMap = new HashMap();
		roadMap.put("U_FREIGHTINFO", map);
		// org.apache.commons.io.input.
		is = InputStreamUtil.toInputStream(b);//copy
		roadMap.put("U_ROADFREIGHT", RoadToMap(is));
		return roadMap;
	}

	/**
	 * 直接将陆运信息封装到hashMap
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List RoadToMap(InputStream is) throws Exception {
		List roadList = new ArrayList();
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
			String distance = etm.getDatasInSheet(0, r, 3);
			String uuid = etm.getDatasInSheet(0, r, 0);
			if (origincity != "" && destination != "" && distance != "") {
				Map map = new HashMap();
				// map.put("uuid", etm.formatId(uuid));
				// map.put("pid", etm.formatId(uuid));
				map.put("origincity", origincity);
				map.put("destination", destination);
				map.put("distance", distance);
				map.put("ltl_timelimit", etm.getDatasInSheet(0, r, 4));
				map.put("ltl_minicost", etm.getDatasInSheet(0, r, 5));
				map.put("ltl_m3", etm.getDatasInSheet(0, r, 6));
				map.put("ltl_ton", etm.getDatasInSheet(0, r, 7));
				map.put("ltl_kg", etm.getDatasInSheet(0, r, 8));
				map.put("ltl_tonkm", etm.getDatasInSheet(0, r, 9));
				map.put("ltl_m3km", etm.getDatasInSheet(0, r, 10));
				map.put("ftl_timelimit", etm.getDatasInSheet(0, r, 11));
				map.put("ftl_2ton", etm.getDatasInSheet(0, r, 12));
				map.put("ftl_5ton", etm.getDatasInSheet(0, r, 13));
				map.put("ftl_8ton", etm.getDatasInSheet(0, r, 14));
				map.put("ftl_10ton", etm.getDatasInSheet(0, r, 15));
				map.put("ftl_12ton", etm.getDatasInSheet(0, r, 16));
				map.put("ftl_15ton", etm.getDatasInSheet(0, r, 17));
				map.put("ftl_20ton", etm.getDatasInSheet(0, r, 18));
				map.put("container_timelimit", etm.getDatasInSheet(0, r, 19));
				map.put("container_20gp", etm.getDatasInSheet(0, r, 20));
				map.put("container_40gp", etm.getDatasInSheet(0, r, 21));
				map.put("container_20hc", etm.getDatasInSheet(0, r, 22));
				map.put("fbu_timelimit", etm.getDatasInSheet(0, r, 23));
				map.put("fbu_6car", etm.getDatasInSheet(0, r, 24));
				map.put("fbu_8car", etm.getDatasInSheet(0, r, 25));
				map.put("fbu_10car", etm.getDatasInSheet(0, r, 26));
				map.put("fbu_12car", etm.getDatasInSheet(0, r, 27));
				map.put("fbu_ltl_car", etm.getDatasInSheet(0, r, 28));
				roadList.add(map);
				System.out.println("数据记录号：第" + (r - 13) + "条:map:" + map);
			}
		}
		return roadList;
	}

}
