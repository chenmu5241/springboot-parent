package net.newglobe.app.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.newglobe.app.vo.DataResult;
import net.newglobe.app.vo.Result;

@Controller
@RequestMapping("webupload")
public class UploadController {
    public Logger logger = LoggerFactory.getLogger(UploadController.class);

    @RequestMapping("upload")
    @ResponseBody
    public DataResult<String> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file, String filePath) {
        DataResult<String> result = new DataResult<String>();
        try {
            Random random = new Random();
            String basePath = request.getServletContext().getRealPath("/");
            //判断文件路径是否存在不存在则递归创建
            String dirs = basePath + "upload" + File.separator + filePath;
            mkDir(new File(dirs));

            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = "upload" + File.separator + filePath + File.separator + new Date().getTime() + random.nextInt(100000) + suffix;//upload文件夹之后的路径
            file.transferTo(new File(basePath + File.separator + fileName));
            result.setSuccess(true);
            result.setData(fileName);
            System.out.println(fileName);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("上传异常！");
        }
        return result;
    }

    @RequestMapping(value = "deleteFile", method = RequestMethod.POST)
    @ResponseBody
    private Result deleteFile(HttpServletRequest request, String fileName) {
        Result result = new Result();
        try {
            String fullPath = request.getServletContext().getRealPath("/") + fileName;
            /* 构建文件目录 */
            File fileDir = new File(fullPath);
            if (fileDir.exists()) {
                fileDir.delete();
            }
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除旧图片遇到问题");
        }
        return result;
    }

    @RequestMapping("/uploadWithThumb")
    @ResponseBody
    public DataResult<Map<String, String>> uploadWithThumb(HttpServletRequest request, @RequestParam("file") MultipartFile file, String filePath, Integer width, Integer height) {
        DataResult<Map<String, String>> result = new DataResult<Map<String, String>>();
        try {
            Random random = new Random();
            String basePath = request.getServletContext().getRealPath("/");
            //判断文件路径是否存在不存在则递归创建
            String dirs = basePath + "upload" + File.separator + filePath;
            mkDir(new File(dirs));

            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = "upload" + File.separator + filePath + File.separator + new Date().getTime() + random.nextInt(100000) + suffix;//upload文件夹之后的路径
            File fileObj = new File(basePath + File.separator + fileName);
            file.transferTo(fileObj);
            //生成缩略图
            String thumbName = "upload" + File.separator + filePath + File.separator + "thumb" + File.separator + new Date().getTime() + random.nextInt(100000) + suffix;
            scale(fileObj, basePath + File.separator + thumbName, height, width, false);
            Map<String, String> map = new HashMap<String, String>();
            map.put("pic", fileName);
            map.put("thumb", thumbName);
            result.setSuccess(true);
            result.setData(map);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("上传异常！");
        }
        return result;
    }

    @RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
    @ResponseBody
    private Result deleteImage(HttpServletRequest request, String pic,String thumb) {
        Result result = new Result();
        try {
            String picPath = request.getServletContext().getRealPath("/") + pic;
            String thumbPath = request.getServletContext().getRealPath("/") + thumb;
            /* 构建文件目录 */
            File picFile = new File(picPath);
            File thumbFile = new File(thumbPath);
            if (picFile.exists()) {
                picFile.delete();
            }
            if (thumbFile.exists()) {
                thumbFile.delete();
            }
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除旧图片遇到问题");
        }
        return result;
    }


    public static void mkDir(File file) {
        if (file.getParentFile().exists()) {
            file.mkdir();
        } else {
            mkDir(file.getParentFile());
            file.mkdir();
        }
    }

    /**
     * 缩放图片方法
     *
     * @param f 要缩放的图片路径
     * @param result       缩放后的图片路径
     * @param height       目标高度像素
     * @param width        目标宽度像素
     * @param bb           是否补白
     */
    public void scale(File f, String result, int height, int width, boolean bb) {
        try {
            double ratio = 0.0; // 缩放比例
//            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);//bi.SCALE_SMOOTH  选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                double ratioHeight = (new Integer(height)).doubleValue() / bi.getHeight();
                double ratioWhidth = (new Integer(width)).doubleValue() / bi.getWidth();
                if (ratioHeight > ratioWhidth) {
                    ratio = ratioHeight;
                } else {
                    ratio = ratioWhidth;
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform//仿射转换
                        .getScaleInstance(ratio, ratio), null);//返回表示剪切变换的变换
                itemp = op.filter(bi, null);//转换源 BufferedImage 并将结果存储在目标 BufferedImage 中。
            }
            if (bb) {//补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);//构造一个类型为预定义图像类型之一的 BufferedImage。
                Graphics2D g = image.createGraphics();//创建一个 Graphics2D，可以将它绘制到此 BufferedImage 中。
                g.setColor(Color.white);//控制颜色
                g.fillRect(0, 0, width, height);// 使用 Graphics2D 上下文的设置，填充 Shape 的内部区域。
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            File resultFile  = new File(result);
            if (!resultFile.exists()){
                resultFile.mkdirs();
            }
            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));      //输出压缩图片
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
