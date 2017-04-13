package base.utils;

import base.bean.PageBean;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Time: 12-12-25 下午1:31
 */
public class BasePageBeanUtil {

    /**
     * @param pageNo
     * @param length
     * @return
     */
    public static PageBean getPageBean(int pageNo, int length) {
        PageBean pageBean = new PageBean();
        pageBean.setLength(length);
        pageBean.setPageNo(pageNo);
        pageBean.init();
        return pageBean;
    }

    /**
     * @param pageNo
     * @param begin
     * @param length
     * @return
     */
    public static PageBean getPageBean(int pageNo, int begin, int length) {
        PageBean pageBean = new PageBean(begin, length);
        pageBean.setPageNo(pageNo);
        pageBean.init();
        return pageBean;
    }

    /**
     *
     * @param request
     * @return
     */
    public static PageBean getPageBean(HttpServletRequest request) {
        int length = BaseIntUtil.getInt(request, "length");
        return getPageBean(request,length);
    }
    
    /**
    *
    * @param request
    * @return
    */
   public static PageBean getPageBean(HttpServletRequest request,int length) {
       return getPageBean(request,0,length);
   }

    public static PageBean getPageBean(HttpServletRequest request,int seq,int length) {
        int begin = BaseIntUtil.getInt(request, "begin");
        String pageNoName = "pageNo";
        if(seq!=0){
            pageNoName = "pageNo_" + seq;
        }
        int pageNo = BaseIntUtil.getInt(request, pageNoName);

        int lengthForm = BaseIntUtil.getInt(request, "length");
        if(lengthForm!=0){
            length = lengthForm;
        }

        PageBean pageBean = new PageBean(length,begin);
        pageBean.setPageNo(pageNo);
        pageBean.init();
        return pageBean;
    }

}
