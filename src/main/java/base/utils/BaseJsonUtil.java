package base.utils;

import base.httpclient.HttpClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;

/**
 * 
 * Time: 14-7-11 上午11:55
 */
public class BaseJsonUtil {
    public static final Log log = LogFactory.getLog(BaseJsonUtil.class);


    public static void main(String[] args) {
        String path = "";
        String json = BaseStringUtil.getContentFromPathOrUrl(path);

        JSONObject jsonObjectRoot = JSONObject.fromObject(json);

        String msg = jsonObjectRoot.getString("msg");

        JSONArray jsonArray = jsonObjectRoot.getJSONArray("list");
        for (Object object : jsonArray) {
            JSONObject jsonObject2 = (JSONObject) object;
//                    BookBean bookBean = new BookBean(jsonObject);
//                    list.add(bookBean);
        }
    }

    public static void writeJsonArray(Object obj, String jsonPath, String jsonName) {
        writeJson(obj, jsonPath, jsonName, true);
    }

    public static void writeJson(Object obj, String jsonPath, String jsonName) {
        writeJson(obj, jsonPath, jsonName, false);
    }

    public static void writeJson(Object obj, String jsonPath, String jsonName, boolean array) {
        String json = "";
        if (array) {
            json = JSONArray.fromObject(obj).toString();
        } else {
            json = JSONObject.fromObject(obj).toString();
        }

        File folder = new File(jsonPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String path = jsonPath + File.separator + jsonName;
        File jsonFile = new File(path);
        try {
            FileUtils.write(jsonFile, json, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param url
     * @return
     */
    public static JSONObject getJSONObject(String url){
        String json = null;
        try {
            json = HttpClientUtil.getResponseContent(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(json);
        return jsonObject;
    }

    /**
     *
     * @param url
     * @return
     */
    public static JSONArray getJSONArray(String url){
        String json = null;
        try {
            json = HttpClientUtil.getResponseContent(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = JSONArray.fromObject(json);
        return jsonArray;
    }


}
