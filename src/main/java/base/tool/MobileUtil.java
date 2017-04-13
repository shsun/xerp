package base.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检测是否为移动端设备访问
 * <p/>
 * 
 * Time: 15-4-27 下午7:11
 */
public class MobileUtil {
    protected final static Log log = LogFactory.getLog(MobileUtil.class);

    // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
    // 字符串在编译时会被转码一次,所以是 "\\b"
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
            + "|windows (phone|ce)|blackberry"
            + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            + "|laystation portable)|nokia|fennec|htc[-_]"
            + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
            + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    //移动设备正则匹配：手机端、平板
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);


    /**
     * 检测是否是移动设备访问
     *
     * @param userAgent 浏览器标识
     * @return true:移动设备接入，false:pc端接入
     * @Title: check
     * @Date : 2014-7-7 下午01:29:07
     */
    public static boolean check(String userAgent) {
        if (null == userAgent) {
            userAgent = "";
        }
        // 匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是不是移动设备
     * @param request
     * @return
     */
    public static boolean isMobile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Boolean mobile = (Boolean) session.getAttribute("mobile");
        if (mobile != null) {
            return mobile.booleanValue();
        }

        //获取ua，用来判断是否为移动端访问
        String userAgent = request.getHeader("USER-AGENT");
        if (null == userAgent) {
            return false;
        }

        userAgent = userAgent.toLowerCase();

        mobile = MobileUtil.check(userAgent);
        session.setAttribute("mobile", mobile);
        return mobile;
    }


}
