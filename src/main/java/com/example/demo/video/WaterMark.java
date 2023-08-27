//package com.example.demo.video;
//
//import org.bytedeco.javacv.*;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.opencv.global.opencv_core;
//import org.bytedeco.opencv.global.opencv_imgcodecs;
//import org.bytedeco.opencv.opencv_core.Mat;
//import org.bytedeco.opencv.opencv_core.Point;
//import org.bytedeco.opencv.opencv_core.Rect;
//import org.bytedeco.opencv.opencv_core.Scalar;
//import sun.font.FontDesignMetrics;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//
//public class WaterMark {
//
//    //设置修改视频的路径
//    public static final String VIDEO_OLD_PATH =
//            "C:\\Users\\hangzhang2\\Desktop\\opencv_test\\361K5FG847HT1643323422618.mp4";
//    //设置新生产视频的路径
//    public static final String VIDEO_NEW_PATH =
//            "C:\\Users\\hangzhang2\\Desktop\\opencv_test\\water_mark.mp4";
//
//    //设置新生产视频的路径
//    public static final String PICTURE_PATH =
//            "C:\\Users\\hangzhang2\\Desktop\\opencv_test\\heat_map.png";
//
//    private static final String FONT_PATH =
//            "C:\\Windows\\Fonts\\ARIALUNI.TTF";
//
//    public static void addTextByGraphics(String context) throws Exception {
//        // 设置源视频、加字幕后的视频文件路径
//        FFmpegFrameGrabber grabber =
//                new FFmpegFrameGrabber(VIDEO_OLD_PATH);
//        grabber.start();
//
//        FFmpegFrameRecorder recorder =
//                new FFmpegFrameRecorder(VIDEO_NEW_PATH,
//                        grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());
//
//
//        // 视频相关配置，取原视频配置
//        recorder.setFrameRate(grabber.getFrameRate());
//        recorder.setVideoCodec(grabber.getVideoCodec());
//        recorder.setVideoBitrate(grabber.getVideoBitrate());
//
//        // 音频相关配置，取原音频配置
//        recorder.setSampleRate(grabber.getSampleRate());
//        recorder.setAudioCodec(grabber.getAudioCodec());
//        recorder.setAudioBitrate(grabber.getAudioBitrate());
//        recorder.start();
//        addText(grabber, recorder);
//        Java2DFrameConverter converter = new Java2DFrameConverter();
//
//        Frame frame;
//        while ((frame =grabber.grab()) != null) {
//            // 从视频帧中获取图片
//            if (frame.image != null) {
//                BufferedImage bufferedImage = converter.getBufferedImage(frame);
//                // 对图片进行文本合入
//                bufferedImage = addImage(bufferedImage);
//                // 视频帧赋值，写入输出流
//                recorder.record(converter.getFrame(bufferedImage));
//            }
//            //配置音乐
//            if (frame.samples != null) {
//                recorder.record(frame);
//            }
//
//        }
//
//        recorder.close();
//        grabber.close();
//    }
//
//    private static void addText(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder) throws Exception {
//        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
//        // 水印文字位置
//        Point point = new Point(10, 50);
//        // 颜色
//        Scalar scalar = new Scalar(0, 255, 255, 0);
//        Mat logo = opencv_imgcodecs.imread(PICTURE_PATH);
//
//        int x = 400;
//        int y = 400;
//
//        Frame frame;
//        while ((frame =grabber.grab()) != null) {
//            // 从视频帧中获取图片
//            if (frame.image != null) {
//                Mat mat = converter.convertToMat(frame);
//                x = x > 600 ? 400 : x+1;
//                y = y > 600 ? 400 : x+1;
//                Mat ROI = mat.apply(new Rect(x, y, logo.cols(), logo.rows()));
//                opencv_core.addWeighted(ROI, 0.5, logo, 1.0 - 0.5, 0.0, ROI);
//
////                opencv_imgproc.putText(mat, "eguid!", point, opencv_imgproc.CV_FONT_VECTOR0, 1.2, scalar, 1, 20, false);
//                recorder.record(converter.convert(mat));
//            }
//            //配置音乐
//            if (frame.samples != null) {
//                recorder.record(frame);
//            }
//        }
//
//        recorder.close();
//        grabber.close();
//    }
//
//
//    /**
//     * 图片添加文本
//     *
//     * @param bufImg bufImg
//     * @param content content
//     * @return BufferedImage
//     */
//    private static BufferedImage addText(BufferedImage bufImg, String content) {
//        Graphics2D graphics = bufImg.createGraphics();
//        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
//
//        //设置图片背景
//        graphics.drawImage(bufImg, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
//
//        //设置字体
//        Font font= new Font("宋体", Font.BOLD, 32);
//        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
//        graphics.setFont(font);
//        int textWidth =metrics.stringWidth(content);
//        graphics.drawString(content, (bufImg.getWidth() - textWidth) / 2, bufImg.getHeight() / 2);
//        graphics.dispose();
//        return bufImg;
//    }
//
//    /**
//     * 图片添加图片
//     *
//     * @param bufImg bufImg
//     * @return BufferedImage
//     */
//    private static BufferedImage addImage(BufferedImage bufImg) throws Exception {
//        Graphics2D graphics = bufImg.createGraphics();
//        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
//
//        //设置图片背景
//        graphics.drawImage(bufImg, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
//
//        // 添加图片
//        BufferedImage img = ImageIO.read(new File(PICTURE_PATH));
//        graphics.drawImage(img, bufImg.getWidth() / 2, bufImg.getHeight() / 2, 100,100,null);
//
//        graphics.dispose();
//        return bufImg;
//    }
//
//
//
//    public static void main(String[] args) throws Exception {
//        addTextByGraphics("Hello World");
//    }
//
//}
