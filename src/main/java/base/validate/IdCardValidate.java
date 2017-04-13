package base.validate;

import java.util.regex.Pattern;

/***
 * 身份证号校验
 * @author Administrator
 *
 */
public class IdCardValidate {
	
	public static boolean validate(String idCard){
		return Pattern
	            .compile("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$")
	            .matcher(idCard).matches();
	}
	
}
