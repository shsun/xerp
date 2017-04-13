package base.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.beans.factory.DisposableBean;

/**
 * 
 * @author shsun
 *
 * @param <T>
 */
public class XAdApplicationContextAware<T> implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext theApplicationContext;

	public void setApplicationContext(ApplicationContext contex) throws BeansException {
		theApplicationContext = contex;
		System.out.println("XAdApplicationContextAware 系统启动....\n\n\n");
	}

	public static ApplicationContext getContext() {
		return theApplicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) getContext().getBean(name);
	}

	public static <T> T getBean(Class<T> cls) {
		return (T) getContext().getBean(cls);
	}

	public static void cleanApplicationContext() {
		theApplicationContext = null;
	}

	@Override
	public void destroy() throws Exception {
		cleanApplicationContext();
	}
}