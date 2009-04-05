package org.utmost.tpl.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

@Repository("JDBCTest")
public class JDBCTest {
	public String jdbcDriver = "";
	public String url = "";
	public String user = "";
	public String password = "";
	public Connection conn = null;
	public int[] NFSJNL = null;
	public ArrayList NFSJNLName = null;
	public ArrayList NFSJNLComm = null;
	public String insterSQLNFSJNL = "";

	public int[] PFSJNL = null;
	public ArrayList PFSJNLName = null;
	public ArrayList PFSJNLComm = null;
	public String insterSQLPFSJNL = "";

	public ResultSet rs = null;
	Statement st = null;

	public boolean getConnect() {
		try {

			Class.forName(getJdbcDriver()).newInstance();
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("数据库连接成功!!");
			// "select * FROM sysibm.tables  where table_schema ='DB2ADMIN'";
			// //获取用户数据库表

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public void processNFSJNL() {
		try {
			DatabaseMetaData meta = conn.getMetaData();
			rs = meta.getColumns(null, null, "NFSJNL", null);
			NFSJNL = new int[105];
			NFSJNLName = new ArrayList();
			NFSJNLComm = new ArrayList();
			int i = 0;
			insterSQLNFSJNL = "insert into NFSJNL(";
			while (rs.next()) {
				// System.out.println(rs.getString("COLUMN_NAME")); // 列名称
				// System.out.println(rs.getString("REMARKS")+",类型："+rs.getString("DATA_TYPE")+",类型名称:"+rs.getString("TYPE_NAME")+"列的大小:"+rs.getString("COLUMN_SIZE"));//来自
				// java.sql.Types 的 SQL 类型
				if (i == 0)
					NFSJNL[i] = rs.getInt("COLUMN_SIZE");
				else
					NFSJNL[i] = NFSJNL[i - 1] + rs.getInt("COLUMN_SIZE");
				// System.out.println(rs.getString("COLUMN_NAME")+":"+
				// NFSJNL[i]);
				NFSJNLName.add(rs.getString("COLUMN_NAME"));
				NFSJNLComm.add(rs.getString("REMARKS"));
				if (i < 83)
					insterSQLNFSJNL += rs.getString("COLUMN_NAME") + ",";
				i++;

				// System.out.println(rs.getString("TYPE_NAME"));//TYPE_NAME
				// 类型名称
				// System.out.println(rs.getString("COLUMN_SIZE"));//COLUMN_SIZE
				// 列的大小
				// System.out.println(rs.getString("REMARKS"));//列的注释
			}
			insterSQLNFSJNL += "UNISEQNO) ";
			System.out.println(insterSQLNFSJNL);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void process__PFSJNL() {
		try {
			DatabaseMetaData meta = conn.getMetaData();
			rs = meta.getColumns(null, null, "PFSJNL", null);
			PFSJNL = new int[113];
			PFSJNLName = new ArrayList();
			PFSJNLComm = new ArrayList();
			int i = 0;
			insterSQLPFSJNL = "insert into PFSJNL(";
			while (rs.next()) {
				// System.out.println(rs.getString("COLUMN_NAME")); // 列名称
				// System.out.println(rs.getString("REMARKS")+",类型："+rs.getString("DATA_TYPE")+",类型名称:"+rs.getString("TYPE_NAME")+"列的大小:"+rs.getString("COLUMN_SIZE"));//来自
				// java.sql.Types 的 SQL 类型
				if (i == 0)
					PFSJNL[i] = rs.getInt("COLUMN_SIZE");
				else
					PFSJNL[i] = PFSJNL[i - 1] + rs.getInt("COLUMN_SIZE");
				// System.out.println(rs.getString("COLUMN_NAME")+":"+
				// NFSJNL[i]);
				PFSJNLName.add(rs.getString("COLUMN_NAME"));
				PFSJNLComm.add(rs.getString("REMARKS"));
				if (i < 93)
					insterSQLPFSJNL += rs.getString("COLUMN_NAME") + ",";
				i++;

				// System.out.println(rs.getString("TYPE_NAME"));//TYPE_NAME
				// 类型名称
				// System.out.println(rs.getString("COLUMN_SIZE"));//COLUMN_SIZE
				// 列的大小
				// System.out.println(rs.getString("REMARKS"));//列的注释
			}
			insterSQLPFSJNL += "UNISEQNO) ";
			System.out.println(insterSQLPFSJNL);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String initConnect(HashMap<String, Object> dbinfo) {
		System.out.println("dbinfo==" + dbinfo);
		this.setJdbcDriver(dbinfo.get("dbVendor").toString());
		this.setPasswrod(dbinfo.get("database_password").toString());
		this.setUser(dbinfo.get("database_user").toString());
		this.setUrl("jdbc:db2://127.0.0.1:50000/utmosthb");
		String strReturn = "";
		if (this.getConnect()) {
			strReturn = "true";
		} else {
			strReturn = "false";
		}
		return strReturn;
	}

	public String getTablesInfo() {
		try {
			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("select * FROM sysibm.tables  where table_schema ='DB2ADMIN'");
			String strRet = "";
			strRet = "<xml>";
			while (rs.next()) {
				strRet += "<table tablename=\"" + rs.getString("table_name")
						+ "\" value=\"" + rs.getString("table_name") + "\"/>";

			}
			strRet += "</xml>";
			System.out.println(strRet);

			return strRet;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public static void main(String[] args) throws IOException, SQLException {
		JDBCTest jt = new JDBCTest();
		jt.setJdbcDriver("com.ibm.db2.jcc.DB2Driver");
		jt.setPasswrod("db2admin");
		jt.setUrl("jdbc:db2://127.0.0.1:50000/utmosthb");
		jt.setUser("db2admin");
		jt.getConnect();
		jt.processNFSJNL();
		jt.readLineNFSJNL();
		jt.process__PFSJNL();
		jt.readLine__PFSJNL();
	}

	public void readLineNFSJNL() throws IOException, SQLException {
		BufferedReader br = new BufferedReader(new FileReader(
				"c://demo//e-20081210-nfsjnl.bin"));// e-20081210-nfsjnl.bin
		String line = null;
		String tempStr = "";

		byte[] tempB = null;
		String sqlt = "";

		if (st == null) {
			st = conn.createStatement();
		}
		Date begDate = new Date();

		int ii = 0;
		while ((line = br.readLine()) != null) {
			tempB = line.getBytes();
			System.out.println(line);

			sqlt = insterSQLNFSJNL + "values(";
			for (int i = 0; i < 83; i++) {
				String value = "";
				int benInd = 0;
				int endInd = 0;
				if (i == 0) {
					benInd = 0;
					endInd = NFSJNL[i];

				} else {
					benInd = NFSJNL[i - 1];
					endInd = NFSJNL[i];

				}
				byte[] tempA = new byte[endInd - benInd];
				for (int j = 0; j < endInd - benInd; j++) {
					tempA[j] = tempB[benInd + j];
				}

				// System.out.println(new String(tempA,"GBK"));
				// tempB.
				value = new String(tempA, "GBK");
				sqlt += "'" + value.trim() + "',";
				System.out.println("第" + (i + 1) + "的数据，开始位置:" + benInd
						+ "，结束位置:" + endInd + ",长度为：" + (endInd - benInd)
						+ NFSJNLComm.get(i) + ":" + NFSJNLName.get(i) + ":'"
						+ value + "'");

			}
			sqlt += "'"
					+ java.util.UUID.randomUUID().toString()
							.replaceAll("-", "") + "')";
			// System.out.println("第"+(ii++)+"记录");
			st.execute(sqlt);
			// break;
			// System.out.println(line);
		}
		System.out.println(begDate);
		System.out.println(new Date());

	}

	public void readLine__PFSJNL() throws IOException, SQLException {
		BufferedReader br = new BufferedReader(new FileReader(
				"c://demo//e-20081210-pfsjnl.bin"));
		String line = null;
		String tempStr = "";

		byte[] tempB = null;
		String sqlt = "";

		if (st == null) {
			st = conn.createStatement();
		}
		Date begDate = new Date();

		int ii = 0;
		while ((line = br.readLine()) != null) {
			tempB = line.getBytes();

			sqlt = insterSQLPFSJNL + "values(";
			for (int i = 0; i < 93; i++) {
				String value = "";
				int benInd = 0;
				int endInd = 0;
				if (i == 0) {
					benInd = 0;
					endInd = PFSJNL[i];

				} else {
					benInd = PFSJNL[i - 1];
					endInd = PFSJNL[i];

				}
				byte[] tempA = new byte[endInd - benInd];
				for (int j = 0; j < endInd - benInd; j++) {
					tempA[j] = tempB[benInd + j];
				}

				// System.out.println(new String(tempA,"GBK"));
				// tempB.
				value = new String(tempA, "GBK");
				sqlt += "'" + value.trim() + "',";
				// System.out.println("第"+(i+1)+"的数据，开始位置:"+benInd+"，结束位置:"+endInd+",长度为："+(endInd-benInd)+NFSJNLComm.get(i)+":"+NFSJNLName.get(i)+":'"+value+"'");

			}
			sqlt += "'"
					+ java.util.UUID.randomUUID().toString()
							.replaceAll("-", "") + "')";
			System.out.println("第" + (ii++) + "记录" + sqlt);
			st.execute(sqlt);

			// System.out.println(line);
		}
		System.out.println(begDate);
		System.out.println(new Date());

	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getJdbcDriver() {
		return this.jdbcDriver;
	}

	public void setUrl(String url) {
		this.url = url;

	}

	public String getUrl() {
		return url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return this.user;
	}

	public void setPasswrod(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}
}
