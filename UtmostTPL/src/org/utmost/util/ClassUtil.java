package org.utmost.util;

import java.lang.reflect.Method;
import java.util.HashMap;

public class ClassUtil {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		HashMap hm = new HashMap();
		hm.put("dd", "asdf");
		ClassUtil cu = new ClassUtil();
		ClassUtil.invokeMethod(cu, "testThis", hm);
	}

	public Object testThis(HashMap hm) {
		System.out.println("hm:" + hm);
		return hm;
	}

	/**
	 * @param methodObject
	 *            方法所在的对象
	 * @param methodName
	 *            方法名
	 * @param hm
	 *            方法名参数
	 */
	public static Object invokeMethod(Object methodObject, String methodName,
			Object obj) throws Exception {
		Class ownerClass = methodObject.getClass();
		Method method = null;
		if (obj != null) {
			method = ownerClass.getMethod(methodName, obj.getClass());
			return method.invoke(methodObject, obj);
		} else {
			method = ownerClass.getMethod(methodName);
			return method.invoke(methodObject);
		}
	}

}
