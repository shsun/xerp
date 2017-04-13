package base.tool.excel.parser;

import java.lang.reflect.Field;

import base.utils.BaseIntUtil;
import base.utils.BaseStringUtil;

/**
 * <p><b>Description:</b>AbstractSheetRecord</p>
 *
 * @author sang
 * @date 2015-10-27上午11:33:33
 * @version 1.0
 */
public abstract class AbstractSheetRecord implements ISheetRecord {

	/**
	 * 赋值,当单元格参数为空时使用默认值处理（如需特殊处理，子类重写该方法）
	 */
	@Override
	public void setData(String[] v) {
		
		if ( IFieldSequence.class.isAssignableFrom(this.getClass()) ) {
			
			IFieldSequence fieldSequence = (IFieldSequence) this;
			String sequence = fieldSequence.getFieldSequence();
			String[] fieldNames = null;
			if ( BaseStringUtil.isNotBlank(sequence) ){
				
				fieldNames = sequence.split(",");
				for ( int i = 0 ; i < fieldNames.length; i++ ) {
					String fieldName = fieldNames[i].trim();
				
					if ( v.length <= i || v[i] == null || v[i].equals(""))
						continue;
					
					try {
						Field field = this.getField(this.getClass(), fieldName);
						if ( field == null )
							continue;
						field.setAccessible(true);
						this.setValue(field, v[i]);
					} catch (SecurityException | IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 查找某个类中的属性，当前类没有往父级查
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	private Field getField(Class<?> clazz,String fieldName) {
		Field field = null;
		for ( ; clazz != Object.class ; clazz = clazz.getSuperclass() ) {
			
			Field[] fields = clazz.getDeclaredFields();
			
			for ( int i = 0 ; fields != null && i < fields.length ; i ++ ) {
				
				if ( fields[i].getName().equals(fieldName) ) {
					field = fields[i];
					return field;
				}
			}
		}
		return field;
	}
	
	/**
	 * 属性赋值(如需要特殊处理，子类重写该方法)
	 * @param field
	 * @param value
	 */
	public void setValue(Field field,Object value) {
		if ( field == null )
			return;
		
		try {
			Class<?> clazz = field.getType();
			if ( clazz.equals(Integer.class) || clazz.equals(int.class)){
				field.set(this, BaseIntUtil.getInt(value));
			} else {
				field.set(this,value);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
