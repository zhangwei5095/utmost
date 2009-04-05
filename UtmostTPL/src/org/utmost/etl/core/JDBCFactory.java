package org.utmost.etl.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class JDBCFactory {
	public static DataSource getDataSource() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"/conf/etl/applicationContext.xml");
		DataSource dataSource = (DataSource) ctx.getBean("dataSource_mysql");
		return dataSource;
	}

	public static void main(String[] args) {

		Connection conn = JDBCFactory
				.getConnection(JDBCFactory.getDataSource());
		Statement stat = null;
		try {
			// stat = conn.createStatement();
			// System.out.println(stat);
			// String sql = "select * from U_TPL_VALIDATOR";
			// stat.execute(sql);
			// ResultSet result = stat.executeQuery(sql);
			// ResultSetMetaData rsmd = result.getMetaData();
			// System.out.println(result);
			// int cols = rsmd.getColumnCount();
			// while (result.next()) {
			// for (int i = 1; i <= cols; i++) {
			// System.out.println(rsmd.getColumnName(i) + ":"
			// + result.getObject(i));
			// }
			// }
			DatabaseMetaData dmd = conn.getMetaData();
			ResultSet tableRet = dmd.getTables(null, "%", "%",
					new String[] { "TABLE" });
			while (tableRet.next())
				System.out.println(tableRet.getString("TABLE_NAME"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(conn);
	}

	public static Connection getConnection(DataSource dataSource) {
		Connection conn = DataSourceUtils.getConnection(dataSource);
		return conn;
	}
}
