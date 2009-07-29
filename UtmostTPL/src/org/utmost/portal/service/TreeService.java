package org.utmost.portal.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.utmost.common.CommService;
import org.utmost.common.SpringContext;

/**
 * Tree to Xml 结构生成工具
 * 
 * @author fss
 * 
 */
@Service("TreeService")
public class TreeService extends CommService {
	private static Log logger = LogFactory.getLog(TreeService.class);

	// private StringBuffer sb =null;

	/**
	 * 
	 * @param main
	 *            为主信息 kv 为字段映射
	 * @return
	 */
	public String getTree(HashMap main, HashMap kv) {
		StringBuffer sb = new StringBuffer();
		String tableName = (String) main.get("tableName");// 数据表名

		AutoService as = (AutoService) SpringContext.getBean("AutoService");
		List tableList = as.findByHql("from " + tableName);
		if (tableList.size() > 0) {
			processXML(tableList, main, kv, sb);
		} else {
			// sb.append("<node> </node>");
			sb.append("<node nodename=\"" + "null" + "\"> </node>");
		}
		// System.out.println("sb.toString():" + sb.toString());
		return sb.toString();
	}

	private String processXML(List tableList, HashMap main, HashMap kv,
			StringBuffer sb) {
		String rootCode = (String) main.get("rootField");// 根节点编码字段
		String rootValue = (String) main.get("rootValue");// 根节点编码Value
		String idField = (String) main.get("idField");// id字段名(UUID)
		String pidField = (String) main.get("pidField");// pid字段名

		for (int i = 0; i < tableList.size(); i++) {
			HashMap oM = (HashMap) tableList.get(i);
			// System.out.println(oM);
			String nodeValue = (String) oM.get(rootCode);// 节点编码的值
			if (nodeValue.equalsIgnoreCase(rootValue)) {// 找到入口节点
				String nodeid = (String) oM.get(idField);// 节点ID
				String nodepid = (String) oM.get(pidField);// 父节点ID
				// System.out.println(nodeid);
				sb.append("<node ");
				Iterator it = kv.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Entry) it.next();
					String fieldname = entry.getValue().toString();
					sb.append(" " + fieldname + "=\""
							+ oM.get(entry.getKey().toString()) + "\" ");
					// System.out.println(entry.getValue().toString());
				}
				sb.append(">" + "\n");
				// System.out.println(sb);
				for (int j = 0; j < tableList.size(); j++) {
					HashMap childM = (HashMap) tableList.get(j);
					String cPid = (String) childM.get(pidField);
					System.out.println(cPid + "--" + pidField + "--" + nodeid
							+ "--->" + childM);
					if (cPid != null && cPid.equalsIgnoreCase(nodeid)) {// 迭代打印子节点
						HashMap cMain = new HashMap(main);
						cMain.put("rootValue", (String) childM.get(rootCode));
						processXML(tableList, cMain, kv, sb);
					}
				}
				sb.append("</node>" + "\n");
				// System.out.println(sb);
			}
		}
		// System.out.println(sb);
		return sb.toString();
	}

	public void deleteTree(HashMap map) {
		AutoService as = (AutoService) SpringContext.getBean("AutoService");
		String tableName = (String) map.get("tableName");
		String idField = (String) map.get("idField");// id字段名(UUID)
		String pidField = (String) map.get("pidField");// pid字段名
		String idValue = (String) map.get("idValue");// uuid
		String hql = "from " + tableName + " v where v." + idField + "='"
				+ idValue + "'";
		List tableList = as.findByHql(hql);
		for (int x = 0; x < tableList.size(); x++) {
			HashMap m = (HashMap) tableList.get(x);
			// String pid = (String) m.get(idField);
			this.deleteTreeNode(map, idValue);
		}
		as.deleteAll(tableName, tableList);
	}

	public void deleteTreeNode(HashMap map, String _pid) {
		AutoService as = (AutoService) SpringContext.getBean("AutoService");
		String tableName = (String) map.get("tableName");
		String idField = (String) map.get("idField");// id字段名(UUID)
		String pidField = (String) map.get("pidField");// pid字段名
		String idValue = (String) map.get("idValue");// uuid
		String hql = "from " + tableName + " v where v." + pidField + "='"
				+ _pid + "'";
		System.out.println("deleteTreeNode-->" + hql);
		List tableList = as.findByHql(hql);
		for (int x = 0; x < tableList.size(); x++) {
			HashMap m = (HashMap) tableList.get(x);
			String uuid = (String) m.get(idField);
			deleteTreeNode(map, uuid);
			// as.delete(tableName, m);
		}
		as.deleteAll(tableName, tableList);
	}

	public static void main(String[] args) {
		HashMap main = new HashMap();
		main.put("tableName", "U_FUN");
		main.put("rootField", "funcode");
		main.put("rootValue", "portal_11");
		main.put("idField", "uuid");
		main.put("pidField", "parentid");
		HashMap kv = new HashMap();
		kv.put("uuid", "uuid");
		kv.put("funcode", "nodecode");
		kv.put("funname", "nodename");
		kv.put("parentid", "parentid");
		TreeService ts = new TreeService();
		String xmltree = ts.getTree(main, kv);
		System.out.println("!-.-" + xmltree);
	}
}
