package marketgidPictureTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by dell on 12.06.2018.
 */
public class RGBConverter {

    private static BufferedImage image;
    private static BufferedImage etalonImage;
    private static URL url;
    private static File file;


    public static int[][][] convert(String imageUrl, String etalonImageFile) throws IOException {
        url = new URL(imageUrl);
        file = new File(etalonImageFile);
        image = ImageIO.read(url);
        etalonImage = ImageIO.read(file);
        int[][][] imagesRGBArray = {pixelParcer(image), pixelParcer(etalonImage)} ;
        return imagesRGBArray;
    }

    private static int[][] pixelParcer(BufferedImage imageForParsing){
        int y = imageForParsing.getHeight();
        int x = imageForParsing.getWidth();
        int[][] pixelArray = new int[x][y];
        for (int i = 0; i < x; i++ ){
            for (int j = 0; j < y; j++){
                pixelArray[i][j] = imageForParsing.getRGB(i, j);
            }
        }
        return pixelArray;
    }
}
