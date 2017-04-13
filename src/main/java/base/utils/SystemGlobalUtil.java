package base.utils;

import base.SystemGlobal;

/**
 * 
 * 2016/11/2 17:54
 * 
 */
public class SystemGlobalUtil {

    /**
     * 是否是本地测试环境
     * @return
     */
    public static boolean isLocal() {
        if (SystemGlobal.getPreferenceBoolean("local")) {
            return true;
        }
        return false;
    }

    /**
     * 发布版本
     * @return
     */
    public static String getVersion(){
        return SystemGlobal.getPreference("version");
    }

    /**
     * 返回项目根路径
     * @return
     */
    public static String getWebRoot(){
        String webRoot = SystemGlobal.getWebRoot();
        return webRoot;
    }
}
