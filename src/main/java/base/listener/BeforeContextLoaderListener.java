package base.listener;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import base.SystemGlobal;
import base.bean.JndiBean;
import base.constant.SystemGlobalConstant;

/**
 * 
 * @author shsun
 *
 */
public abstract class BeforeContextLoaderListener implements javax.servlet.ServletContextListener {
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * @param event
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		SystemGlobal.loadConfig(SystemGlobalConstant.CONFIG_FILE);
		
		// 再加载 SystemGlobals_***.properties,注意 在前面的基础上 如果存在就覆盖，不存在就增加
		this.loadConfigByJndi(SystemGlobalConstant.CONFIG_FILE);

		// 顺道把 项目根的物理路径得到
		String webRoot = context.getRealPath("/");
		webRoot = webRoot.replaceAll("\\\\", "/");
		if (webRoot.endsWith("/")) {
			webRoot = webRoot.substring(0, webRoot.length() - 1);
		}
		SystemGlobal.setPreference("webRoot", webRoot);

		initLoadParameter(event);
	}

	public void contextDestroyed(ServletContextEvent event) {

	}

	/**
	 * 加载 SystemGlobals_***.properties
	 * 
	 * @param configFile
	 */
	private void loadConfigByJndi(String configFile) {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			Object jndiApp_obj = envCtx.lookup("bean/MyBeanFactory");
			JndiBean jndiBean = (JndiBean) jndiApp_obj;

			// 没有配置jndi
			if (jndiBean == null) {
				log.warn("not config jndi for this domain at ${tomcat}\\conf\\Catalina\\localhost");
				return;
			}

			// 个性化加载 SystemGlobals_***.properties
			configFile = configFile.replace(".", "_" + jndiBean.getDomain() + ".");

			log.warn("\n\n\n配置文件：configFile = " + configFile + "\n");
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configFile);

			// 文件没找到
			if (inputStream == null) {
				log.error("inputStream == null for configFile=" + configFile);
				return;
			}

			// 覆盖或增加先前 SystemGlobals.properties
			Properties p = new Properties();
			p.load(inputStream);
			Enumeration<?> enu = p.keys();
			String key = "";
			while (enu.hasMoreElements()) {
				key = (String) enu.nextElement();
				SystemGlobal.setPreference(key, (String) p.get(key));
				System.out.println("------------ key=" + key + ", value=" + p.get(key));
			}

			SystemGlobal.setPreference("jndi", jndiBean.getDomain());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(".....|..|.....not config jndi for this domain.");
		}
	}

	public abstract void initLoadParameter(ServletContextEvent event);

}
