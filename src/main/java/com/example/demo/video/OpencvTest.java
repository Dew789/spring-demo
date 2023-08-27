//package com.example.demo.video;
//
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//
//import java.net.URL;
//import java.util.Arrays;
//
//import static org.opencv.highgui.HighGui.imshow;
//import static org.opencv.highgui.HighGui.waitKey;
//import static org.opencv.imgcodecs.Imgcodecs.imread;
//import static org.opencv.imgproc.Imgproc.*;
//
//public class OpencvTest {
//
//    private static double[] RED = {0.0, 0.0, 255.0, 0.5};
//
//    private static double[] YELLOW = {0.0, 255.0, 0.0, 0.5};
//
//    private static double[] TRANSPARENT = {0.0, 0.0, 0.0, 1};
//
//    public static void connect() throws Exception {
//
//        Mat image = imread("C:\\Users\\hangzhang2\\Desktop\\opencv_test\\red_dot.png");
//        if (image.empty()) {
//            throw new Exception("image is Empty");
//        }
//        imshow("Original Image", image);
//
//        // Convert to one channel and 8 signed int
//        Mat rice = new Mat(image.rows(), image.cols(), CvType.CV_8SC1);
//        cvtColor(image, rice, COLOR_RGB2GRAY);
//
//        // filter greater than 50
//        Mat riceBW = new Mat();
//        threshold(rice, riceBW, 1, 255, THRESH_BINARY);
//
//        Mat out = new Mat(riceBW.size(), riceBW.type());
//        Mat result = new Mat(rice.size(), image.type());
//        int number = connectedComponents(riceBW, out, 8);
//
//        int w = result.cols();
//        int h = result.rows();
//        for (int row = 0; row < h; row++)
//        {
//            for (int col = 0; col < w; col++)
//            {
//                int label = out.at(Integer.TYPE, row, col).getV();
//                if (label == 1)
//                {
//                    continue;
//                }
//                double[] red = {0.0, 0.0, 255.0};
//                result.put(row, col, red);
//            }
//        }
//
//        imshow("Processed Image", result);
//        waitKey();
//    }
//
//    // https://javamana.com/2021/04/20210419221007751b.html
//    public static void heatMap() throws Exception {
//        Mat image = imread("C:\\Users\\hangzhang2\\Desktop\\opencv_test\\heat_map.png");
//        if (image.empty()) {
//            throw new Exception("image is Empty");
//        }
//
//        // Convert to one channel and 8 signed int
//        Mat rice = new Mat(image.rows(), image.cols(), CvType.CV_8SC1);
//        cvtColor(image, rice, COLOR_RGB2GRAY);
//        imshow("Original Image", rice);
//
//        Mat result = new Mat(image.rows(), image.cols(), CvType.CV_8SC3);
//
//        int w = rice.cols();
//        int h = rice.rows();
//        for (int row = 0; row < h; row++)
//        {
//            for (int col = 0; col < w; col++)
//            {
//                double[] label = rice.get(row, col);
//                System.out.println(Arrays.toString(label));
//                if (label[0] > 50 &&  label[0] < 100) {
//                    double[] red = {0.0, 0.0, 255.0};
//                    result.put(row, col, red);
//                }
//                if (label[0] > 100 ) {
//                    double[] yellow = {0.0, 255.0, 0.0};
//                    result.put(row, col, yellow);
//                }
//            }
//        }
//
//        imshow("Processed Image", result);
//        waitKey();
//
//    }
//    public static void main(String[] args) throws Exception {
//        System.setProperty("java.awt.headless", "false");
//        System.out.println(System.getProperty("java.library.path"));
//
//        URL url = ClassLoader.getSystemResource("lib/opencv/opencv_java455.dll");
//        System.load(url.getPath());
//        System.out.println(url.getPath());
//
//        heatMap();
//    }
//}
