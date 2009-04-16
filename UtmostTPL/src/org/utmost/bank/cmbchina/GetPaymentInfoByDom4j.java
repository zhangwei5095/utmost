package org.utmost.bank.cmbchina;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.utmost.util.StringUtil;

public class GetPaymentInfoByDom4j {

	public HashMap parseXML(InputStream is) throws DocumentException {
		// InputStream is = StringUtil.parseInputStream(xml);
		SAXReader reader = new SAXReader();
		Document doc = reader.read(is);
		List childNodes = doc.selectNodes("//CMBSDKPGK/INFO/*");
		System.out.println("childNodes:" + childNodes.size());
		for (Object obj : childNodes) {
			Node childNode = (Node) obj;
			System.out.println(childNode.getName() + "-" + childNode.getText());
		}

		System.out.println("INFO---------------------->");
		childNodes = doc.selectNodes("//CMBSDKPGK/NTQPAYQYZ");
		System.out.println("childNodes:" + childNodes.size());
		for (Object obj : childNodes) {
			Node childNode = (Node) obj;
			for (Object o : childNode.selectNodes("*")) {
				Node ichildNode = (Node) o;
				System.out.println(ichildNode.getName() + "-"
						+ ichildNode.getText());
			}
			System.out.println("NTQPAYQYZ---------------------->");
		}
		return null;
	}

	public static void main(String[] args) {
		GetPaymentInfoByDom4j o = new GetPaymentInfoByDom4j();
		InputStream is = GetPaymentInfoByDom4j.class
				.getResourceAsStream("Test.xml");
		try {
			o.parseXML(is);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

}
