//package base.interceptor;
//
//import java.lang.reflect.Method;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//
//import base.annontation.Token;
//import base.util.BaseStringUtil;
//import base.util.BaseTokenUtil;
//
//
///***
// * 防重复提交
// * @author 丁万水
// *
// */
//public class TokenInterceptor extends HandlerInterceptorAdapter{
//	private static final Log log = LogFactory.getLog(TokenInterceptor.class);
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
//		HandlerMethod handlerMethod = (HandlerMethod)object;
//		if(handlerMethod == null){
//			return true;
//		}
//		Method method = handlerMethod.getMethod();
//		if(method == null){
//			return true;
//		}
//		Token token = method.getAnnotation(Token.class);
//		if(token == null){
//			return true;
//		}
//		if(!token.validate()){
//			return true;
//		}
//		//校验是否重复提交
//		//传入的token为空访问非法
//		String str = request.getParameter("token");
//		if(BaseStringUtil.isBlank(str)){
//			request.setAttribute("error", "非法访问，token缺失");
//			request.getRequestDispatcher("/error.jsp").forward(request, response);
//			log.error("非法访问，token缺失");
//			return false;
//		}
//		//传入的token不存在，重复提交
//		if(!BaseTokenUtil.validateToken(request, str)){
//			request.setAttribute("error", "非法访问，重复提交");
//			request.getRequestDispatcher("/error.jsp").forward(request, response);
//			log.error("非法访问，重复提交");
//			return false;
//		}
//		//移除token
//		BaseTokenUtil.removeToken(request, str);
//		return true;
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
//			throws Exception {
//		HandlerMethod handlerMethod = (HandlerMethod)object;
//		if(handlerMethod == null){
//			return ;
//		}
//		Method method = handlerMethod.getMethod();
//		if(method == null){
//			return ;
//		}
//		Token token = method.getAnnotation(Token.class);
//		if(token == null){
//			return ;
//		}
//		if(!token.add()){
//			return ;
//		}
//		BaseTokenUtil.addToken(request);
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
//			throws Exception {
//	}
//
//}
