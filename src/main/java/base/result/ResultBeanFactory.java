package base.result;


/**
 * 
 * Time: 15-1-19 下午5:46
 */
public class ResultBeanFactory {

    private static ResultBean resultBeanSuccess = null;

    /**
     *
     * @return
     */
    public static ResultBean getResultBeanSuccess(){
        if(resultBeanSuccess!=null){
            return resultBeanSuccess;
        }
        resultBeanSuccess = new ResultBean();
        resultBeanSuccess.setCode(ResultBeanConstant.SUCCESS_CODE);
        resultBeanSuccess.setMsg(ResultBeanConstant.SUCCESS_MSG);
        return resultBeanSuccess;
    }
    
    
    
}
