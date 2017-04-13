package base.utils;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import base.constant.BaseConstant;
import base.result.ResultBean;

/**
 * 
 * Time: 12-10-11 下午3:30
 */
public class BaseResponseUtil {
    private static final Log log = LogFactory.getLog(BaseResponseUtil.class);
    /**
     * @param @param response
     * @param @param res    响应字符串
     * @return void    返回类型
     * @throws
     * @Title: responseJson
     * @Description: JSON 格式 响应
     */
    public static void responseJson(HttpServletResponse response, String res) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(res);
            out.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
//
//
//    /**
//     *
//     * @param response
//     * @param code
//     */
//    public static void responseAjaxJson2(HttpServletResponse response, int code) {
//        String msg = "出错了";
//        responseAjaxJsonOk(response, code, msg);
//    }

    /**
     * @param response
     * @param id
     */
    public static void responseAjaxJsonOKId(HttpServletResponse response, Object id) {
        JSONObject res = new JSONObject();
        try {
            res.put("code", BaseConstant.SUCCESS_CODE);
            res.put("msg", BaseConstant.SUCCESS_MSG);
            res.put("id", id);
            responseJson(response, res.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @param response
     */
    public static void responseAjaxJsonOk(HttpServletResponse response) {
        responseAjaxJson(response, BaseConstant.SUCCESS_CODE, BaseConstant.SUCCESS_MSG);
    }

    /**
     * @param response
     * @param msg
     */
    public static void responseAjaxJsonOK(HttpServletResponse response, String msg) {
        responseAjaxJson(response, BaseConstant.SUCCESS_CODE, msg);
    }

    /**
     *
     * @param response
     * @param code
     * @param msg
     */
    public static void responseAjaxJson(HttpServletResponse response, String code, String msg) {
        JSONObject res = new JSONObject();
        try {
            res.put("code", code);
            res.put("msg", msg);
            responseJson(response, res.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @param response
     * @param id
     */
    public static void responseAjaxJson(HttpServletResponse response, String code ,String msg , Object data) {
        JSONObject res = new JSONObject();
        try {
            res.put("code", code);
            res.put("msg", msg);
            res.put("data", data);
            responseJson(response, res.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    
    /**
     *
     * @param response
     * @param resultBean
     */
    public static void responseAjaxJson(HttpServletResponse response,ResultBean resultBean) {
        responseAjaxJson(response,resultBean.getCode(),resultBean.getMsg());
    }

    /**
     *
     * @param response
     * @param msg
     */
    public static void responseAjaxJson(HttpServletResponse response, String msg) {
        JSONObject res = new JSONObject();
        try {
            res.put("msg", msg);
            responseJson(response, res.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    
    public static void responseAjaxJson(HttpServletResponse response, Collection<?> collection) {
    	JsonConfig jsonConfig = new JsonConfig();
    	jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray jArrRes = JSONArray.fromObject(collection,jsonConfig);
        try {
            responseJson(response, jArrRes.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
