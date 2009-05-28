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

	public ConsoleService() throws IOException {
		final LoopedStreams ls = new LoopedStreams();
		// 重定向System.out和System.err
		PrintStream ps = new PrintStream(ls.getOutputStream());
		System.setOut(ps);
		System.setErr(ps);
		startConsoleReaderThread(ls.getInputStream());
	} // ConsoleTextArea()

	private static StringBuffer rs = new StringBuffer();

	public String getConsoleInfo(HashMap map) {
		return rs.toString();
	}

	public void clear(HashMap map) {
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
		try {
			consoleService = new ConsoleService();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 启动几个写操作线程向
		// System.out和System.err输出
		// startWriterTestThread("写操作线程 #1", System.err, 20, 50);
		// startWriterTestThread("写操作线程 #2", System.out, 500, 50);
		// startWriterTestThread("写操作线程 #3", System.out, 200, 50);
		// startWriterTestThread("写操作线程 #4", System.out, 100, 50);
		// startWriterTestThread("写操作线程 #5", System.err, 50, 50);
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
