package org.utmost.portal.auth.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;
import org.utmost.portal.service.TreeService;
import org.utmost.util.StringUtil;

@Service("AuthService")
public class AuthService {
	@Autowired
	private AutoService autoService;
	@Autowired
	private TreeService treeService;

	/**
	 * 根据loginname获取用户权限信息
	 * 
	 * @return
	 */
	public Object findAuthInfo(String loginname) {

		return null;
	}

	/**
	 * 获取功能树
	 * 
	 * @return
	 * @throws DocumentException
	 */
	public String findFuncTreeByRole(String rolecode, String funccode)
			throws DocumentException {
		// 根据funccode获取功能树
		String funcTreeAll = findFuncTreeByCode(funccode);
		// 根据rolecode获取func权限
		List list = autoService
				.findByHql("from U_PORTAL_REFROLEFUNC v where v.rolecode='"
						+ rolecode + "'");
		// 过滤功能树
		// funcTreeAll="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+funcTreeAll;
		String funcTree = filterTreeByRole(funcTreeAll, list, null);
		return funcTree;
	}

	/**
	 * 过滤功能树
	 * 
	 * @param funcTreeAll
	 * @param list
	 * @return
	 * @throws DocumentException
	 */
	private String filterTreeByRole(String funcTreeAll, List<HashMap> rolelist,
			List childlist) throws DocumentException {
		List childNodes;
		if (childlist == null && (!funcTreeAll.equals(""))) {
			Document document = DocumentHelper.parseText(funcTreeAll);
			childNodes = document.selectNodes("/node");
		} else {
			childNodes = childlist;
		}
		Element element = null;
		if (childNodes != null)
			for (Object obj : childNodes) {
				element = (Element) obj;
				String funcuuid = element.attributeValue("uuid");
				boolean flag = false;// 判断是否存在
				for (HashMap map : rolelist) {
					if (map.get("funcuuid").toString().equals(funcuuid)) {
						// System.out
						// .println("-" + element.attributeValue("nodecode")
						// + "---->" + funcuuid + "--->"
						// + map.get("funcuuid"));
						flag = true;
					}
				}
				if (!flag && element.getParent() != null)
					element.getParent().remove(element);//
				List list = element.elements();
				if (list.size() != 0 && list != null)
					filterTreeByRole(funcTreeAll, rolelist, list);
			}
		if (element != null)
			return element.asXML();
		else
			return null;
	}

	/**
	 * 根据funccode获取功能树
	 */
	public String findFuncTreeByCode(String funccode) {
		HashMap main = new HashMap();
		main.put("tableName", "U_PORTAL_FUNC");
		main.put("rootField", "funccode");
		main.put("rootValue", funccode);
		main.put("idField", "uuid");
		main.put("pidField", "pid");
		HashMap kv = new HashMap();
		kv.put("uuid", "uuid");
		kv.put("funccode", "nodecode");
		kv.put("funcname", "nodename");
		kv.put("pid", "pid");
		kv.put("state", "state");
		kv.put("funcpath", "module");
		String tree = treeService.getTree(main, kv);
		return tree;
	}

}
