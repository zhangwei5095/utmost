package org.utmost.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Service("LogService")
public class LogService {
	public Object performance(ProceedingJoinPoint pjp) throws Throwable {
		long st = System.currentTimeMillis();
		Object retVal = pjp.proceed();
		long et = System.currentTimeMillis();
		System.out.println("Performance->"
				+ pjp.getSignature().getDeclaringTypeName() + "--"
				+ pjp.getSignature().getName() + " ms:" + (et - st));
		Object obj[] = pjp.getArgs();
		for (Object s : obj) {
			System.out.println("	->arg:" + s);
		}
		return retVal;
	}

	public void beforeAdvice(JoinPoint point) {
		System.out.println("前置通知被触发：" + point.getTarget().getClass().getName()
				+ "将要" + point.getSignature().getName());
	}
}
