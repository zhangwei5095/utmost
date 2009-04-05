package org.utmost.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Python控制台模拟类
 * 
 * @author wanglm
 * 
 */
@Service("CommService")
public abstract class CommService {
	@Autowired
	private DBSupport db;

	private static Log logger = LogFactory.getLog(CommService.class);

	public DBSupport getDb() {
		if (db == null) {
			logger.error("reload DBSupport for CommService in SpringContext");
			try {
				Thread.sleep(2000);// 同步线程时间差
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			DBSupport dbsupport = (DBSupport) SpringContext
					.getBean("DBSupport");
			db = dbsupport;
		}
		return db;
	}

	public void setDb(DBSupport db) {
		this.db = db;
	}

}
