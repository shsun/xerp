package base.validate;

import java.util.regex.Pattern;

/***
 * 邮箱校验
 * @author Administrator
 *
 */
public class EmailValidate {
	
	public static boolean validate(String email){
		return Pattern
	            .compile("^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$")
	            .matcher(email).matches();
	}
	
}
