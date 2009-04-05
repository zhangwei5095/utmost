package org.utmost.portal.service;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import org.springframework.stereotype.Service;
import org.utmost.common.CommService;

/**
 * 规则引擎对外接口
 * 
 * @author wanglm
 * 
 */
@Service("RuleService")
public class RuleService extends CommService {
	public String exec(String arg) throws FileNotFoundException,
			UnsupportedEncodingException {
		System.out.println("arg----->:\n" + arg);
		return RuleEngine.exec(arg);
	}
}
