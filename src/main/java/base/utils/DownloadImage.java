package base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * Time: 15-5-27 上午8:26
 */
public class DownloadImage {
    	public static void main(String[] args) throws Exception {
		download("http://p0.so.qhimg.com/t015534a75aa3717fcf.jpg","c:\\","","t015534a75aa3717fcf.jpg");
	}

    /**
     *
     * @param urlString
     * @param baseDir : "/home/shsun/htdocs/img/laolianggushi/"
     * @param path : "5"
     * @param filename : "1.jpg"
     * @return : "5/1.jpg"
     * @throws Exception
     */
	public static String download(String urlString,String baseDir,String path, String filename) throws Exception {
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(5 * 1000);
        con.setReadTimeout(5*1000);
		// 输入流
		InputStream is = con.getInputStream();

		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流

        String savePath = baseDir + path;
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		OutputStream os = new FileOutputStream(file.getPath() + File.separator + filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 关闭
		os.close();
		is.close();

        return path + "/" + filename;
	}
}
