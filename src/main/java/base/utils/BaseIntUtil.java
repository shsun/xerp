package base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Date: 2010-7-20 10:13:47
 * <p/>
 * 
 */
public class BaseIntUtil {


	/**
     * @param i
     * @return
     */
    public static boolean isNotBlank(int[] i) {
        return !isBlank(i);
    }

    /**
     * 判断是否为空
     *
     * @param i
     * @return
     */
    public static boolean isBlank(int[] i) {
        if (i == null || i.length == 0) {
            return true;
        }
        return false;
    }
	
    /**
     * @param i
     * @return
     */
    public static boolean isNotBlank(Integer i) {
        return !isBlank(i);
    }

    /**
     * 判断是否为空
     *
     * @param i
     * @return
     */
    public static boolean isBlank(Integer i) {
        if (i == null || i == 0) {
            return true;
        }
        return false;
    }

    public static boolean equals(Integer i,int j){
        if(i == null){
            return false;
        }

        if(i.intValue()==j){
            return true;
        }

        return false;
    }

    public static boolean equals(Byte i,byte j){
        if(i == null){
            return false;
        }

        if(i.byteValue()==j){
            return true;
        }

        return false;
    }

    public static int add(Object i,Object j){
        return BaseIntUtil.getInt(i) + BaseIntUtil.getInt(j);
    }
    public static int sub(Object i,Object j){
        return BaseIntUtil.getInt(i) - BaseIntUtil.getInt(j);
    }

    /**
     * @param request
     * @param paramName
     * @return
     */
    public static int getInt(HttpServletRequest request, String paramName) {
        return getInt(request, paramName, 0);
    }

    public static int getInt(HttpServletRequest request, String paramName, int defaultValue) {
        String obj = request.getParameter(paramName);
        int i = getInt(obj);
        if (i == 0) {
            i = defaultValue;
        }
        return i;
    }

    /**
     * 得到 int
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("unused")
	public static int getInt(Object obj) {
        if(obj==null){
            return 0;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof Double) {
            Double d = (Double) obj;
            return d.intValue();
        }
        if (obj instanceof Byte) {
            Byte b = (Byte) obj;
            return b.intValue();
        }

        if (obj instanceof String) {
            String str = (String) obj;
            if (BaseStringUtil.isBlank(str)) {
                return 0;
            }
            str = str.trim();
            try {
                if (str.indexOf(".") != -1) {
                    Double d = Double.parseDouble(str);
                    return d.intValue();
                }
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                //e.printStackTrace();
            }
            return 0;
        }

        if (obj instanceof Boolean) {
            boolean b = (Boolean) obj;
            if (b) {
                return 1;
            }
            return 0;
        }
        if (obj instanceof Long) {
            Long value = (Long) obj;
            if (value == null) {
                return 0;
            }
            return value.intValue();
        }

        return 0;
    }

    public static int getRandom(int feed) {
        int i = (int) (Math.random() * feed);
        return i;
    }

    public static int getTotal(String str){
        int[] array = BaseIdUtil.getArray(str);
        return getTotal(array);
    }

    public static int getTotal(int[] array){
        int total = 0;
        for(int i : array){
           total = total+i;
        }
        return total;
    }
    
    /**
     * 
    * @Title: isNumeric
    * @param str
    * @return 
    * @return boolean  
    * @Description: 判断字符串是否为数字
    * @throws
     */
    public static boolean isNumeric(String str){ 
    	   Pattern pattern = Pattern.compile("[0-9]*"); 
    	   Matcher isNum = pattern.matcher(str);
    	   if( !isNum.matches() ){
    	       return false; 
    	   } 
    	   return true; 
    	}
}
