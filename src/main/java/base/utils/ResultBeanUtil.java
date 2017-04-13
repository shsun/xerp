package base.utils;

import base.result.ResultBean;
import base.result.ResultBeanConstant;

/**
 * 
 * 2016/7/27 18:24
 * 
 */
public class ResultBeanUtil {

    /**
     *
     * @param resultBean
     * @return
     */
    public static boolean isError(ResultBean resultBean){
        if(resultBean == null){
            return false;
        }

        if(resultBean.getCode().equals(ResultBeanConstant.SUCCESS_CODE)){
            return false;
        }

        return true;
    }
}
