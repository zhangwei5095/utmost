package org.utmost.portal.service;

import org.springframework.stereotype.Service;

@Service("TestForUnis")
public class TestForUnis {

	public String helloUnis(String str) {
		System.out.println("helloUnis:" + str);
		return "helloUnis" + str;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
