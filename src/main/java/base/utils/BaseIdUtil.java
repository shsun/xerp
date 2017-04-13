package base.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Time: 15-4-19 下午1:40
 */
public class BaseIdUtil {

    /**
     * 得到 id int 数组
     *
     * @param ids
     * @return long[]
     */
    public static int[] getIds(String ids) {
        if (ids == null || ids.trim().equals("")) {
            return null;
        }
        String[] ids_string = ids.split(",");
        if (ids_string == null || ids_string.length == 0) {
            return null;
        }

        int len = ids_string.length;
        int[] ids_int = new int[len];
        for (int i = 0; i < len; i++) {
            ids_int[i] = BaseIntUtil.getInt(ids_string[i]);
        }
        return ids_int;
    }

    /**
     * @param ids
     * @param id
     * @return
     */
    public static String getIds(String ids, long id) {
        int[] ids2 = getIds(ids);
        if (ids2 == null) {
            return String.valueOf(id);
        }

        int len = ids2.length;

        for (int i = 0; i < len; i++) {
            if (id == ids2[i]) {
                return ids;
            }
        }

        return ids + "," + id;
    }

    public static String getIds(String ids, int id) {
        return getIds(ids, id, 0);
    }

    public static String getIds(String ids, int id, int size) {
        int[] idsArray = getIds(ids);
        if (idsArray == null) {
            return String.valueOf(id);
        }
        if (size != 0 && idsArray.length >= size) {
            return ids;
        }

        boolean add = true;
        for (int id2 : idsArray) {
            if (id2 == id) {
                add = false;
                break;
            }
        }

        if (add) {
            ids = ids + "," + id;
        }

        return ids;
    }

    /**
     * 排除掉 重复的和0的元素
     * @param ids1
     * @param size : 结果最大长度
     * @return
     */
    public static String getIdsNoRepeat(String ids1,int size){
        String ids = "";
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        int[] idArray = getArray(ids1);
        if(idArray==null){
            return ids;
        }

        for(int id:idArray){
            if(map.size()==size && size!=0){
                return ids;
            }
            if(map.containsKey(id)){
                continue;
            }
            map.put(id,id);
            if(id ==0){
                continue;
            }
            if(!ids.equals("")){
                ids+=",";
            }
            ids+=id;
        }

        return ids;
    }

    public static String checkIds(String ids) {
        if (ids == null) {
            return "";
        }

        ids = ids.trim();
        if (ids.startsWith(",")) {
            ids = ids.substring(1, ids.length());
        }
        if (ids.endsWith(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        return ids;
    }

    public static int[] upsetIds(String ids, int feed) {
        if (feed == 0) {
            feed = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        }

        int[] array = getArray(ids);
        if (array == null || array.length == 1) {
            return array;
        }

        int length = array.length;

        int[] array2 = new int[length];

        for (int i = 0; i < length; i++) {
            int j = (i + feed) % length;
            int k = i / 2;
            if (i % 2 == 1) {
                k = (length - 1) / 2 + (i) / 2 + 1;
            }
            array2[k] = array[j];
        }

        return array2;
    }

    public static int[] getIntArray(HttpServletRequest request, String paramName) {
        String obj = request.getParameter(paramName);
        return getIntArray(obj);
    }

    /**
     * 字符串转成int数组 ,按逗号分隔
     *
     * @param str
     * @return
     */
    public static int[] getIntArray(String str) {
        String[] strs = BaseStringUtil.getArray(str);
        if (strs == null) {
            return null;
        }
        int len = strs.length;
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            array[i] = BaseIntUtil.getInt(strs[i]);
        }
        return array;
    }

    /**
     * 取数组第index 个元素
     * @param str
     * @param index
     * @return
     */
    public static int getIndex(String str,int index){
        int[] ids = getIntArray(str);
        if(ids==null){
            return 0;
        }
        if(ids.length<=index){
            index = ids.length-1;
        }

        return ids[index];
    }

    public static int getIntArrayLength(String str) {
        int[] array = getIntArray(str);
        if (array == null) {
            return 0;
        }
        return array.length;
    }

    /**
     * @param array
     * @return
     */
    public static int[] getArrayRepeat(int[] array) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        List<Integer> list = new ArrayList<Integer>();

        for (int i : array) {
            if (map.containsKey(i)) {
                list.add(i);
                continue;
            }
            map.put(i, i);
        }
        int size = list.size();
        int[] array2 = new int[size];
        for (int i = 0; i < size; i++) {
            array2[i] = list.get(i);
        }

        return array2;
    }

    /**
     *
     * @param ids_new
     * @param ids_exist
     * @return
     */
    public static int[] getArrayNotIn(String ids_new, String ids_exist) {
        int[] ids_new_array = BaseIdUtil.getArray(ids_new);
        int[] ids_exist_array = BaseIdUtil.getArray(ids_exist);

        return getArrayNotIn(ids_new_array,ids_exist_array);
    }

    /**
     * 得到新增数据， 参数顺序调整就是 得到减少的数据
     * ids_new_array 里面有，但 ids_exist_array 里面没有的
     * @param ids_new_array
     * @param ids_exist_array
     * @return
     */
    public static int[] getArrayNotIn(int[] ids_new_array, int[] ids_exist_array) {
        List<Integer> result = new ArrayList<Integer>();
        if (ids_new_array == null) {
            return new int[]{};
        }
        if (ids_exist_array == null) {
            return ids_new_array;
        }

        for (int newInt : ids_new_array) {
            boolean add = true;
            for (int oldInt : ids_exist_array) {
                if (newInt == oldInt) {
                    add = false;
                }
            }
            if (add) {
                result.add(newInt);
            }
        }
        int size = result.size();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = result.get(i);
        }

        return array;
    }
    
    /**
     * 得到新增数据， 参数顺序调整就是 得到减少的数据
     * ids_new_array 里面有，但 ids_exist_array 里面没有的
     * @param ids_new_array
     * @param ids_exist_array
     * @return
     */
    public static Integer[] getArrayNotIn(Integer[] ids_new_array, Integer[] ids_exist_array) {
        List<Integer> result = new ArrayList<Integer>();
        if (ids_new_array == null) {
            return new Integer[]{};
        }
        if (ids_exist_array == null) {
            return ids_new_array;
        }

        for (int newInt : ids_new_array) {
            boolean add = true;
            for (int oldInt : ids_exist_array) {
                if (newInt == oldInt) {
                    add = false;
                }
            }
            if (add) {
                result.add(newInt);
            }
        }
        int size = result.size();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = result.get(i);
        }

        return array;
    }

    /**
     * 字符串转成int数组 ,按逗号分隔
     *
     * @param str
     * @return
     */
    public static int[] getArray(String str) {
        String[] strs = BaseStringUtil.getArray(str);
        return getArray(strs);
    }

    public static int[] getArray(String[] strs) {
        if (strs == null) {
            return null;
        }
        int len = strs.length;
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            array[i] = BaseIntUtil.getInt(strs[i]);
        }
        return array;
    }


    public static boolean isIn(int id, String ids) {
        int[] idArray = getIntArray(ids);
        if (idArray == null) {
            return false;
        }
        for (int id2 : idArray) {
            if (id == id2) {
                return true;
            }
        }
        return false;
    }

    public static int getCount(String ids) {
        int[] array = getArray(ids);
        if (array == null) {
            return 0;
        }
        return array.length;
    }

    /**
     *
     * @param ids
     * @return
     */
    public static List<Integer> getIdList(String ids){
        int[] array = getArray(ids);
        if(array==null){
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();
        for(int id:array){
            list.add(id);
        }
        return list;
    }
    

    /**
     * 字符串以逗号分隔，取两个字符串交集，然后返回List<Integer>结果集
     * @param s1
     * @param s2
     * @return
     * 
     * @author sangshuan
     */
    public static Map<Integer,Integer> getMapIntersect(String newids,String oldids) {
    	
    	int[] new_ids = getArray(newids);
    	int[] old_ids = getArray(oldids);
    	return getMapContain(new_ids,old_ids);

    }
    
    /**
     * 取两个数组交集，然后返回List<Integer>结果集
     * @param s1
     * @param s2
     * @return
     * 
     * @author sangshuan
     */
    public static Map<Integer,Integer> getMapContain(int[] newids,int[] oldids) {
    	
    	Map<Integer,Integer> data = new HashMap<Integer,Integer>();
    	
    	if ( BaseIntUtil.isBlank(newids) || BaseIntUtil.isBlank(oldids) )
    		return data;

    	
    	for ( int i = 0 ; i < newids.length ; i ++ ) {
    		
    		boolean add = false;
    		
    		for (int j = 0 ; j < oldids.length ; j ++ ) {
    			if ( newids[i] == oldids[j] && i != j ) {
    				add = true;
    				break;
    			}
    		}
    		
    		if ( add ) {
    			data.put(newids[i], i + 1);
    		}
    		
    	}
    	
    	return data;
    }
    
    
    /**
     * 取两个数组的差集以newids为主，去除包含exitids部分
     * @param s1
     * @param s2
     * @return
     * 
     * @author sangshuan
     */
    public static Map<Integer,Integer> getMapNotIn(String newids,String exitids) {
    	int[] new_ids = getArray(newids);
    	int[] exit_ids = getArray(exitids);
    	return getMapNotIn(new_ids,exit_ids);
    }
    
    /**
     * 取两个数组的差集以newids为主，去除包含exitids部分
     * @param s1
     * @param s2
     * @return
     * 
     * @author sangshuan
     */
    public static Map<Integer,Integer> getMapNotIn(int[] newids,int[] exitids) {
    	
    	Map<Integer,Integer> data = new HashMap<Integer,Integer>();
    	
    	if ( BaseIntUtil.isBlank(newids))
    		return data;
    	
    	for ( int i = 0 ; i < newids.length ; i ++ ) {
    		
    		boolean remove = false;
    		
    		for (int j = 0 ; exitids != null && j < exitids.length ; j ++ ) {
    			if ( newids[i] == exitids[j]) {
    				remove = true;
    				break;
    			}
    		}
    		
    		if ( !remove ) {
    			data.put(newids[i], i + 1);
    		}
    		
    	}
    	
    	return data;
    }
    
    
    public static void main(String[] args) {
    	
    	String s1 = "5,7,3,4,1";
    	String s2 = "";
    	Map<Integer,Integer> newList = getMapNotIn(s1,s2);
    	
    	for ( Integer id : newList.keySet() ) {
    		//System.out.println(id.toString() + "  " + newList.get(id) ) ;
    	}
    }
    
}
