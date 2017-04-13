package base.validate;

import java.util.regex.Pattern;

/***
 * 用户名校验
 * @author Administrator
 *
 */
public class UserNameValidate {
	
	public static boolean validate(String userName){
		return Pattern
	            .compile("^[a-zA-z]\\w{3,15}$")
	            .matcher(userName).matches();
	}
	
}
