package org.utmost.tpl.test.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.utmost.common.DBSupport;

@Repository("DBTest")
public class DBTest {
	public void saveDB(DBSupport _db) {
		DBSupport db = _db;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid", "sss");
		map.put("nodecode", "娃哈哈ha");
		db.save("U_TPL_TEMPLATEDATA", map);

	}
}
