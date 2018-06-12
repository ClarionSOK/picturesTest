package marketgidPictureTest;

import org.junit.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.when;

/**
 * Created by dell on 11.06.2018.
 */
public class PictureLoaderNewLogic {
    //Domains
    private static String domainsPathRu = "src/files/domainsNewRu.txt";
    private static String domainsPathUs = "src/files/domainsNewUs.txt";
    private static String domainsPath = domainsPathRu;
    private static String[] domainsList;
    private static int domainsListIndex = 0;

    //Sizes
    private static String sizesPathRu = "src/files/sizesRu.txt";
    private static String sizesPathUs = "src/files/sizesUs.txt";
    private static String sizesPath = sizesPathRu;
    private static String[] sizesList;
    private static int sizeListIndex = 2;
    private static String actualSize;

    //Images
    private static String picturesFolderPathRu = "src/files/picturesNewRu";
    private static String picturesFolderPathUs = "src/files/picturesNewUs";
    private static String picturesFolderPath = picturesFolderPathRu;
    private static BufferedImage image;
    private static String imageLink;
    private static String[] cropedImagesList;
    private static String cropedImagesListPath = "src/files/cropTestImagesUrls.txt";
    private static int cropedImagesListIndex = 0;
    private static String[] etalonImageList;
    private static String etalonImageListPath = "src/files/etalonImageList.txt";
    private static int etalonImageListIndex = 0;
    private static int[][][] imagesArray;
    private static File outputPicture;

    //Protocols
    private static String protocol = "http://";
    private static String protocolName = "http";

    //Campaign type
    private static String[] campaignType = {"g", "gc", "n", "nc", "l"};
    private static int campaignTypeIndex = 0;

    //Extensions
    private static String[] extension = {".jpg", ".png", ".gif", ".test"};
    private static int extensionIndex = 0;

    //Various
    private static String base64Link = "aHR0cHM6Ly9pbWdnLWNkbi5tZ2lkLmNvbS8yNDk3LzI0OTc5OTJfNDkyeDMyOC5qcGc";
    private static URL url;
    private static int iterationIndex = 0;



    public static void formArrays(){
        try {
            readDomains(domainsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            readSizes(sizesPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            readEtalon(etalonImageListPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            readCropImages(cropedImagesListPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deletePictures(){
        for (File picture : new File(picturesFolderPath).listFiles()) {
            while (picture.isFile()) {
                picture.delete();
            }
        }
        for (File picture : new File("src/files/croppedImages").listFiles()) {
            while (picture.isFile()) {
                picture.delete();
            }
        }
    }
    //Read file with domains and converts to array
    public static void readDomains(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        System.out.println(Arrays.toString(lines.toArray(new String[lines.size()])));
        domainsList = lines.toArray(new String[lines.size()]);
    }

    //Read file with sizes and converts to array
    public static void readSizes(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        System.out.println(Arrays.toString(lines.toArray(new String[lines.size()])));
        sizesList = lines.toArray(new String[lines.size()]);
    }

    //Read file with cropped images urls
    public static void readCropImages(String filename) throws IOException{
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        System.out.println(Arrays.toString(lines.toArray(new String[lines.size()])));
        cropedImagesList = lines.toArray(new String[lines.size()]);
    }

    //Read file with etalon images
    public static void readEtalon(String filename) throws IOException{
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        System.out.println(Arrays.toString(lines.toArray(new String[lines.size()])));
        etalonImageList = lines.toArray(new String[lines.size()]);
    }

    //Checking standard sizes, domains, types, extension and protocol
    public static void getStandardParametersImageLink() throws IOException {
        campaignTypeIndex = (int) (Math.random() * 5);
        extensionIndex = (int) (Math.random() * 4);
        imageLink = protocol + domainsList[domainsListIndex] + "/" + campaignType[campaignTypeIndex] + "/1/" + sizesList[sizeListIndex] + "/" + "50x10x492x328" +"/" + base64Link + extension[extensionIndex];
        System.out.println(imageLink);
        when().
                get(imageLink).
                then().assertThat().statusCode(200);
        url = new URL(imageLink);
        image = ImageIO.read(url);
        actualSize = image.getWidth() + "x" + image.getHeight();
        System.out.println(actualSize);
        Assert.assertEquals(actualSize, sizesList[sizeListIndex]);
        outputPicture = new File( picturesFolderPathRu + "/" + protocolName + "_" + URLEncoder.encode(domainsList[domainsListIndex], "UTF-32") + "_" + campaignType[campaignTypeIndex] + "_" + sizesList[sizeListIndex] + extension[extensionIndex]);
        ImageIO.write(image, "jpg", outputPicture);
    }

    public static void checkStandardParametersImageLink() throws IOException {
        while (sizeListIndex < sizesList.length && domainsListIndex < domainsList.length && iterationIndex < 2) {
            getStandardParametersImageLink();
            sizeListIndex++;
            if (sizeListIndex == sizesList.length){
                sizeListIndex = 2;
                domainsListIndex++;
                System.out.println("=============================================");
                if(domainsListIndex == domainsList.length){

                    /**
                     *  change to https on prod */

                    protocolName = "http";
                    protocol = "http://";
                    System.out.println("********************** End of iteration #" + iterationIndex + " **********************");
                    iterationIndex++;
                    domainsListIndex = 0;
                    if(iterationIndex == 2) {
                        domainsPath = domainsPathUs;
                        sizesPath = sizesPathUs;
                        picturesFolderPath = picturesFolderPathUs;
                        protocol = "http://";
                        protocolName = "http";
                        formArrays();
                        deletePictures();
                    }
                }
            }
        }
    }

    //Testing different crops, logos,  custom sizes and hash
    public static void getLinksWithDifferentCrops() throws IOException, NoSuchAlgorithmException {
        imageLink = cropedImagesList[cropedImagesListIndex];
        System.out.println(imageLink);
        when().
                get(imageLink).
                then().assertThat().statusCode(200);
        imagesArray = RGBConverter.convert(imageLink, etalonImageList[etalonImageListIndex]);
        Assert.assertTrue(Arrays.deepEquals(imagesArray[0], imagesArray[1]));
        outputPicture = new File ("src/files/croppedImages/cropped_" + cropedImagesListIndex + ".jpg");
        url = new URL(imageLink);
        image = ImageIO.read(url);
        ImageIO.write(image, "jpg", outputPicture);
    }
    //
    public static void checkLinksWithDifferentCrops() throws IOException, NoSuchAlgorithmException {
        while (cropedImagesListIndex < 3 /** cropedImagesList.length */) {
            getLinksWithDifferentCrops();
            cropedImagesListIndex++;
            etalonImageListIndex++;
        }
    }
}
