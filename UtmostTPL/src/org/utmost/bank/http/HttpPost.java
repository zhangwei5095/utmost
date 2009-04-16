package org.utmost.bank.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.utmost.bank.cmbchina.GetNewNoticeRequest;
import org.utmost.bank.cmbchina.GetPaymentInfoRequest;
import org.utmost.bank.cmbchina.GetSysInfoRequest;
import org.utmost.bank.cmbchina.ListAccountRequest;
import org.utmost.bank.cmbchina.ListModeRequest;
import org.utmost.bank.cmbchina.PaymentRequest;
import org.utmost.bank.cmbchina.PaymentResponse;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

public class HttpPost {
	private String urlStr;
	private URL url;
	private HttpURLConnection url_con;
	private String response_content;

	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}

	public String getResponse_content() {
		return response_content;
	}

	private void setResponse_content(String response_content) {
		this.response_content = response_content;
		// System.out.println("response_content:\n" + response_content);
	}

	public void send_url(String param) {
		try {
			url = new URL(urlStr);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			url_con.setDoOutput(true);
			// String param = "action=mobile&mobile=" + mobile_number;
			url_con.getOutputStream().write(param.getBytes());
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			InputStream in = url_con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			StringBuilder tempStr = new StringBuilder();
			String brs = null;
			while ((brs = br.readLine()) != null) {
				tempStr.append(brs + "\n");
				// tempStr.append(brs);
			}
			String s = new String(tempStr.toString().getBytes(), "GBK");
			setResponse_content(s);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (url_con != null)
				url_con.disconnect();
		}
	}

	// 格式化
	public static String processXML(String str) {
		str = "<?xml version=\"" + "1.0\"" + " encoding=\"GBK\"" + "?" + ">"
				+ "\n" + str + "\0";
		str = str.replaceAll("\n", "").replaceAll("\r", "")
				.replaceAll("\t", "").replaceAll("\b", "").replaceAll("\r\n",
						"").replaceAll("  ", "").replaceAll("__", "_");
		return str;
	}

	public static String post(String str) {
		HttpPost post = new HttpPost();
		post.setUrlStr("http://192.168.14.35:10000");
		// String str = PaymentRequest.getXML();
		System.out.println("post format msg:\n" + str);
		str = processXML(str);
		System.out.println("post msg:\n" + str);
		post.send_url(str);

		String response = post.getResponse_content();
		response = response.substring(response.indexOf("<?xml"), response
				.length());

		response = response.substring(36, response.length());
		System.out.println("response:\n" + response);
		return response;
	}

	public static void main(String[] args) throws FileNotFoundException,
			SAXException {
		HttpPost post = new HttpPost();
		post.setUrlStr("http://192.168.14.35:10000");
		// String str = PaymentRequest.getXML();
		String str = PaymentRequest.getXML();
		System.out.println("post format msg:\n" + str);
		str = processXML(str);
		// String str = ListAccountRequest.getXML();
		System.out.println("post msg:\n" + str);
		post.send_url(str);

		String response = post.getResponse_content();
		response = response.substring(response.indexOf("<?xml"), response
				.length());

		response = response.substring(36, response.length());
		System.out.println("response:\n" + response);

		// 反向
		// PaymentResponse pr = new PaymentResponse();
		// pr = (PaymentResponse) pr.processXStream().fromXML(response);
		// System.out.print(pr.getNTQPAYRQZ().getSQRNBR());

		// XStream xstream = new XStream();
		// xstream.alias("CMBSDKPGK", GetNewNoticeRequest.class);
		// GetNewNoticeRequest obj = (GetNewNoticeRequest) xstream
		// .fromXML(response);

		// SAXReader saxReader = new SAXReader();
		// try {
		// InputStream inputStream = new ByteArrayInputStream(response
		// .getBytes());
		// Document doc = saxReader.read(inputStream);
		//
		// System.out.println("doc:\n" + doc.asXML());
		// } catch (DocumentException e) {
		// e.printStackTrace();
		// }
	}
}
