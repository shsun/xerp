package base.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @Description 获取Spring BaseSpringUtil
 * @author shsun
 */
public class BaseSpringUtil {

	/** 声明spring上下文 */
	static ApplicationContext appContext = null;

	/**
	 * 获取spring上下文实例
	 * 
	 * @return ApplicationContext appContext spring上下文
	 */
	public static synchronized ApplicationContext getAppContext() {
		if (appContext == null) {
			appContext = new ClassPathXmlApplicationContext(new String[] { "/spring/*.xml" });
		}
		return appContext;
	}
}
