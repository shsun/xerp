package base.validate;

import java.util.regex.Pattern;


public class ECharValidate {
	
	public static boolean validate(String echar){
		return Pattern
	            .compile("^[a-zA-z\\w]*$")
	            .matcher(echar).matches();
	}
	
}
