package base.helper;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import org.apache.commons.io.FileUtils;

import base.utils.BaseStringUtil;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 图片加水印，自动缩放等类，该类需要ImageMagick和JImageMaigick配合使用<br>
 * 该类CMS专用 <br>
 * 使用该类注意事项 <br>
 * 特别注意： <br>
 * 1、服务器上安装ImageMagick <br>
 * 下载地址：Linux: ftp://ftp.imagemagick.org/pub/ImageMagick/ImageMagick.tar.gz <br>
 * Windows：ftp://ftp.imagemagick.org/pub/ImageMagick/binaries/ImageMagick-6.2.6-8-Q16-windows-dll.exe
 * <br>
 * 2、服务器上安装：JMagick (PATH=/home5/jdk1.5.0_07/bin:$PATH) <br>
 * 下载地址：Linux：http://www.yeo.id.au/jmagick/quickload/JMagick-6.2.6-0.tar.gz <br>
 * Windows：http://www.yeo.id.au/jmagick/quickload/win-6.2.6/jmagick-6.2.6-win.zip
 * <br>
 * winodws 下使用6.3.9版本。 3、配置环境变量 <br>
 * 修改myconfig.sh <br>
 * 把 JAVA_OPTS="-server";export JAVA_OPTS <br>
 * 换成 JAVA_OPTS="-Djava.awt.headless=true -Djava.library.path=/usr/local/lib
 * -Djmagick.systemclassloader=no -server";export JAVA_OPTS <br>
 *
 * @author Yangsy <br>
 * @author garmbrood Date: 2006-4-25 10:14:37 <br>
 */
public class JmagickUtils {

    public static final int POS_TYPE_LEFT_TOP = 1;
    public static final int POS_TYPE_RIGHT_TOP = 2;
    public static final int POS_TYPE_LEFT_BOTTOM = 3;
    public static final int POS_TYPE_RIGHT_BOTTOM = 4;
    public static final int POS_TYPE_CENTER = 5;

    public static final Map<String, MagickImage> logoCache = new HashMap<String, MagickImage>();

    static {
        System.setProperty("jmagick.systemclassloader", "no");
    }

    /**
     * 获得宽高
     *
     * @param picFrom 图片地址
     * @return 返回宽
     */
    public static String getWidthHeight(String picFrom) throws MagickException {
        MagickImage image = new MagickImage(new ImageInfo(picFrom));

        Dimension dimension = image.getDimension();
        int iWi = (int) dimension.getWidth();
        int iHi = (int) dimension.getHeight();
        return iWi + "," + iHi;
    }

    public static boolean zoom(String inputDir, String outputDir,String inputFileName,String outputFileName, int width,
                               int height, boolean scale) {
           File outputFolder = new File(outputDir);
        if(!outputFolder.exists()){
            outputFolder.mkdirs();
        }
        if(!inputDir.endsWith("/")){
           inputDir = inputDir + "/";
        }
        if(!outputDir.endsWith("/")){
            outputDir = outputDir + "/";
        }

        if(BaseStringUtil.isBlank(outputFileName)){
            outputFileName = inputFileName;
        }

        String picFrom = inputDir + inputFileName;
        String picTo = outputDir + outputFileName;

        return zoom(picFrom,picTo,width,height,scale);
    }


    public static boolean zoom(String picFrom, int width,
                               int height) {
        if(BaseStringUtil.isBlank(picFrom)){
            return false;
        }

        String picTo = picFrom.replace(".jpg","_s.jpg");
        return zoom(picFrom,picTo,width,height,false);
    }

    /**
     * 缩放图片
     *
     * @param picFrom 待添加水印的图片地址
     * @param picTo   缩放后的图片地址
     * @param width   缩放后的宽度
     * @param height  缩放后的高度
     * @param scale   是等比缩放，如果是等比缩放，则以最 width和 height表示最长的宽度和高度
     * @return 是否缩放成功
     */
    public static boolean zoom(String picFrom, String picTo, int width,
                               int height, boolean scale) {

        try {
            width = width == 0 ? 500 : width;
            // Resize
            ImageInfo info = new ImageInfo(picFrom);
            MagickImage image = new MagickImage(new ImageInfo(picFrom));
            MagickImage scaled = null;// 小图片文件的大小.

            Dimension dimension = null;
            try {
                dimension = image.getDimension();
            } catch (MagickException e) {
                //System.out.println("MagickException: picFrom=" + picFrom);
                e.printStackTrace();
            }
            int iWi = (int) dimension.getWidth();
            int iHi = (int) dimension.getHeight();

            if ((iWi <= width && width > 0) && (iHi <= height && height > 0)) {
                File srcFile = new File(picFrom);
                File destFile = new File(picTo);
                try {
                    FileUtils.copyFile(srcFile, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            if (!scale) {
                scaled = image.scaleImage(width, height);
            } else {
                int toWidth;
                int toHeight;
                if (width < 1) {
                    toWidth = (int) (((float) height / (float) iHi) * iWi);
                    toHeight = height;
                } else if (height < 1) {
                    toWidth = width;
                    toHeight = (int) (((float) width / (float) iWi) * iHi);
                } else {
                    toWidth = width;
                    toHeight = (int) (((float) width / (float) iWi) * iHi);
                }
                image.profileImage("*", null);// 移除图片的其他信息
                scaled = image.scaleImage(toWidth, toHeight);
            }

            scaled.setFileName(picTo);
            info.setQuality(90);
            scaled.writeImage(info);
           // //System.out.println(picFrom+" zoom cost "+(System.currentTimeMillis()-starttime)+" ms");
        } catch (MagickException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 缩放图片
     *
     * @param picFrom 待添加水印的图片地址
     * @param picTo   缩放后的图片地址
     * @param width   缩放后的宽度
     * @param height  缩放后的高度
     * @param scale   是等比缩放，如果是等比缩放，则以最 width和 height表示最长的宽度和高度
     * @return 是否缩放成功
     */
    public static boolean resizeAll(String picFrom, String picTo, int width,
                                    int height, boolean scale) {

        try {
            // Resize
            ImageInfo info = new ImageInfo(picFrom);
            MagickImage image = new MagickImage(new ImageInfo(picFrom));
            MagickImage scaled = null;// 小图片文件的大小.

            Dimension dimension = image.getDimension();
            int iWi = (int) dimension.getWidth();
            int iHi = (int) dimension.getHeight();

            if ((iWi <= width && width > 0) && (iHi <= height && height > 0)) {
                File srcFile = new File(picFrom);
                File destFile = new File(picTo);
                try {
                    FileUtils.copyFile(srcFile, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            if (!scale) {
                scaled = image.scaleImage(width, height);
            } else {
                int toWidth = 0;
                int toHeight = 0;
                if(width<1){
                    if(iHi<height){
                        toWidth = iWi;
                        toHeight = iHi;
                    }else {
                        toWidth =  (int) (((float) iWi / (float) iHi) * height);
                        toHeight = height;
                    }
                } else if(height<1){
                    if(iWi<width){
                        toWidth = iWi;
                        toHeight = iHi;
                    }else {
                        toWidth = width;
                        toHeight = (int) (((float) width / (float) iWi) * iHi);
                    }
                }  else if(iWi >= iHi){
                    if(iWi>=width){
                        toWidth = width;
                        toHeight = (int) (((float) width / (float) iWi) * iHi);
                    }else {
                        toWidth = (int) (((float) iWi / (float) iHi) * height);
                        toHeight = height;
                    }
                }  else if(iWi < iHi){
                    if(iHi>=height){
                        toWidth = (int) (((float) iWi / (float) iHi) * height);
                        toHeight = height;
                    } else {
                        toWidth = width;
                        toHeight = (int) (((float) width / (float) iWi) * iHi);
                    }
                }

                image.profileImage("*", null);// 移除图片的其他信息
                scaled = image.scaleImage(toWidth, toHeight);
            }

            scaled.setFileName(picTo);
            info.setQuality(90);
            scaled.writeImage(info);
        } catch (MagickException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 加水印。建议水印文件使用透明png格式
     *
     * @param sourceFile 待加水印的图片
     * @param watermark  水印文件
     * @param targetFile 添加水印后的文件，即新生成的文件
     * @param x          边距
     * @param y          边距
     * @param align      位置,定义参加 JmagickUtils.POS_TYPE*
     * @return 是否添加成功
     */
    public static boolean generateMark(String sourceFile, String watermark,
                                       String targetFile, int x, int y, int align) {
        try {
            //目标文件
            File _file = new File(sourceFile);
            BufferedImage src = ImageIO.read(_file);
            int width = src.getWidth();
            int height = src.getHeight();
            Graphics g = src.createGraphics();
            //System.out.println("--------------img:"+watermark);
            //水印文件
            BufferedImage warterImg = ImageIO.read(new File(watermark));
            int warterWidth = warterImg.getWidth();
            int warterHeight = warterImg.getHeight();
            //大图宽度如果小于5倍缩略图宽度，则缩小缩略图大小
            if (warterWidth * 4 > width) {
                int _warterWidth = warterWidth * width / (warterWidth * 4);
                int _warterHeight = warterHeight * _warterWidth / warterWidth;
                warterWidth = _warterWidth;
                warterHeight = _warterHeight;
            }
            int[] position = getPosition(width, height, warterWidth, warterHeight, x, y, align);
            g.drawImage(warterImg, position[0], position[1], warterWidth, warterHeight, null);
            g.dispose();

            ImageWriter writer = null;
            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("JPEG");
            if (iter.hasNext()) {
                writer = iter.next();
            }
            IIOImage iioImage = new IIOImage(src, null, null);
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.9f);
            ImageOutputStream outputStream = ImageIO.createImageOutputStream(new File(targetFile));
            writer.setOutput(outputStream);
            writer.write(null, iioImage, param);
            writer.dispose();
            //System.out.println("watermark=" + watermark + " sourcfile=" + sourceFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 对png图片加水印。建议水印文件使用透明png格式
     *
     * @param sourceFile 待加水印的图片
     * @param watermark  水印文件
     * @param targetFile 添加水印后的文件，即新生成的文件
     * @param x          边距
     * @param y          边距
     * @param align      位置,定义参加 JmagickUtils.POS_TYPE*
     * @return 是否添加成功
     */
    public static boolean generatePngMark(String sourceFile, String watermark,
                                          String targetFile, int x, int y, int align) {
        try {
            //目标文件
            File _file = new File(sourceFile);
            BufferedImage src = ImageIO.read(_file);
            int width = src.getWidth();
            int height = src.getHeight();
            Graphics g = src.createGraphics();

            //水印文件
            BufferedImage warterImg = ImageIO.read(new File(watermark));
            int warterWidth = warterImg.getWidth();
            int warterHeight = warterImg.getHeight();
            //大图宽度如果小于5倍缩略图宽度，则缩小缩略图大小
            if (warterWidth * 4 > width) {
                int _warterWidth = warterWidth * width / (warterWidth * 4);
                int _warterHeight = warterHeight * _warterWidth / warterWidth;
                warterWidth = _warterWidth;
                warterHeight = _warterHeight;
            }
            int[] position = getPosition(width, height, warterWidth, warterHeight, x, y, align);
            g.drawImage(warterImg, position[0], position[1], warterWidth, warterHeight, null);
            g.dispose();

            ImageWriter writer = null;
            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("PNG");
            if (iter.hasNext()) {
                writer = iter.next();
            }
            IIOImage iioImage = new IIOImage(src, null, null);
            ImageOutputStream outputStream = ImageIO.createImageOutputStream(new File(targetFile));
            writer.setOutput(outputStream);
            writer.write(null, iioImage, null);
            writer.dispose();
            //System.out.println("watermark=" + watermark + " sourcfile=" + sourceFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    /**
     * 返回水印打印的坐标
     *
     * @param width   图片宽度
     * @param height  图片高度
     * @param wwidth  水印宽度
     * @param wheight 水印高度
     * @param marginX 横向边距
     * @param marginY 纵向边距
     * @param align   浮动位置
     * @return [x坐标，y坐标]
     */
    private static int[] getPosition(int width, int height, int wwidth, int wheight, int marginX, int marginY, int align) {
        int[] position = new int[2];

        switch (align) {
            case POS_TYPE_LEFT_TOP://左上
                position[0] = marginX;
                position[1] = marginY;
                break;
            case POS_TYPE_LEFT_BOTTOM://左下
                position[0] = marginX;
                position[1] = height - (marginY + wheight);
                break;
            case POS_TYPE_RIGHT_TOP://右上
                position[0] = width - (marginX + wwidth);
                position[1] = marginY;
                break;

            case POS_TYPE_CENTER://居中
                position[0] = (width + wwidth) / 2;
                position[1] = (height + wheight) / 2;
                break;
            default://POS_TYPE_RIGHT_BOTTOM 右下
                position[0] = width - (marginX + wwidth);
                position[1] = height - (marginY + wheight);
                break;
        }
        return position;
    }

    public static boolean resizeForSoft(String picFrom, String picTo, int width,
                                        int height, boolean scale) {

        try {
            width = width == 0 ? 500 : width;
            // Resize
            ImageInfo info = new ImageInfo(picFrom);
            MagickImage image = new MagickImage(new ImageInfo(picFrom));
            MagickImage scaled = null;// 小图片文件的大小.

            Dimension dimension = image.getDimension();
            int iWi = (int) dimension.getWidth();
            int iHi = (int) dimension.getHeight();

            if ((iWi <= width && width > 0) && (iHi <= height && height > 0)) {
                File srcFile = new File(picFrom);
                File destFile = new File(picTo);
                try {
                    FileUtils.copyFile(srcFile, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            if (!scale) {
                scaled = image.scaleImage(width, height);
            } else {
                int toWidth;
                int toHeight;
                if (width < 1) {
                    toWidth = (int) (((float) height / (float) iHi) * iWi);
                    toHeight = height;
                } else if (height < 1) {
                    toWidth = width;
                    toHeight = (int) (((float) width / (float) iWi) * iHi);
                } else if (((float) width / (float) height) > ((float) iWi / (float) iHi)) {
                    toWidth = (int) (((float) height / (float) iHi) * iWi);
                    toHeight = height;
                } else {
                    toWidth = width;
                    toHeight = (int) (((float) width / (float) iWi) * iHi);
                }
                image.profileImage("*", null);// 移除图片的其他信息
                scaled = image.scaleImage(toWidth, toHeight);
            }

            scaled.setFileName(picTo);
            info.setQuality(90);
            scaled.writeImage(info);
        } catch (MagickException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) throws MagickException, IOException {
       System.setProperty("jmagick.systemclassloader", "no");
        String filepath = "C:\\Workspaces\\MyEclipse10\\abc\\web\\0\\1.jpg";
        JmagickUtils.zoom(filepath, 121, 75);

    }
}
