package base.validate;

import java.util.regex.Pattern;

/***
 * 密码校验
 * @author Administrator
 *
 */
public class PwdValidate {
	
	public static boolean validate(String userName){
		return Pattern
	            .compile("^[a-zA-z]\\w{3,15}$")
	            .matcher(userName).matches();
	}
	
}
