package base.validate;

import java.util.regex.Pattern;

/***
 * 手机号校验
 * @author Administrator
 *
 */
public class MobileValidate {
	
	public static boolean validate(String mobile){
		 return Pattern
		            .compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[^1^4,\\D]))\\d{8}")
		            .matcher(mobile).matches();
	}
	
}
