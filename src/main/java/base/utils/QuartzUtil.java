package base.utils;

import java.util.Calendar;

/**
 * 
 * Time: 15-4-13 上午8:36
 */
public class QuartzUtil {

    /**
     *
     * @param id
     * @param step
     * @return
     */
    public static boolean notDo(Integer id,int step){
        if(id==null){
            return true;
        }
        if(step==0){
            return false;
        }
        int day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        if(id%step==day%step){
            return false;
        }
        return true;
    }
}
