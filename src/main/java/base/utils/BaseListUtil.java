package base.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * <p><b>Description:</b>ListUtil工具类</p>
 *
 * @author sang
 * @date 2015-9-6下午5:09:02
 * @version 1.0
 */
public class BaseListUtil {


	/**
	 * 是否非空
	 * @param <T>
	 * @param i
	 * @return
	 */
    public static <T> boolean isNotBlank(List<T> v) {
        return !isBlank(v);
    }

    /**
     * 判断是否为空
     * @param <T>
     * @param i
     * @return
     */
    public static <T> boolean isBlank(List<T> v) {
        if (v == null || v.size() == 0) {
            return true;
        }
        return false;
    }

	/**
	 * 获取Tree顺序结构
	 * @param data 数据集
	 * @param parentField 父级字段 比如pid
	 * @param parentValue 父级字段值 
	 * @param childField  关联字段 比如id，当前id是子项的pid值
	 * @param exclude 是否排除
	 * @param excludeChildValue 排除child值
	 * @return
	 */
	public static <T> List<T> getTreeOrder(List<T> data,String parentField,Object parentValue,String childField,Boolean exclude,Object excludeChildValue){
		
		List<T> tree = null;
		
		if ( data == null || data.size() == 0 ) {
			return tree;
		}
		
		tree = new ArrayList<T>();
		
		getTreeOrder(tree,data,null,parentField,parentValue,childField,exclude,excludeChildValue);
		
		return tree;

	}
	
	/**
	 * 获取Tree顺序具体实现方法
	 * @param tree
	 * @param data
	 * @param parent
	 * @param parentField
	 * @param topValue
	 * @param childField
	 * @param exclude
	 * @param excludeChildValue
	 */
	private static <T> void getTreeOrder(List<T> tree,List<T> data,T parent,String parentField,Object topValue,String childField,Boolean exclude,Object excludeChildValue) {
		
		for ( int i = 0 ; i < data.size(); i++ ) {
			
			T item = data.get(i);
			
			Object itemParentValue = BaseObjectUtil.getFieldValue(item, parentField,Object.class);
			
			Object itemValue = BaseObjectUtil.getFieldValue(item, childField,Object.class);
			
			if ( exclude == true ) {
				
				if ( itemValue == excludeChildValue || itemValue.equals(excludeChildValue) ) {
					continue;
				}
			}
			
			if ( parent == null ) {
				
				//根节点
				if ( itemParentValue == topValue || itemParentValue.equals(topValue) ) {
					
					tree.add(item);
					
					data.remove(i);
					i--;
					
					getTreeOrder(tree,data,item,parentField,topValue,childField,exclude,excludeChildValue);
				}
				
			} else {
				
				Object parentValue = BaseObjectUtil.getFieldValue(parent, childField,Object.class);
				
				if ( itemParentValue == parentValue ) { 
					
					tree.add(item);
					
					data.remove(i);
					i--;
					
					getTreeOrder(tree,data,item,parentField,topValue,childField,exclude,excludeChildValue);
					
				} 
			} 
		}
	}
	
	/**
	 * 获取data split(",") 转换List<Integer>数组
	 * @param data
	 * @return
	 */
	public static List<Integer> getList(String data){
		List<Integer> typeList = new ArrayList<Integer>();
		if ( BaseStringUtil.isNotBlank(data) ) {
			String[] typeArray = data.split(",");
			for ( String typeStr : typeArray ) {
				
				Integer type = BaseIntUtil.getInt(typeStr);
				if ( type != null ) {
					typeList.add(type);
				}
			}
		}
		return typeList;
	}
	
	
}
