package com.vw.freightdb.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadSeaRoute {

	/**
	 * 得到头部信息
	 * @param sheet
	 * @param coordinate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public Map<String,Object> getHead(HSSFSheet sheet,String[] coordinate) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		String[] keys=new String[]{"uuid","providername","businesstype","validfrom",
		"until","validitydate","permission","remark","handlers","createtime","lastedittime"};
		ParseExcelUtil peu=new ParseExcelUtil();
		for(int i=0;i<keys.length;i++){
			String rowColStr=coordinate[i];
			if("".equals(rowColStr)){
				map.put(keys[i],rowColStr);
			}else{
				int[] rowColNum=peu.getRowCol(rowColStr);
				int rowNum=rowColNum[0];
				int colNum=rowColNum[1];
				String value=peu.getCellValue(sheet, rowNum, colNum);
				if(keys[i].equals("until")||keys[i].equals("validitydate")){
					DateFormat format= new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yy");
					Date date = format2.parse(value);
					value=format.format(date).replaceAll("\n", "");
				}
				map.put(keys[i], value);
			}
			
		}
		return map;
	}
	/**
	 * 得到SeaFreight
	 * @param sheet
	 * @param coordinate
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getSeaFreight(HSSFSheet sheet,String[] coordinate) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		String[] keys = {"uuid", "pid","origincity", "destination", "packingtype","totalfreight",
				"p1_totalfreight","p1_from","p1_to", "p1_transittime", "p1_currency", 
				"p2_totalfreight","p2_from","p2_to", "p2_transittime", "p2_currency",
				"p3_totalfreight","p3_from","p1_to", "p3_transittime", "p3_currency",
				"p4_totalfreight","p4_from","p4_to", "p4_transittime", "p4_currency",
				"p5_totalfreight","p5_from","p5_to", "p5_transittime", "p5_currency"
				};
		ParseExcelUtil peu=new ParseExcelUtil();
		for(int i=0;i<keys.length;i++){
			String rowColStr=coordinate[i];
			if("".equals(rowColStr)){
				map.put(keys[i],rowColStr);
			}else{
				int[] rowColNum=peu.getRowCol(rowColStr);
				int rowNum=rowColNum[0];
				int colNum=rowColNum[1];
				String value=peu.getCellValue(sheet, rowNum, colNum).replaceAll("\n", "");
				map.put(keys[i], value);
			}
		}
		return map;
	}
	/**
	 * 根据坐标来得到SeaFreight
	 * @param sheet
	 * @param coordinate
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getSeaFreightByNum(HSSFSheet sheet,String[] rowColStr,int  index,int maxRow) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		String[] keys = {"uuid", "pid","origincity", "destination", "packingtype","totalfreight",
				"p1_totalfreight","p1_from","p1_to", "p1_transittime", "p1_currency", 
				"p2_totalfreight","p2_from","p2_to", "p2_transittime", "p2_currency",
				"p3_totalfreight","p3_from","p1_to", "p3_transittime", "p3_currency",
				"p4_totalfreight","p4_from","p4_to", "p4_transittime", "p4_currency",
				"p5_totalfreight","p5_from","p5_to", "p5_transittime", "p5_currency"
				};
		ParseExcelUtil peu=new ParseExcelUtil();
		for(int i=0;i<keys.length;i++){
			if("".equals(rowColStr[i])){
				map.put(keys[i],"");
			}else{
				int[] num=peu.getRowCol(rowColStr[i]);
				int rowNum=0;
				if(index!=0){
					rowNum=num[0]+(index)*2;
				}else{
					rowNum=num[0];
				}
				if(rowNum>maxRow){
					return null;
				}
				String value="";
				value=peu.getCellValue(sheet, rowNum, num[1]).replaceAll("\n", "");
				map.put(keys[i], value);
			}
		}
		if("".equals(map.get("p1_from"))||null==map.get("p1_from")){
			return null;
		}else{
			return map;
		}	
	}
	/**
	 * 得到一个SeaFreightItem 
	 * @param sheet
	 * @param coordinate
	 * @param sect 第几段
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getSeaFreightItem(HSSFSheet sheet,String[] coordinate,int sect) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		String[] keys = {"uuid","pid","name","amount","unit"};
		ParseExcelUtil peu=new ParseExcelUtil();
		//name amount unit同时不为空的情况下才加
		boolean isAdd=false;
		for(int i=0;i<keys.length;i++){
			String rowColStr=coordinate[i];
			if("".equals(rowColStr)){
				map.put(keys[i],rowColStr);
			}else{
				int[] rowColNum=peu.getRowCol(rowColStr);
				int rowNum=rowColNum[0]+(sect-1)*16;
				int colNum=rowColNum[1];
				String value=peu.getCellValue(sheet, rowNum, colNum).replaceAll("\n", "");
				if("name".equals(keys[i])){
					if(sect==3||sect==4){
						value="p5_"+value;
					}else{
						value="p"+sect+"_"+value;
					}
					
				}
				if(null!=value&&!"".equals(value)){
					map.put(keys[i], value);
					isAdd=true;
				}else{
					isAdd=false;
				}
			}
		}
		return (isAdd==true)?map:null;
	}
	public Map<String,Object> getSeaFreightItemByNum(HSSFSheet sheet,String[] coordinate,int sect,int index,int maxRow) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		String[] keys = {"uuid","pid","name","amount","unit"};
		ParseExcelUtil peu=new ParseExcelUtil();
		//name amount unit同时不为空的情况下才加
		boolean isAdd=false;
		for(int i=0;i<keys.length;i++){
			String rowColStr=coordinate[i];
			if("".equals(rowColStr)){
				map.put(keys[i],rowColStr);
			}else{
				int[] rowColNum=peu.getRowCol(rowColStr);
				int rowNum=0;
				if(keys[i].equals("name")){
					rowNum=rowColNum[0];
				}else{
					rowNum=rowColNum[0]+index*2;
				}
				if(rowNum>maxRow){
					return null;
				}
				int colNum=rowColNum[1];
				String value=peu.getCellValue(sheet, rowNum, colNum).replaceAll("\n", "");
				if("name".equals(keys[i])){
					if(sect==3||sect==4){
						value="p5_"+value;
					}else{
						value="p"+sect+"_"+value;
					}
					
				}
				if(null!=value&&!"".equals(value)){
					map.put(keys[i], value);
					isAdd=true;
				}else{
					isAdd=false;
				}
			}
		}
		return (isAdd==true)?map:null;
	}
	/**
	 * 求出三段所有的items
	 * @param sheet
	 * @param nameNum
	 * @param amountNum
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getSeaFreightItems(HSSFSheet sheet,String[] nameNum,String[] amountNum,String[] unitNum,int sectNum) throws Exception{
		List<Map> items=new ArrayList();
		for(int i=1;i<=sectNum;i++){
			Map<String,Object> map=null;
			for(int j=0;j<nameNum.length;j++){
				String nameIndex=nameNum[j];
				String amountIndex=amountNum[j];
				String unitIndex=unitNum[j];
				String[] coordinate=new String[]{"","",nameIndex,amountIndex,unitIndex};
				map=this.getSeaFreightItem(sheet, coordinate, i);
				if(map!=null){
					items.add(map);
				}
			}
		}
		return items;
	}
	public List<Map> getSeaFreightItemsByNum(HSSFSheet sheet,String[] nameNum,String[] amountNum,String[] unitNum,int sect,int index,int maxRow) throws Exception{
		List<Map> items=new ArrayList();
		Map<String,Object> map=null;
		for(int j=0;j<nameNum.length;j++){
			String nameIndex=nameNum[j];
			String amountIndex=amountNum[j];
			String unitIndex=unitNum[j];
			String[] coordinate=new String[]{"","",nameIndex,amountIndex,unitIndex};
			map=this.getSeaFreightItemByNum(sheet, coordinate, sect, index, maxRow);
			if(map!=null){
				String amountVal=(String) map.get("amount");
				if(!"".equals(amountVal)||null!=amountVal){
					items.add(map);
				}
			}
		}
		return items;
	}
	/**
	 * 得到头部信息 海运信息及费用信息
	 * @param xlsPath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getSeaOneInfo(String xlsPath) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		ParseExcelUtil peu=new ParseExcelUtil();
		HSSFWorkbook book=peu.getWorkBook(xlsPath);
		HSSFSheet sheet=book.getSheetAt(0);
		
		String[] coordinateHead=new String[]{"","D3","","","N3","N5","","D68","","",""};
		//得到头部map
		Map<String,Object> headMap=this.getHead(sheet, coordinateHead);
		map.put("U_FREIGHTINFO", headMap);
		
		String[] coordinateSea1=new String[]{
				"","","","","B26","",
				"","F21","H21","K21","P21",
				"","F37","H37","K37","P37",
				"","","","","",
				"","","","","",
				"","F53","H53","K53","P53"
		};
		String[] coordinateSea2=new String[]{
				"","","","","B26","",
				"","F21","H21","K21","P21",
				"","F37","H37","K37","P37",
				"","","","","",
				"","","","","",
				"","F53","H53","K53","P53",
		};
		String[] coordinateSea3=new String[]{
				"","","","","B29","",
				"","F21","H21","K21","P21",
				"","F37","H37","K37","P37",
				"","","","","",
				"","","","","",
				"","F53","H53","K53","P53",
		};
		String[] nameNum1 = new String[]{ "D24", "F24", "G24", "H24", "I24", "K24", "L24",
				"M24", "N24", "O24", "P24" };
		
		String[] amountNum1=new String[]{ "D26", "F26", "G26", "H26", "I26", "K26", "L26",
				"M26", "N26", "O26", "P26"};
		String[] unitNum1=new String[]{ "D27", "F27", "G27", "H27", "I27", "K27", "L27",
				"M27", "N27", "O27", "P27"};
		
		String[] amountNum2=new String[]{ "D29", "F29", "G29", "H29", "I29", "K29", "L29",
				"M29", "N29", "O29", "P29"};
		String[] unitNum2=new String[]{ "D30", "F30", "G30", "H30", "I30", "K30", "L30",
				"M30", "N30", "O30", "P30"};
		
		String[] amountNum3=new String[]{ "D32", "F32", "G32", "H32", "I32", "K32", "L32",
				"M32", "N32", "O32", "P32"};
		String[] unitNum3=new String[]{ "D33", "F33", "G33", "H33", "I33", "K33", "L33",
				"M33", "N33", "O33", "P33"};
		List<Map> seas=new ArrayList();
		
		Map<String,Object> seaMap1=this.getSeaFreight(sheet, coordinateSea1);
		List<Map> itemsMap1=this.getSeaFreightItems(sheet, nameNum1, amountNum1, unitNum1, 3);
		seaMap1.put("U_SEAFREIGHTITEM", itemsMap1);
		seas.add(seaMap1);
		
		Map<String,Object> seaMap2=this.getSeaFreight(sheet, coordinateSea2);
		List<Map> itemsMap2=this.getSeaFreightItems(sheet, nameNum1, amountNum2, unitNum2, 3);
		seaMap2.put("U_SEAFREIGHTITEM", itemsMap2);
		seas.add(seaMap2);
		
		Map<String,Object> seaMap3=this.getSeaFreight(sheet, coordinateSea3);
		List<Map> itemsMap3=this.getSeaFreightItems(sheet, nameNum1, amountNum3, unitNum3, 3);
		seaMap3.put("U_SEAFREIGHTITEM", itemsMap3);
		seas.add(seaMap3);
		
		map.put("U_SEAFREIGHT", seas);
		return map;
	}
	public Map<String,Object> getSeaMoreInfo(String xlsPath) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		ParseExcelUtil peu=new ParseExcelUtil();
		HSSFWorkbook book=peu.getWorkBook(xlsPath);
		HSSFSheet sheet=book.getSheetAt(0);
		
		String[] coordinateHead=new String[]{"","C2","","","B5","B7","","","","",""};
		//得到头部map
		Map<String,Object> headMap=this.getHead(sheet, coordinateHead);
		map.put("U_FREIGHTINFO", headMap);
		
		
		List<Map> seas=new ArrayList();
		int rowNum=sheet.getLastRowNum();
		int index=0;
		String[] rowColStr=new String[]{
				"","","","","G20","",
				"","C20","D20","J20","I20",
				"","D20","E20","J20","I20",
				"","","","","",
				"","","","","",
				"","E20","F20","J20","I20"
		};
		String[] nameNum = { "K18", "L19", "M19", "N19", "O19", "P19", "Q19",
				"R19", "S19", "U19", "X19", "Y19", "Z19", "AA19", "AB19",
				"AC19", "AD19", "AG19", "AH19", "AI19", "AJ19", "AK19", "AL19",
				"AM19", "AN19", "AO19", "AP19", "AQ19", "AR19" };
		String[] amountNum={ "K20", "L20", "M20", "N20", "O20", "P20", "Q20",
				"R20", "S20", "U20", "X20", "Y20", "Z20", "AA20", "AB20",
				"AC20", "AD20", "AG20", "AH20", "AI20", "AJ20", "AK20", "AL20",
				"AM20", "AN20", "AO20", "AP20", "AQ20", "AR20" };
		String[] unitNum={ "K21", "L21", "M21", "N21", "O21", "P21", "Q21",
				"R21", "S21", "U21", "X21", "Y21", "Z21", "AA21", "AB21",
				"AC21", "AD21", "AG21", "AH21", "AI21", "AJ21", "AK21", "AL21",
				"AM21", "AN21", "AO21", "AP21", "AQ21", "AR21" };
		int sect=3;
		
		for(int i=20;i<rowNum;i++){
			Map<String,Object> seaMap=this.getSeaFreightByNum(sheet, rowColStr, index,rowNum);
			if(seaMap!=null){
				//查找一个seafreight下的所有记录 
				List<Map> itemsMap=this.getSeaFreightItemsByNum(sheet, nameNum, amountNum, unitNum, sect, index, rowNum);
				seaMap.put("U_SEAFREIGHTITEM", itemsMap);
				seas.add(seaMap);
				index++;
			}
		}
		map.put("U_SEAFREIGHT", seas);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]){
		@SuppressWarnings("unused")
		String xlsPath = new String("seaone.xls");//单测试
		String xlsPath2 = new String("seamore.xls");//多测试
		ReadSeaRoute test=new ReadSeaRoute();
		try {
			//test.getSeaOneInfo(xlsPath);
			Map map=null;
			//map=test.getSeaMoreInfo(xlsPath2);//多测试
			map=test.getSeaOneInfo(xlsPath);//单测试
			Map headMap=(HashMap<String, Object>) map.get("U_FREIGHTINFO");
			System.out.println("头部信息");
			for(int i=0;i<headMap.keySet().toArray().length;i++){
				System.out.println(headMap.keySet().toArray()[i]+":"+headMap.values().toArray()[i]);
			}
			List list=(ArrayList) map.get("U_SEAFREIGHT");
			System.out.println("共"+list.size()+"条信息");
			for(int i=0;i<list.size();i++){
				Map<String,Object> seaMapAll=(Map<String, Object>) list.get(i);
				for(int j=0;j<seaMapAll.keySet().toArray().length;j++){
					System.out.println(seaMapAll.keySet().toArray()[j]+":"+seaMapAll.values().toArray()[j]);
				}
				List itemList=(List) seaMapAll.get("U_SEAFREIGHTITEM");
				for(int m=0;m<itemList.size();m++){
					Map<String,Object> itemMap=(Map<String, Object>) itemList.get(m);
					for(int j=0;j<itemMap.keySet().toArray().length;j++){
						System.out.println(itemMap.keySet().toArray()[j]+":"+itemMap.values().toArray()[j]);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
