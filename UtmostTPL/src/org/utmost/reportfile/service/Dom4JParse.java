package org.utmost.reportfile.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.utmost.util.PathUtil;
import org.utmost.util.StringUtil;

public class Dom4JParse {
	public static void main(String[] args) throws NumberFormatException,
			Exception {
		// ArrayList datalist = new ArrayList();
		// ArrayList node1 = new ArrayList();
		// ArrayList node2 = new ArrayList();
		// ArrayList node3 = new ArrayList();
		// for (int x = 0; x < 20; x++) {
		// HashMap m = new HashMap();
		// m.put("A", "AAA");
		// m.put("B", "BBB");
		// m.put("C", "总共");
		// node1.add(m);
		// node2.add(m);
		// node3.add(m);
		// }
		// datalist.add(node1);
		// datalist.add(node2);
		// datalist.add(node3);
		// Dom4JParse o = new Dom4JParse();
		// o.buildReportFile("Template_Demo.xml", "d:/ddc.txt", datalist);
	}

	public String buildReportFile(String templatename, String targetname,
			ArrayList datalist) throws NumberFormatException, Exception {
		// String filename = "Template_Demo.xml";
		String templatepath = "org/utmost/reportfile/template/";
		String classpath = PathUtil.getClassPath();
		File file = new File(classpath + templatepath + templatename);
		SAXReader reader = new SAXReader();
		Document doc = reader.read(file);
		List childNodes = doc.selectNodes("//xml/node");
		// System.out.println("//xml/node --> " + childNodes.size());
		if (datalist.size() != childNodes.size())
			throw new Exception("Error:datalist.size() != childNodes.size()!");
		FileWriter fw = new FileWriter(targetname);
		int count = 0;
		for (Object obj : childNodes) {
			Node childNode = (Node) obj;
			List columnNodes = childNode.selectNodes("columns/column");

			ArrayList maplist = (ArrayList) datalist.get(count);
			for (int x = 0; x < maplist.size(); x++) {
				HashMap map = (HashMap) maplist.get(x);
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < columnNodes.size(); i++) {
					Node columnNode = (Node) columnNodes.get(i);
					String headerText = columnNode.valueOf("@headerText");
					String dataField = columnNode.valueOf("@dataField");
					String type = columnNode.valueOf("@type");
					String length = columnNode.valueOf("@length");
					String fill = columnNode.valueOf("@fill");
					String position = columnNode.valueOf("@position");

					String fillDataField = (String) map.get(dataField);
					if (fillDataField == null)
						continue;
					int startOrend = 0;
					if (position.equals("before")) {
						startOrend = 1;
					} else if (position.equals("after")) {
						startOrend = -1;
					} else {
						throw new Exception("Error");
					}
					String col = StringUtil.fChar(fillDataField, fill, Integer
							.parseInt(length), startOrend);
					sb.append(col);
				}
				sb.append("\n");
				fw.write(sb.toString());
			}
			count++;
		}
		fw.close();
		System.out.println("buildReportFile success!");
		return targetname;
	}
}
