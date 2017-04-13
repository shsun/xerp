package base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import base.constant.BaseConstant;


/**
 * 
 * Time: 12-12-25 下午3:43
 */
public class BaseRequestUtil {
    private final static Log log = LogFactory.getLog(BaseRequestUtil.class);

    public static boolean isMobile(String userAgent,String ip) {
        if (userAgent == null || "23.92.24.165".equals(ip)) {
            return false;
        }

        userAgent = userAgent.toUpperCase();
        if (userAgent.indexOf("NOKI") > -1 || // Nokia phones and emulators
                userAgent.indexOf("ERIC") > -1 || // Ericsson WAP phones and emulators
                userAgent.indexOf("WAPI") > -1 || // Ericsson WapIDE 2.0
                userAgent.indexOf("MC21") > -1 || // Ericsson MC218
                userAgent.indexOf("AUR") > -1 || // Ericsson R320
                userAgent.indexOf("R380") > -1 || // Ericsson R380
                userAgent.indexOf("UP.B") > -1 || // UP.Browser
                userAgent.indexOf("WINW") > -1 || // WinWAP browser
                userAgent.indexOf("UPG1") > -1 || // UP.SDK 4.0
                userAgent.indexOf("UPSI") > -1 || //another kind of UP.Browser
                userAgent.indexOf("QWAP") > -1 || // unknown QWAPPER browser
                userAgent.indexOf("JIGS") > -1 || // unknown JigSaw browser
                userAgent.indexOf("JAVA") > -1 || // unknown Java based browser
                userAgent.indexOf("ALCA") > -1 || // unknown Alcatel-BE3 browser (UP based)
                userAgent.indexOf("MITS") > -1 || // unknown Mitsubishi browser
                userAgent.indexOf("MOT-") > -1 || // unknown browser (UP based)
                userAgent.indexOf("MY S") > -1 || //  unknown Ericsson devkit browser
                userAgent.indexOf("WAPJ") > -1 || //Virtual WAPJAG www.wapjag.de
                userAgent.indexOf("FETC") > -1 || //fetchpage.cgi Perl script from www.wapcab.de
                userAgent.indexOf("ALAV") > -1 || //yet another unknown UP based browser
                userAgent.indexOf("WAPA") > -1 || //another unknown browser (Web based "Wapalyzer")
                userAgent.indexOf("OPER") > -1 || //Opera
                userAgent.indexOf("DOPOD") > -1 ||  //多普达
                userAgent.indexOf("ANDROID") > -1 ||  //多普达
                userAgent.indexOf("APPLE") > -1 ||  //多普达
                userAgent.indexOf("SYMBIAN") > -1   //symbian系统
                ) {
            return true;
        }
        return false;
    }

    public static boolean isXss(String queryString){
        if(BaseStringUtil.isBlank(queryString)){
            return false;
        }
        if(queryString.contains("'")
                || queryString.contains("%")
                || queryString.contains("{")
                || queryString.contains("}")
                || queryString.contains("#")
                || queryString.contains("!")
                || queryString.contains("~")
                || queryString.contains("@")
                || queryString.contains("^")
                || queryString.contains("&")
                || queryString.contains("*")
                || queryString.contains("(")
                || queryString.contains(")")
                || queryString.contains("=")
                || queryString.contains("|")
                ){
            return true;
        }
        Pattern TAGPATTERN = Pattern.compile("<.+?>");
        // 匹配一个或多个列举出的关键字，每个关键字和=中间允许0到n个空格
        Pattern EVENTPATTERN = Pattern.compile("(onblur\\s*=)+|(onchange\\s*=)+|(onclick\\s*=)+|(ondblclick\\s*=)+|(onerror\\s*=)+|(onfocus\\s*=)+|(onfocusin\\s*=)+|(onfocusout\\s*=)+|(onkeydown\\s*=)+|(onkeypress\\s*=)+|(onkeyup\\s*=)+|(onload\\s*=)+|(onmousedown\\s*=)+|(onmousemove\\s*=)+|(onmouseout\\s*=)+|(onmouseover\\s*=)+|(onmouseup\\s*=)+|(onresize\\s*=)+|(onscroll\\s*=)+|(onselect\\s*=)+|(onsubmit\\s*=)+|(onunload\\s*=)+|(alert\\s*[(])+");
        try {
            if (TAGPATTERN.matcher(URLDecoder.decode(queryString, "UTF-8")).find() || EVENTPATTERN.matcher(URLDecoder.decode(queryString, "UTF-8")).find()) {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    public static boolean randImgError(HttpServletRequest request, HttpServletResponse response){
        String randSession = (String) request.getSession().getAttribute(BaseConstant.KEY_RAND_SESSION);
        String randRequest = request.getParameter(BaseConstant.KEY_RAND_REQUEST);

        if(randRequest==null || !randRequest.toLowerCase().equals(randSession)){
            BaseResponseUtil.responseAjaxJson(response, BaseConstant.KEY_RAND_REQUEST,"验证码错误");
            return true;
        }

        return false;
    }
    
//    /**
//     * 判断Form中QueryMode取Form对象
//     * （初始化\超链接操作时，验证对象是否存在，存在返回session中的form，不存在创建对象存储到session中，表单提交叫form存储到session中）
//     * @param request
//     * @param sessionKey
//     * @param form
//     * @param clazz
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//	public static <T> T getSessionForm(HttpServletRequest request,String sessionKey,BaseForm form,Class<T> clazz) {
//
//    	String mode = form.getQueryMode();
//    	T sessionForm = (T)form;
//
//    	if ( BaseStringUtil.isBlank(mode) ) {
//    		//直接查找session
//    		sessionForm = (T) request.getSession().getAttribute(sessionKey);
//    	} else if ( mode.equalsIgnoreCase("form") ) {
//    		//覆盖session内对象，并使用
//    		request.getSession().setAttribute(sessionKey, form);
//    	} else if ( mode.equalsIgnoreCase("first") ) {
//    		//清除session，使用地址栏参数
//    		request.getSession().removeAttribute(sessionKey);
//    	}
//
//    	if ( BaseObjectUtil.isNull(sessionForm) ) {
//    		try {
//				return clazz.newInstance();
//			} catch (InstantiationException | IllegalAccessException e) {
//				e.printStackTrace();
//			}
//    	}
//
//    	return BaseObjectUtil.getDeepCopy(sessionForm);
//
////    	if ( BaseStringUtil.isNotBlank(mode) && mode.equalsIgnoreCase("form")){
////
////    		sessionForm = (T) form;
////			request.getSession().setAttribute(sessionKey, form.getDeepCopy());
////
////    	} else  {
////
////    		sessionForm = (T) request.getSession().getAttribute(sessionKey) ;
////			if ( BaseObjectUtil.isNull(sessionForm) ) {
////
////				try {
////					sessionForm = clazz.newInstance();
////				} catch (InstantiationException | IllegalAccessException e) {
////					e.printStackTrace();
////				}
////				request.getSession().setAttribute(sessionKey,sessionForm);
////
////			}
////		}
////
////		return sessionForm;
//    }

    /**
     * 判断Form中QueryMode取Form对象
     * （初始化\超链接操作时，验证对象是否存在，存在返回session中的form，不存在创建对象存储到session中，表单提交叫form存储到session中）
     * @param request
     * @param form
     * @param clazz
     * @param <T>
     * @return
     */
//	public static <T> T getSessionForm(HttpServletRequest request,BaseForm form,Class<T> clazz) {
//
//    	String sessionKey = clazz.getSimpleName() + form.getOperate();
//
//    	return getSessionForm(request, sessionKey, form, clazz);
//    }
	
	/***
	 * 获取请求参数拼装成字符串，类似request.getQueryString()，但支持非url后面的参数
	 * @param request
	 * @return
	 */
	public static String getQueryString(HttpServletRequest request){
		StringBuilder qs = new StringBuilder();
		Map<String,String[]> map = request.getParameterMap();
		Set<Entry<String, String[]>> entry = map.entrySet();
		Iterator<Entry<String, String[]>> i = entry.iterator();
		boolean isFirst = true;
		while(i.hasNext()){
			Entry<String, String[]> e = i.next();
			String key = e.getKey();
			String[] values = e.getValue();
			if(BaseStringUtil.isBlank(key)){
				continue;
			}
			if(values == null || values.length==0){
				continue;
			}
			for(String value:values){
				if(!isFirst){
					qs.append("&");
				}
				isFirst = false;
				qs.append(key);
				qs.append("=");
				qs.append(value);
			}
		}
		
		return qs.toString();
	}

    /**
     * 将错误原因显示在 error.jsp中
     * @param request
     * @param msg
     * @return
     */
    public static String getErrorJsp(HttpServletRequest request,String msg){
        log.error(msg);
        request.setAttribute("error",msg);
        return "/error";
    }
    /**
     * 
    * @Title: getRemortIP
    * @param request
    * @return 
    * @return String  
    * @Description: 获取真实IP
    * @throws
     */
    public static String getRemortIP(HttpServletRequest request) { 
        if (request.getHeader("x-forwarded-for") == null) { 
            return request.getRemoteAddr(); 
        } 
        //反向代理获取真实IP
        return request.getHeader("x-forwarded-for"); 
    }   
}
