package base.utils;

import base.tool.MD5;

/**
 * 
 * Date: 2011-1-13 13:47:48
 * <p/>
 *  加密
 */
public class BaseSecretUtil {

    /**
     *
     * @param str
     * @param secret
     * @return
     */
    public static boolean error(Object str,String secret) {
        if(BaseStringUtil.isBlank(secret)){
            return true;
        }
        String secret2 = BaseSecretUtil.getSecret(str);
        return !secret.equals(secret2);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        String str = BaseSecretUtil.getSecret(1);
        //System.out.println(str);
    }

    /**
     * 得到加密串
     * @param str
     * @return
     */
    public static String getSecret(Object str){
         return BaseSecretUtil.getSecret(str, "abc.com");
    }

    /**
     * @param str
     * @param key
     * @return
     */
    public static String getSecret(Object str, String key) {
        MD5 m = new MD5();
        return m.calcMD5(str + "_" + key);
    }

}
