package base.upload;

import java.io.File;

import base.utils.BaseFileUtil;

/**
 * 
 * 2016/6/12 14:50
 * 
 */
public class UploadBean {

    private String extension;//扩展名
    private String fileName; //新文件名
    private String absoluteFileName; //绝对路径
    private int sizeK; //文件大小
    private String relativeFileName; //相对路径
    private String originalFilename;//原始文件名

    public UploadBean() {
    }

    public UploadBean(File file,String originalFilename,String relativeFileName) {
        this.extension = BaseFileUtil.getExtName(file.getName());
        this.fileName = file.getName();
        this.absoluteFileName = file.getAbsolutePath();
        this.sizeK = (int) (file.length()/1024);
        this.originalFilename = originalFilename;
        this.relativeFileName = relativeFileName;
    }


    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRelativeFileName() {
        return relativeFileName;
    }

    public void setRelativeFileName(String relativeFileName) {
        this.relativeFileName = relativeFileName;
    }

    public String getAbsoluteFileName() {
        return absoluteFileName;
    }

    public void setAbsoluteFileName(String absoluteFileName) {
        this.absoluteFileName = absoluteFileName;
    }

    public int getSizeK() {
        return sizeK;
    }

    public void setSizeK(int sizeK) {
        this.sizeK = sizeK;
    }
}
