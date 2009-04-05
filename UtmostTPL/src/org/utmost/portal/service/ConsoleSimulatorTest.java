package org.utmost.portal.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;

@Service("ConsoleSimulatorTest")
public class ConsoleSimulatorTest implements Runnable {
	public ConsoleSimulatorTest() {

	}

	private volatile boolean isStop = false;

	private static final int INFO = 0;

	private static final int ERROR = 1;

	private InputStream is;
	private OutputStream ops;

	private int type;

	public ConsoleSimulatorTest(InputStream is, OutputStream os, int type) {
		this.is = is;
		this.type = type;
		this.ops = os;
	}


	public static String consoleContext = "";

	public static void clearContext() {
		consoleContext = "";
	}

	public void run() {
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		String s;
		try {
			while ((!isStop) && (s = reader.readLine()) != null) {
				if (s.length() != 0) {
					if (type == INFO) {
						System.out.println("INFO> " + s);
						consoleContext += "INFO> " + s + "\n";
						ops.write("dir /s".getBytes());
						ops.flush();
					} else {
						System.err.println("ERROR> " + s);
						consoleContext += "ERROR> " + s + "\n";
					}

					try {
						Thread.sleep(10);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void stop() {
		isStop = true;
	}

	public static String runCmd(String cmd) throws InterruptedException,
			IOException {
		String url = ConsoleSimulatorTest.class.getResource("").getPath();
		String command = cmd;
		System.out.println("command:\n" + command);

		Process child = Runtime.getRuntime().exec(command);

		OutputStream os = child.getOutputStream();
		InputStream stdin = child.getInputStream();
		InputStream stderr = child.getErrorStream();

		// 启动新线程截获命令行打印数据
		Thread tIn = new Thread(new ConsoleSimulatorTest(stdin, os, INFO));
		Thread tErr = new Thread(new ConsoleSimulatorTest(stderr, os, ERROR));
		tIn.start();
		tErr.start();
		int result = child.waitFor();
		tIn.join();
		tErr.join();
		if (result == 0) {
			// System.out.println("SUCCESS! ");
		} else {
			// System.out.println("FAILED! ");
		}
		return consoleContext;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {
		System.out.println(ConsoleSimulatorTest.runCmd("cmd /c dir"));
	}
}
