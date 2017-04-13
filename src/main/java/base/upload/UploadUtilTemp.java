package base.upload;

import base.SystemGlobal;
import base.utils.BaseFileUtil;
import base.utils.BaseStringUtil;
import base.utils.SystemGlobalUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * 2016/6/12 14:56
 * 
 */
public class UploadUtilTemp {
    private static final Log log = LogFactory.getLog(UploadUtilTemp.class);

    private static String extNames = "jpg,jpeg,png,doc,docx,xlsx,mp3,wav";

    /**
     * @param request
     * @return
     * @throws java.io.IOException
     */
    public static UploadBean uploadOne(HttpServletRequest request, String fileName) throws IOException, UploadException {
        List<UploadBean> list = uploadMany(request, fileName);
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    /**
     * @param fileName
     * @throws base.upload.UploadException
     */
    private static void validateExtion(String fileName) throws UploadException {
        String extName = BaseFileUtil.getExtName(fileName);
        if (BaseStringUtil.isBlank(extName)) {
            throw new UploadException("文件扩展名为空");
        }
        String[] array = BaseStringUtil.getArray(extNames);
        for (String str : array) {
            if (extName.toLowerCase().equals(str)) {
                return;
            }
        }

        throw new UploadException("不允许这种扩展名文件上传到服务器上: " + extName);
    }

    /**
     * @param request
     * @return
     * @throws java.io.IOException
     */
    public static List<UploadBean> uploadMany(HttpServletRequest request, String userFileName) throws IOException, UploadException {
        //创建一个通用的多部分解析器
        ServletContext servletContext = request.getSession().getServletContext();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);

        //判断 request 是否有文件上传,即多部分请求
        if (!multipartResolver.isMultipart(request)) {
            log.info("没有文件上传.... ");
            return null;
        }

        //转换成多部分request
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

        List<UploadBean> list = new ArrayList<>();
        //取得request中的所有文件名
        Iterator<String> fileNameIterator = multipartHttpServletRequest.getFileNames();
        while (fileNameIterator.hasNext()) {
            //取得上传文件
            String fileName = fileNameIterator.next();
            MultipartFile multipartFile = multipartHttpServletRequest.getFile(fileName);
            if (multipartFile == null) {
                continue;
            }

            //取得当前上传文件的文件名称
            String myFileName = multipartFile.getOriginalFilename();
            if (BaseStringUtil.isBlank(myFileName)) {
                continue;
            }

            String originalFilename = multipartFile.getOriginalFilename();

            validateExtion(originalFilename);

            String relativeFileName = UploadUtilTemp.getRelativeFileName(originalFilename);
            //定义上传路径
            String path = SystemGlobal.getPreference("file.upload.path");
            if (SystemGlobalUtil.isLocal()) {
                path = SystemGlobalUtil.getWebRoot() + "/upload/";
            }

            String localFilePath = path + userFileName + "/" + relativeFileName;
            File localFile = new File(localFilePath);
            localFile.mkdirs();
            multipartFile.transferTo(localFile);

            UploadBean uploadBean = new UploadBean(localFile, originalFilename, relativeFileName);
            list.add(uploadBean);
        }

        return list;
    }

    /**
     * @param file
     * @param width
     * @param height
     * @throws java.io.IOException
     * @throws InterruptedException
     * @throws org.im4java.core.IM4JavaException
     */
    public static void zoom(File file, int width, int height) throws IOException, InterruptedException, IM4JavaException {
        String absoluteFileName = file.getAbsolutePath();
        zoom(absoluteFileName, absoluteFileName, width, height);
    }

    /**
     * @param absoluteFileName  原始图片完整路径
     * @param absoluteFileName2 缩放后图片完整路径
     * @param width             draw后的宽度
     * @param height            draw后的高度
     * @throws java.io.IOException
     * @throws InterruptedException
     * @throws org.im4java.core.IM4JavaException
     */
    public static void zoom(String absoluteFileName, String absoluteFileName2, int width, int height) throws IOException, InterruptedException, IM4JavaException {
        IMOperation imOperation = new IMOperation();
        imOperation.addImage();
        imOperation.resize(width, height);
        //            op.font("Arial").fill("red").draw("text 30,30 www.taobao.com");
        imOperation.quality(85d);
        imOperation.addImage();
        //IM4JAVA是同时支持ImageMagick和GraphicsMagick的，如果为true则使用GM，如果为false支持IM。
        ConvertCmd convertCmd = new ConvertCmd(true);
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0) {  //linux下不要设置此值，不然会报错
            convertCmd.setSearchPath("C:\\Program Files\\GraphicsMagick-1.3.21-Q8");
        }
        convertCmd.setErrorConsumer(StandardStream.STDERR);
        convertCmd.run(imOperation, absoluteFileName, absoluteFileName2);
    }

    /**
     * @param args
     */
    public static void main(String args[]) {
        try {
            zoom("c://img/1.jpg", "c://img/1_2.jpg", 300, 400);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param fileName
     * @return
     */
    public static String getRelativeFileName(String fileName) {
        String extName = BaseFileUtil.getExtName(fileName);
        if (BaseStringUtil.isBlank(extName)) {
            return "";
        }
        String fileName2 = System.currentTimeMillis() + "." + extName;
        Calendar calendar = Calendar.getInstance();
        String folder = calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.WEEK_OF_YEAR);

        return folder + "/" + fileName2;
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static void deleteFile(String sPath) {
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除文件夹
     *
     * @param sPath 被删除的文件夹路径
     * @return 删除成功返回true，否则返回false
     */
    public static void deleteDirectory(String sPath) {
        File file = new File(sPath);
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                    deleteDirectory(files[i].getPath());//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        }
    }


}
