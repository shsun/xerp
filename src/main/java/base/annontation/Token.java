package base.annontation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 校验重复提交注解
 * 
 * @author shsun
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token {
	// 是否向session中添加token
	public boolean add() default false;

	// 是否校验token
	public boolean validate() default false;
}
