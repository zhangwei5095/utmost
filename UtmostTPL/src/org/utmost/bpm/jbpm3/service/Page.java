package org.utmost.bpm.jbpm3.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 分页po User: yuchen Date: 2008-11-24 Time: 17:26:31
 */

public class Page {
	private int pageSize;// 每页显示的条数
	private int recordCount;// 总共的条数
	private int currentPage;// 当前页面
	private static final Log log = LogFactory.getLog(Page.class);

	/**
	 * 默认构造器
	 */
	public Page() {
	}

	/**
	 * @param pageSize
	 *            每页显示的条数
	 * @param recordCount
	 *            总共记录的条数
	 * @param currentPage
	 *            当前页面
	 */
	public Page(int pageSize, int recordCount, int currentPage) {
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		this.setCurrentPage(currentPage, "");
	}

	/**
	 * 初始化 currentPage 当前页 为1
	 * 
	 * @param pageSize
	 *            每页显示的条数
	 * @param recordCount
	 *            总共的条数
	 */
	public Page(int pageSize, int recordCount) {
		this(pageSize, recordCount, 1);
	}

	/**
	 * 获取总共的页数
	 * 
	 * @return 总共的页数
	 */
	public int getPageCount() {
		int size = recordCount / pageSize;// 总条数/每页显示的条数=总页数
		int mod = recordCount % pageSize;// 最后一页的条数
		if (mod != 0)
			size++;
		return recordCount == 0 ? 1 : size;
	}

	/**
	 * 获取起始的记录
	 * 
	 * @return 当前起始记录
	 */
	public int getFromIndex() {
		// System.out.println("from index:"+(currentPage-1) * pageSize);
		return (currentPage - 1) * pageSize;
	}

	/**
	 * 获取最末的记录
	 * 
	 * @return 最末的页记录
	 */
	public int getToIndex() {
		// System.out.println("to index:"+Math.min(recordCount, currentPage *
		// pageSize));
		return Math.min(recordCount, currentPage * pageSize);
	}

	/**
	 * 获取当前页
	 * 
	 * @return 当前页
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            当前页
	 * @param inner
	 */
	public void setCurrentPage(int currentPage, String inner) {
		int validPage = currentPage <= 0 ? 1 : currentPage;
		validPage = validPage > getPageCount() ? getPageCount() : validPage;
		this.currentPage = validPage;
	}

	/**
	 * @param currentPage
	 *            当前页
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return 每页显示大小
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示的条数
	 * 
	 * @param pageSize
	 *            每页显示的条数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取总共的条数
	 * 
	 * @return 总共的条数
	 */
	public int getRecordCount() {
		return recordCount;
	}

	/**
	 * 设置总共的条数
	 * 
	 * @param recordCount
	 *            总共的条数
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
}