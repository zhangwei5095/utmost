package org.utmost.portal.service;

import java.io.Console;

public class TestConsole {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Console console=System.console();
		System.out.println(""+console);
		if(console!=null) {
			String user=console.readLine("Enter username:").toString();
			
			System.out.println("user:"+user);
		}
	}

}
