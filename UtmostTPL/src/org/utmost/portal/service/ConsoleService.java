package org.utmost.portal.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service("ConsoleService")
public class ConsoleService {

	public ConsoleService(InputStream[] inStreams) {
		for (int i = 0; i < inStreams.length; ++i)
			startConsoleReaderThread(inStreams[i]);
	} // ConsoleTextArea()

	public ConsoleService() {
		old = System.out;// 源
		olderr = System.err;// 源
	} // ConsoleTextArea()

	public void startConsole() throws IOException {
		if (!istrue) {
			istrue = true;
			final LoopedStreams ls = new LoopedStreams();
			// 重定向System.out和System.err
			PrintStream ps = new PrintStream(ls.getOutputStream());
			System.setOut(ps);
			System.setErr(ps);
			startConsoleReaderThread(ls.getInputStream());
		}
	}

	private static StringBuffer rs = new StringBuffer();

	public void stopConsole() {
		if (istrue) {
			istrue = false;
			System.setOut(old);
			System.setErr(olderr);
		}
	}

	private static Boolean istrue = false;//
	private static PrintStream old = null;
	private static PrintStream olderr = null;

	public String getConsoleInfo() {
		if (istrue)
			return rs.toString();
		else
			return "Console is closed!";
	}

	public void clear() {
		rs = new StringBuffer();
	}

	private void startConsoleReaderThread(InputStream inStream) {
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				inStream));
		new Thread(new Runnable() {
			public void run() {
				StringBuffer sb = new StringBuffer();
				try {
					String s = "";
					while ((s = br.readLine()) != null) {
						rs.append(s + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	} // startConsoleReaderThread()

	// 该类剩余部分的功能是进行测试
	public static void main(String[] args) {
		ConsoleService consoleService = null;
		consoleService = new ConsoleService();
	} // main()

	// private static void startWriterTestThread(final String name,
	// final PrintStream ps, final int delay, final int count) {
	// new Thread(new Runnable() {
	// public void run() {
	// for (int i = 1; i <= 100000; ++i) {
	// ps.println("***" + name + ", hello !, i=" + i);
	// try {
	// Thread.sleep(delay);
	// } catch (InterruptedException e) {
	// }
	// }
	// }
	// }).start();
	// } // startWriterTestThread()
}
