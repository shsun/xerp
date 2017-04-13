package base.servlet;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import base.SystemGlobal;
import base.tool.fileupload.MutiFileUpload;

/**
 * 文件上传 单文件 多文件  
 * @author shsun
 *
 */
@Controller("UploadServlet")
public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		try {
			MutiFileUpload upload=new MutiFileUpload();
			//文件路径可以通过配置文件system.properties获取
			String path= SystemGlobal.getPreference("UPLOAD_IMAGE_DIR");
			File parent = new File(path);
			//执行上传
			upload.parseRequest(httpServletRequest);
			List<HashMap<String,String>> list=upload.mainUpload(parent,path);
			//System.out.println(list.size());
        }catch (Exception e) {
            e.printStackTrace();
        }
        
	}
}
