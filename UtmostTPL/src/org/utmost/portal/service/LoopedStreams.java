package org.utmost.portal.service;

import java.io.*;

public class LoopedStreams {
	private PipedOutputStream pipedOS = new PipedOutputStream();
	private boolean keepRunning = true;
	private ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream() {
		public void close() {
			keepRunning = false;
			try {
				super.close();
				pipedOS.close();
			} catch (IOException e) {
				// 记录错误或其他处理
				// 为简单计，此处我们直接结束
				// System.exit(1);
				e.printStackTrace();
			}
		}
	};
	private PipedInputStream pipedIS = new PipedInputStream() {
		public void close() {
			keepRunning = false;
			try {
				super.close();
			} catch (IOException e) {
				// 记录错误或其他处理
				// 为简单计，此处我们直接结束
				// System.exit(1);
				e.printStackTrace();
			}
		}
	};

	public LoopedStreams() throws IOException {
		pipedOS.connect(pipedIS);
		startByteArrayReaderThread();
	} // LoopedStreams()

	public InputStream getInputStream() {
		return pipedIS;
	} // getInputStream()

	public OutputStream getOutputStream() {
		return byteArrayOS;
	} // getOutputStream()

	private void startByteArrayReaderThread() {
		new Thread(new Runnable() {
			public void run() {
				while (keepRunning) {
					// 检查流里面的字节数
					if (byteArrayOS.size() > 0) {
						byte[] buffer = null;
						synchronized (byteArrayOS) {
							buffer = byteArrayOS.toByteArray();
							byteArrayOS.reset(); // 清除缓冲区
						}
						try {
							// 把提取到的数据发送给PipedOutputStream
							pipedOS.write(buffer, 0, buffer.length);
						} catch (IOException e) {
							// 记录错误或其他处理
							// 为简单计，此处我们直接结束
							// System.exit(1);
							e.printStackTrace();
						}
					} else
						// 没有数据可用，线程进入睡眠状态
						try {
							// 每隔1秒查看ByteArrayOutputStream检查新数据
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}
		}).start();
	} // startByteArrayReaderThread()
} // LoopedStreams