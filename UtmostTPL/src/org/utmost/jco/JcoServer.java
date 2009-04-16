package org.utmost.jco;

import java.util.ArrayList;

import org.utmost.jco.handler.GPNetBankHandler;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.server.DefaultServerHandlerFactory;
import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerContextInfo;
import com.sap.conn.jco.server.JCoServerErrorListener;
import com.sap.conn.jco.server.JCoServerExceptionListener;
import com.sap.conn.jco.server.JCoServerFactory;
import com.sap.conn.jco.server.JCoServerFunctionHandler;
import com.sap.conn.jco.server.JCoServerState;
import com.sap.conn.jco.server.JCoServerStateChangedListener;

public class JcoServer {
	public String SERVER_NAME = "SERVER";

	// private ArrayList funname = null;
	private JCoServerFunctionHandler handler = null;

	public JcoServer(JCoServerFunctionHandler _handler) {
		// this.funname = _funname;
		this.handler = _handler;
	}

	public void runServer() {
		try {
			JCoServer server = null;
			try {
				server = JCoServerFactory.getServer(SERVER_NAME);
			} catch (JCoException ex) {
				System.out.println("Unable to create the server " + SERVER_NAME
						+ ", because of " + ex.getMessage());
				ex.printStackTrace();
				// throw new RuntimeException("Unable to create the server "
				// + SERVER_NAME + ", because of " + ex.getMessage(), ex);
			}

			DefaultServerHandlerFactory.FunctionHandlerFactory factory = new DefaultServerHandlerFactory.FunctionHandlerFactory();
			factory.registerHandler("STFC_CONNECTION", handler);
			factory.registerHandler("Z_FI_NB001", handler);
			factory.registerHandler("Z_FI_NB002", handler);
			server.setCallHandlerFactory(factory);

			MyThrowableListener eListener = new MyThrowableListener();
			server.addServerErrorListener(eListener);
			server.addServerExceptionListener(eListener);

			MyStateChangedListener slistener = new MyStateChangedListener();
			server.addServerStateChangedListener(slistener);

			server.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	static class MyThrowableListener implements JCoServerErrorListener,
			JCoServerExceptionListener {

		public void serverErrorOccurred(JCoServer jcoServer,
				String connectionId, JCoServerContextInfo serverCtx, Error error) {
			System.out.println(">>> Error occured on "
					+ jcoServer.getProgramID() + " connection " + connectionId);
			error.printStackTrace();
		}

		public void serverExceptionOccurred(JCoServer jcoServer,
				String connectionId, JCoServerContextInfo serverCtx,
				Exception error) {
			System.out.println(">>> Error occured on "
					+ jcoServer.getProgramID() + " connection " + connectionId);
			error.printStackTrace();
		}
	}

	static class MyStateChangedListener implements
			JCoServerStateChangedListener {
		public void serverStateChangeOccurred(JCoServer server,
				JCoServerState oldState, JCoServerState newState) {
			System.out.println("Server state changed from "
					+ oldState.toString() + " to " + newState.toString()
					+ " on server with program id " + server.getProgramID());
		}
	}

	public static void main(String[] args) {
		GPNetBankHandler gpnbh = new GPNetBankHandler();
		JcoServer o = new JcoServer(gpnbh);
		o.runServer();
	}
}
