package base.annontation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 防参数篡改注解
 * 
 * @author shsun
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamsSecret {
	// 添加校验，参数key数组
	public String[] add() default {};

	// 校验，参数key数组
	public String[] validate() default {};
}
