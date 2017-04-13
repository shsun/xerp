package base.tool.upload;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import base.SystemGlobal;
import base.result.ResultBeanConstant;
import base.tool.ProgressManage;
import base.tool.ProgressStatus;
import base.utils.BaseDateUtil;
import base.utils.BaseIntUtil;
import base.utils.BaseResponseUtil;
import base.utils.BaseStringUtil;

public class FileUploadSupport {
	
	
	public String upload_success_code = "200";
	
	public String upload_error_code = "0";
	
	public String import_finish_code = "300";
	
	public int upload_max_size = 1024 * 1024 * 1024;
	
	
	
	/**
	 * 获取Model对象
	 * @return
	 */
	private FileUploadModel getModel() {
		return new FileUploadModel();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FileUploadModel servletFileUpload(HttpServletRequest request,String uid,String specifiedType) {

		FileUploadModel model = this.getModel();
		model.setCode(this.upload_success_code);
		List<String> messages = model.getMessages();
		Map<String,Object> fieldMap = model.getFieldMap();
		
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			model.setCode(this.upload_error_code);
			messages.add(this.messageLog("请使用文件表单上传,操作退出......"));
			return model;
		}

		messages.add(this.messageLog("=================================================="));
		messages.add(this.messageDateLog("开始读取文件..."));

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(this.upload_max_size);
		// 监听读取进度
//		upload.setProgressListener(new ProgressListener() {
//			public void update(long bytesRead, long contentLength, int items) {
//				ProgressStatus status = new ProgressStatus();
//				status.setBytesRead(bytesRead);
//				status.setContentLength(contentLength);
//				status.setItems(items);
//			}
//		});

		try {
			List<FileItem> fields = upload.parseRequest(request);

			messages.add(this.messageDateLog("文件读取完毕..."));
			messages.add(this.messageDateLog("开始保存文件..."));

			Iterator iter = fields.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				String fieldName = item.getFieldName();

				if (item.isFormField()) {
					// common feild
					String value = item.getString();
					fieldMap.put(fieldName, value);
				} else {
					// file feild
					String name = item.getName();
					name = name.substring(name.lastIndexOf("\\") + 1);
//					String fileName = name.substring(0,name.lastIndexOf("."));
					String fileType = name.substring(name.lastIndexOf(".") + 1,name.length());
					if (!specifiedType.equals(fileType)) {
						model.setCode("0");
						messages.add(this.messageDateLog("文件类型错误,提交文件格式为:" + fileType + ";要求格式应为:" + specifiedType));
						break;
					}
					messages.add(this.messageDateLog("开始保存文件[" + name + "]"));
					final String filePath = this.getFilePath(request, uid, name, fileType);
					item.write(new File(filePath));
					messages.add(this.messageDateLog("文件[" + name + "]保存成功"));
					fieldMap.put(fieldName, filePath); //暂时保存全路径
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.setCode("0");
			messages.add(this.messageDateLog(e.getMessage()));
		}

		messages.add(this.messageLog("=================================================="));
	
		return model;
	}
	
	
	/**
	 * 根据id查询导入状态
	 * @param request
	 * @param response
	 */
	public void servletImportStatus(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		Vector<Object> messages = null;
		String code = ResultBeanConstant.SUCCESS_CODE;
		ProgressStatus status = null;
		if ( BaseStringUtil.isNotBlank(id) ) {
			messages = ProgressManage.getRecentMessages(id);
			status = (ProgressStatus)ProgressManage.getProgress(id);
			boolean isFinish = ProgressManage.isFinish(id);
			if (isFinish) {
				code = this.import_finish_code;
			}
			int requestIndex = BaseIntUtil.getInt(request, "requestIndex");
			status.setRequestIndex(requestIndex);
		}
		this.responseStatusJson(response, code, status, messages);
	}
	
	/**
	 * response 状态信息
	 * @param response
	 * @param code
	 * @param status
	 * @param messages
	 */
	public void responseStatusJson(HttpServletResponse response,String code,Object status,Vector<Object> messages) {
		JSONObject res = new JSONObject();
        try {
            res.put("code", code);
            res.put("status", net.sf.json.JSONObject.fromObject(status));
            res.put("data", messages);
            BaseResponseUtil.responseJson(response, res.toString());
        } catch (Exception e) {
        	
        }
	}
	
	/**
	 * 打印消息
	 * @param message
	 * @return
	 */
	public String messageLog(String message) {
		//System.out.println(message);
		return message;
	}
	
	/**
	 * 打印带日期格式的消息
	 * @param message
	 * @return
	 */
	public String messageDateLog(String message) {
		message = BaseDateUtil.getFormatStringSecond(new Date()) + " " + message;
		//System.out.println(message);
		return message;
	}
	
	/**
	 * 获取文件保存路径(程序不会自动创建文件夹，在实现路径的时候需要确认文件夹已经存在)
	 * @param request
	 * @param uid
	 * @param fileName
	 * @param fileType
	 * @return
	 */
	public String getFilePath(HttpServletRequest request,String uid,String fileName,String fileType) {
		return SystemGlobal.getPreference("webRoot") + "/temp/" + uid + fileType;
//		return request.getRealPath("/") + "temp/" + uid + "." + fileType;
	}
}
