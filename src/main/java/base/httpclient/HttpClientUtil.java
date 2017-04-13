package base.httpclient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import base.utils.BaseStringUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * 登陆 获取cookie
 *
 * @author shsun
 */
public class HttpClientUtil {
    public static final Log log = LogFactory.getLog(HttpClientUtil.class);

    public static void main(String[] args) throws IOException {
        String urlStr = "http://www.cha60.com/chapter.jsp?bookId=1111111111111111111111111111";

        String str = HttpClientUtil.getResponseContent(urlStr);
        //System.out.println(str);
    }

    public static String getResponseContent(String url) throws IOException {
        return getHtml(url, null);
    }

    /**
     * @param url
     * @param encode
     * @return
     */
    public static String getHtml(String url, String encode) {
        if (BaseStringUtil.isBlank(encode)) {
            encode = "UTF-8";
        }

        if(BaseStringUtil.isBlank(url)){
            log.error("url=" + url);
            return "";
        }

        url = url.replace(" ", "%20");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            httpGet.addHeader("Content-Type", "text/html;charset=" + encode);
            httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; GoogleToolbar 2.0.114-big; Windows XP 5.1)");
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate");
            httpGet.setHeader("Connection", "keep-alive");

            int timeOut = 60*1000;
            //超时设置
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut)
                    .setConnectTimeout(timeOut)
                    .setConnectionRequestTimeout(timeOut).build();
            httpGet.setConfig(requestConfig);
            response = httpclient.execute(httpGet);
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String str =  EntityUtils.toString(entity, encode);
                return str;
			} else {
				//log.error(url + ": status=" + status);
				return "";
			}
        } catch (ConnectionPoolTimeoutException e) {
			log.error("1--> ConnectionPoolTimeoutException:" + url, e);
		} catch (ConnectTimeoutException e) {
            log.error("2--> ConnectTimeoutException" + url, e);
		} catch (SocketTimeoutException e) {
            log.error("3--> SocketTimeoutException" + url, e);
		} catch (Exception e) {
            log.error("4--> Exception" + url, e);
		}
        return "";
    }

}