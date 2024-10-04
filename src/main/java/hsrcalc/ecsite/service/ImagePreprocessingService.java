package hsrcalc.ecsite.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImagePreprocessingService {
    // static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static String modifyImage(String filePath) throws IOException, ClassNotFoundException, InstantiationException, 
                                                  IllegalAccessException, NoSuchMethodException, 
                                                  IllegalArgumentException, InvocationTargetException {

        Loader.load(opencv_java.class);
        int pos = filePath.length() - 4;
        String newFile = filePath.substring(0, pos) + "-BW" 
                         + filePath.substring(pos);

        // Load the image
        Mat image = Imgcodecs.imread(filePath, Imgcodecs.IMREAD_GRAYSCALE);
        
        if (image.empty()) {
            System.out.println("Could not open or find the image!");
            return filePath;
        }

        // Apply a median blur to remove small dots
        Mat denoisedImage = new Mat();
        Imgproc.medianBlur(image, denoisedImage, 3);

        // Save the processed image
        Imgcodecs.imwrite(newFile, denoisedImage);

        // FOR DEBUGGING
        // System.out.println("Image processing completed. Output saved at: " + newFile);

        return newFile;
    }
}
