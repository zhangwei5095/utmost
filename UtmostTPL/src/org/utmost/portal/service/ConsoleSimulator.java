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

@Service("ConsoleSimulator")
public class ConsoleSimulator implements Runnable {
	public ConsoleSimulator() {

	}

	private volatile boolean isStop = false;

	private static final int INFO = 0;

	private static final int ERROR = 1;

	private InputStream is;

	private int type;

	public ConsoleSimulator(InputStream is, int type) {
		this.is = is;
		this.type = type;
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

	public static String runPython(String name, String scripts)
			throws InterruptedException, IOException {
		String url = ConsoleSimulator.class.getResource("").getPath();
		url = url.replaceAll("%20", " ");
		String classesurl = url.substring(1, url.indexOf("/classes/"))
				+ "/classes/";
		String liburl = url.substring(1, url.indexOf("/classes/"))
				+ "/lib/jython-2.2.1.jar";
		String liburls = "";
		String filespath = url.substring(1, url.indexOf("/classes/")) + "/lib/";
		File f = new File(filespath);
		System.out.println(filespath);
		File[] files = f.listFiles();
		System.out.println("files:" + files);
		for (File fo : files) {
			liburls += filespath + fo.getName() + ";";
			// System.out.println(fo.getName());
		}

		// Properties pro = System.getProperties();
		// System.out.println("Properties:\n"+pro);
		String scripsurl = url.substring(1, url.indexOf("/classes/"))
				+ "/classes/org/utmost/portal/scripts/";
		// System.out.println("url:" + url);
		// System.out.println("classesurl:" + classesurl);
		// System.out.println("liburl:" + liburl);

		// scripts=scripts.replaceAll("\n", ";");
		// scripts=scripts.replaceAll(";+", ";");

		String filename = scripsurl + name;
		FileWriter fw = new FileWriter(new File(filename));
		fw.write(scripts);
		fw.close();

		String command = "java -classpath \"" + classesurl + "\";\"" + liburls
				+ "\" org.utmost.portal.service.RuleEngine \"" + filename
				+ "\"";
		System.out.println("command:\n" + command);

		Process child = Runtime.getRuntime().exec(command);

		OutputStream os = child.getOutputStream();
		InputStream stdin = child.getInputStream();
		InputStream stderr = child.getErrorStream();
		//启动新线程截获命令行打印数据
		Thread tIn = new Thread(new ConsoleSimulator(stdin, INFO));
		Thread tErr = new Thread(new ConsoleSimulator(stderr, ERROR));
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
		System.out.println(ConsoleSimulator.runPython("DD.py", "print 22+33"
				+ ";print 'asdddf'"));
	}
}
