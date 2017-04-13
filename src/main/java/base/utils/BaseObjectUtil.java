package base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * <b>Description:</b>Object工具类
 * </p>
 * 
 * @author sang
 * @date 2015-9-1上午11:22:39
 * @version 1.0
 */
public class BaseObjectUtil {

	/**
	 * 判断是否为空
	 * 
	 * @param v
	 * @return
	 */
	public static boolean isNull(Object v) {

		if (v == null) {

			return true;

		}

		return false;

	}

	/**
	 * 动态获取对象中某属性值
	 * 
	 * @param item
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	@SuppressWarnings("unchecked")
	public static <T, C> C getFieldValue(T item, String name, Class<C> clazz) {

		C value = null;

		Field field = null;

		try {

			field = item.getClass().getDeclaredField(name);

			field.setAccessible(true);

			value = (C) field.get(item);

		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {

			e.printStackTrace();

		}

		return value;
	}


	/**
	 * 查询list集合对象中某个属性的集合
	 * 
	 * @param list
	 * @param fieldName
	 * @param clazz
	 * @return
	 */
	public static <T, C> List<C> getFieldValues(List<T> list, String fieldName,
			Class<C> clazz) {

		List<C> result = new ArrayList<C>();

		for (T item : list) {
			C value = BaseObjectUtil.getFieldValue(item, fieldName, clazz);
			result.add(value);
		}

		return result;
	}

	/**
	 * 动态给对象的属性赋值
	 * 
	 * @param obj
	 * @param fieldName
	 * @param value
	 */
	public static <T,V> void setFieldValue(T object,String fieldName,V value) {
    	
		Field field = null;
		try {
			field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(object, value);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
    }

	/**
	 * 深度复制
	 * 
	 * @param v
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getDeepCopy(T v) {

		T object = null;

		ByteArrayOutputStream byteos = new ByteArrayOutputStream();

		try {

			ObjectOutputStream objectos = new ObjectOutputStream(byteos);
			objectos.writeObject(v);
			objectos.close();
			ByteArrayInputStream byteis = new ByteArrayInputStream(
					byteos.toByteArray());
			ObjectInputStream objectis = new ObjectInputStream(byteis);
			object = (T) objectis.readObject();
			objectis.close();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return object;
	}
}
