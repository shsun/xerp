package base.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import base.tool.MD5;

/**
 * 防止参数串改
 * @author dws
 *
 */
public class BaseParamsSecretUtil {
	
	private static String privatekey ="123456";
	
	/***
	 * 生成参数加密后的secret，并setAttribute到request中
	 * @param request	
	 * @param add		需要加密参数key集合
	 */
	public static String addSecret(HttpServletRequest request,String[] add){
		if(add==null || add.length==0){
			return null;
		}
		List<String> list = new ArrayList<String>();
		for(int i = 0; i<add.length; i++){
			list.add(String.valueOf(request.getAttribute(add[i])));
		}
		String str = getSecret(list);
		request.setAttribute("secretKey", str);
		return str;
	}
	
	/***
	 * 生成参数加密后的secret，并setAttribute到request中
	 * @param request	
	 * @param add		需要加密参数集合
	 */
	public static String addSecret(HttpServletRequest request,List<String> list){
		if(list==null || list.size()==0){
			return null;
		}
		String str = getSecret(list);
		request.setAttribute("secretKey", str);
		return str;
	}
	
	/***
	 * 生成参数加密后的secret
	 * @param list	需要加密的参数集合
	 */
	public static String getSecret(List<String> list){
		if(list == null || list.size()==0){
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for(String s:list){
			if(BaseStringUtil.isBlank(s)){
				continue;
			}
			builder.append(s);
		}
		builder.append(privatekey);

		String str = builder.toString();
		str = new MD5().calcMD5(str);
		return str;
	}
	
	
	/***
	 * 校验参数是否合法
	 * @param list			需要校验的参数，有序
	 * @param secret
	 * @return
	 */
	public static boolean validate(List<String> list, String secret){
		if(BaseStringUtil.isBlank(secret)){
			return false;
		}
		return secret.equals(getSecret(list));
	}
	
	/**
	 * 校验参数是否合法
	 * @param request
	 * @param validate		需要校验参数的key，有序
	 * @return
	 */
	public static boolean validate(HttpServletRequest request,String [] validate){
		if(validate.length==0){
			return false;
		}
		List<String> add = new ArrayList<String>();
		for(int i = 0; i<validate.length; i++){
			add.add(request.getParameter(validate[i]));
		}
		return validate(add, request.getParameter("secretKey"));
	}
	
}
