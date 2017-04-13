package base.validate;

import java.util.regex.Pattern;

/***
 * 固话校验
 * @author Administrator
 *
 */
public class PhoneValidate {
	
	public static boolean validate(String phone){
		return Pattern
	            .compile("^((0\\d{2,3}-\\d{7,8})|(1[3584]\\d{9}))$")
	            .matcher(phone).matches();
	}
	
}
