package base.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import base.annontation.ParamsSecret;
import base.utils.BaseParamsSecretUtil;

/***
 * 防参数串改
 * @author 丁万水
 *
 */
public class SecretParamsInterceptor implements HandlerInterceptor{
	private static final Log log = LogFactory.getLog(SecretParamsInterceptor.class);
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod)object;
		if(handlerMethod == null){
			return ;
		}
		Method method = handlerMethod.getMethod();
		if(method == null){
			return ;
		}
		ParamsSecret paramsSecret = method.getAnnotation(ParamsSecret.class);
		if(paramsSecret == null){
			return ;
		}
		String [] add = paramsSecret.add();
		if(add.length==0){
			return ;
		}
		BaseParamsSecretUtil.addSecret(request, add);
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod)object;
		if(handlerMethod == null){
			return true;
		}
		Method method = handlerMethod.getMethod();
		if(method == null){
			return true;
		}
		ParamsSecret paramsSecret = method.getAnnotation(ParamsSecret.class);
		if(paramsSecret == null){
			return true;
		}
		String [] validate = paramsSecret.validate();
		if(validate.length == 0){
			return true;
		}
		if(BaseParamsSecretUtil.validate(request, validate)){
			return true;
		}
		request.setAttribute("error", "参数遭到串改！");
		request.getRequestDispatcher("/error.jsp").forward(request, response);
		log.error("参数遭到串改！");
		return false;
	}

}
