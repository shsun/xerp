package base.model;

import base.bean.PageBean;

/**
 * <p><b>Description:</b>model基类</p>
 *
 * @author sang
 * @date 2015-9-1下午1:53:44
 * @version 1.0
 */
public class BaseExample {

	private PageBean pageBean;

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }
}
