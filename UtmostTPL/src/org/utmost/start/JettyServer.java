package org.utmost.start;


import org.mortbay.jetty.Server;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
public class JettyServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		Connector conn = new SelectChannelConnector();
		conn.setPort(8080);
		server.setConnectors(new Connector[] { conn });
		WebAppContext webRoot = new WebAppContext();
		webRoot.setLogUrlOnStart(false);
		webRoot.setContextPath("/UtmostTPL");
		webRoot.setWar("WebRoot"); // 如需指定war目录，则相对应地在工程目录下建立一同名目录，否则启动时会产生异常
		server.setHandler(webRoot);
		server.start();
	}
}
