package org.utmost.common;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.utmost.tpl.service.TPLTaskService;

/**
 * 未实现
 * 
 * @author wanglm
 * 
 */
@Component("UtmostCache")
public class UtmostCache extends CommService {
	private static Log logger = LogFactory.getLog(UtmostCache.class);
	private HashMap<String, List> cacheMap = new HashMap<String, List>();

	public UtmostCache() {
		// logger.info("Initialized UtmostCache");
		// cacheTPL();
		// logger.info("UtmostCache initialized");
	}

	/**
	 * 加载TPL数据映射
	 */
	public void cacheTPL() {
		cacheMap.put("U_TPL_TEMPLATEDATA", getDb()
				.findAll("U_TPL_TEMPLATEDATA"));

		cacheMap.put("U_TPL_TEMPLATEVIEW", getDb()
				.findAll("U_TPL_TEMPLATEVIEW"));
	}

	public List getList(String key) {
		return cacheMap.get(key);
	}
}
