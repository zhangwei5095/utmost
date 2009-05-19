package org.utmost.portal.auth.service;

import org.springframework.stereotype.Service;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;

@Service("AuthService")
public class AuthService {
	/**
	 * 根据loginname获取用户权限信息
	 * 
	 * @return
	 */
	public Object findAuthInfo(String loginname) {
		AutoService service = (AutoService) SpringContext
				.getBean("AutoService");

		return null;
	}

	/**
	 * 根据角色获取功能树
	 * 
	 * @return
	 */
	public String findAuthTree() {
		return null;
	}
}
