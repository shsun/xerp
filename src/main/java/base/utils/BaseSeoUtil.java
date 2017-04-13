package base.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Time: 14-9-9 下午3:12
 */
public class BaseSeoUtil {

    public static boolean isNotSpider(HttpServletRequest request){
        return !isSpider(request) ;
    }

    /**
     * 是网络爬虫
     * @param request
     * @return
     */
    public static boolean isSpider(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");
        return isSpider(userAgent) ;
    }


    public static boolean isSpider(String agent){
        if(BaseStringUtil.isBlank(agent)){
            return true;
        }
        agent = agent.toLowerCase();
        if(agent.contains("spider")){
            return true;
        }
        if(agent.contains("bot")){
            return true;
        }

        return false;
    }

    public String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        return ip;
    }

}
