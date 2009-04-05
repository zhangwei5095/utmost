package org.utmost.tpl.test;

import java.util.Date;
import java.util.HashMap;

public class testHashMap {
	public static void main(String[] args) {
		long st = System.currentTimeMillis();
		HashMap map = new HashMap();
		for (int x = 0; x < 100000; x++) {
			map.put(java.util.UUID.randomUUID().toString(), new Date());
		}
		long et = System.currentTimeMillis();
		System.out.println((et - st));
	}
}
