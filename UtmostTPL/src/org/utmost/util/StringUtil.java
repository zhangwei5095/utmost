package org.utmost.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
	 */
	private StringUtil() {
	}

	/**
	 * 判断有几个中文字符
	 * 
	 * @param str
	 * @return
	 */
	public static int getChineseCount(String str) {
		String temp = null;
		Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
		Matcher m = p.matcher(str);
		int count = 0;
		while (m.find()) {
			temp = m.group(0);
			count += temp.length();
			System.out.println(temp + ":" + temp.length());
		}
		return count;
	}

	/**
	 * 设置填充字符
	 * 
	 * @param str
	 *            原字符
	 * @param fill
	 *            填充字符
	 * @param digit
	 *            填充后总位数
	 * @param startOrend
	 *            填充在原字符首尾(1,-1)
	 * @return
	 * @throws Exception
	 */
	public static String fChar(String str, String fill, int digit,
			int startOrend) throws Exception {
		if (!(str.length() > digit)) {
			// 处理中文问题
			// int slength=new String(str.getBytes(),"ISO-8859-1").length();
			int slength = str.length();
			int i = digit - slength;
			StringBuffer fillStr = new StringBuffer();
			for (int x = 0; x < i; x++)
				fillStr.append(fill);
			if (startOrend >= 0)
				return fillStr + str;
			else
				return str + fillStr;
		} else {
			throw new Exception("fillCharacter Exception");
		}
	}

	/**
	 * 得到字符串的字节长度
	 * 
	 * @param source
	 *            字符串
	 * @return 字符串的字节长度
	 */
	public static int getByteLength(String source) {
		int len = 0;
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			int highByte = c >>> 8;
			len += highByte == 0 ? 1 : 2;
		}
		return len;
	}

	/**
	 * 去除右边多余的空格。
	 * 
	 * @param value
	 *            待去右边空格的字符串
	 * @return 去右边空格的字符串
	 */
	public static String trimRight(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (Character.isWhitespace(ch[i])) {
				endIndex = i;
			} else {
				break;
			}
		}
		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	/**
	 * 去除左边多余的空格。
	 * 
	 * @param value
	 *            待去左边空格的字符串
	 * @return 去掉左边空格后的字符串
	 */
	public static String trimLeft(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length; i++) {
			if (Character.isWhitespace(ch[i])) {
				index = i;
			} else {
				break;
			}
		}
		if (index != -1) {
			result = result.substring(index + 1);
		}
		return result;
	}

	/**
	 * 将字符串数组使用指定的分隔符合并成一个字符串。
	 * 
	 * @param array
	 *            字符串数组
	 * @param delim
	 *            分隔符，为null的时候使用""作为分隔符（即没有分隔符）
	 * @return 合并后的字符串
	 */
	public static String combineStringArray(String[] array, String delim) {
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	/**
	 * 字符串数组中是否包含指定的字符串。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @param caseSensitive
	 *            是否大小写敏感
	 * @return 包含时返回true，否则返回false
	 */
	public static boolean contains(String[] strings, String string,
			boolean caseSensitive) {
		for (int i = 0; i < strings.length; i++) {
			if (caseSensitive == true) {
				if (strings[i].equals(string)) {
					return true;
				}
			} else {
				if (strings[i].equalsIgnoreCase(string)) {
					return true;
				}
			}
		}
		return false;
	}

	public static InputStream parseInputStream(String str) {
		InputStream is = new ByteArrayInputStream(str.getBytes());
		return is;
	}

	public static void main(String[] args) throws Exception {
		String input = "中文答复fd何as最的天dafds工作df高fdsdfs";
		System.out.println(StringUtil.getChineseCount(input));

		String fstr = "123哈哈";
		System.out.println(StringUtil.fChar("", "中", 10, 1));
		System.out.println(StringUtil.fChar(fstr, "中", 10, 1).length());

		System.out.println(StringUtil.getByteLength(fstr));
	}
}
