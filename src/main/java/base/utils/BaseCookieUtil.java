package base.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Cookie工具的使用
 *
 * @author zhangquansheng
 *         2012-11-27
 */
public class BaseCookieUtil {
    public static final Log log = LogFactory.getLog(BaseCookieUtil.class);

    //--------------------------------获得方法------------------------------------

    public static Cookie getCookie(HttpServletRequest req, String key) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(key)) {
                return cookie;
            }
        }

        return null;
    }

    /**
     * 获取对应key的值
     *
     * @param req
     * @param key cookie相应的key
     * @return
     */
    public static String getCookieValue(HttpServletRequest req, String key) {
        Cookie cookie = getCookie(req, key);
        if (cookie == null) {
            return "";
        }

        return cookie.getValue();
    }

    /**
     *
     * @param req
     * @param key
     * @return
     */
    public static int getCookieValueInt(HttpServletRequest req, String key) {
       String value = getCookieValue(req,key);
        return BaseIntUtil.getInt(value);
    }

    //--------------------------------设置方法------------------------------------

    /**
     * 设置cookie
     *
     * @param request
     * @param response
     * @param name     相应的key
     * @param value    相应的值
     */
    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response, String name, String value) {
        setCookie(request, response, name, value, 0x278d00);
    }

    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response, String name, int value) {
        setCookie(request, response, name, value+"", 0x278d00);
    }


    /**
     * 设置cookie
     *
     * @param request
     * @param response
     * @param name
     * @param value
     * @param maxAge   生命周期  生存时间的整数,以秒为单位
     */
    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response, String name, String value, int maxAge) {
        if (BaseStringUtil.isBlank(value)) {
            return;
        }

        String valueEncode = "";
        try {
            valueEncode = URLEncoder.encode(value, "utf-8");
            Cookie cookie = new Cookie(name, valueEncode);
            if (maxAge == -1) {
                maxAge = 5 * 365 * 24 * 60 * 60;
            }
            cookie.setMaxAge(maxAge);
            cookie.setHttpOnly(true);
            cookie.setPath(getPath(request));
            response.addCookie(cookie);
        } catch (Exception e) {
            log.error("@setCookie name=" + name + ",value=" + value + ",valueEncode=" + valueEncode + ",maxAge=" + maxAge);
            e.printStackTrace();
        }
    }

    /**
     * 为指定域名设置cookie
     *
     * @param key
     * @param value
     * @param domain   域名
     * @param MaxAge
     * @param response
     */
    public static void setCookie(String key, String value, String domain, int MaxAge, HttpServletResponse response) {
        if (BaseStringUtil.isBlank(value)) {
            return;
        }

        String valueEncode = "";
        try {
            valueEncode = URLEncoder.encode(value, "utf-8");
            Cookie cookie2 = new Cookie(key, valueEncode);
            cookie2.setMaxAge(MaxAge);
            cookie2.setDomain(domain);
            cookie2.setPath("/");
            cookie2.setHttpOnly(true);
            response.addCookie(cookie2);
        } catch (Exception e) {
            log.error("@setCookie key=" + key + ",value=" + value + ",valueEncode=" + valueEncode + ",MaxAge=" + MaxAge + ",domain=" + domain);
            e.printStackTrace();
        }
    }

    //--------------------------------移除方法------------------------------------

    /**
     * 删除相应的cookie
     *
     * @param request
     * @param response
     * @param cookie   删除的cookie
     */
    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response, Cookie cookie) {
        if (cookie != null) {
            cookie.setPath(getPath(request));
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * @param request
     * @param response
     * @param key
     */
    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response, String key) {
        Cookie cookie = getCookie(request, key);
        if (cookie == null) {
            return;
        }

        deleteCookie(request, response, cookie);
    }

    /**
     * 清理所有的COOKIE
     */
    public static void deleteCookies(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = new Cookie(cookies[i].getName(), null);
            cookie.setMaxAge(0);
            cookie.setPath("/");//根据你创建cookie的路径进行填写
            response.addCookie(cookie);
        }
        request.getSession().invalidate();
    }

    //--------------------------------辅助方法------------------------------------

    /**
     * 获取路径
     *
     * @param request
     * @return
     */
    private static String getPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return (path == null || path.length() == 0) ? "/" : path;
    }

}
