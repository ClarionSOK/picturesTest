package pictureTester;


import org.junit.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.when;




public class PictureLoaderRuLogic {

    private static String domainsPathRu = "src/files/domainsRu.txt";
    private static String domainsPathUs = "src/files/domainsUs.txt";
    private static String domainsPath = domainsPathRu;
    private static String sizesPathRu = "src/files/sizesRu.txt";
    private static String sizesPathUs = "src/files/sizesUs.txt";
    private static String sizesPath = sizesPathRu;
    private static String[] domainsList;
    private static String[] sizesList;
    private static int domainsNumber = 0;
    private static int sizeListNumber = 0;
    private static String imageLink;
    private static BufferedImage image = null;
    private static URL url;
    private static String size;
    private static String picturesFolderPathRu = "src/files/picturesRu";
    private static String picturesFolderPathUs = "src/files/picturesUs";
    private static String picturesFolderPath = picturesFolderPathRu;
    private static String teaserRu = "/57471/57471266_";
    private static String teaserUs = "/2757/2757134_";
    private static String teaser = teaserRu;
    private static String protocol = "http://";
    private static String protocolName = "http";
    private static int iterationNumber = 0;

    //private static String

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
    }


    public static boolean deletePictures(){
        boolean flag = false;
        for (File picture : new File(picturesFolderPath).listFiles()) {
            if (picture.isFile()) {
                picture.delete();
            } else {
                flag = true;
            }
        }
        return flag;
    }
    //Read file with domains and converts to array
    public static void readDomains(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
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
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        System.out.println(Arrays.toString(lines.toArray(new String[lines.size()])));
        sizesList = lines.toArray(new String[lines.size()]);
    }



    
    //Forming links and checking its status codes and parameters, saving pictures to folder
    public static void getStatusCode() throws IOException {
        imageLink = protocol + domainsList[domainsNumber] + teaser + sizesList[sizeListNumber] + ".jpg";
        when().
                get(imageLink).
                then().assertThat().statusCode(200);
        System.out.println(imageLink);
        url = new URL(imageLink);
        image = ImageIO.read(url);
        size = image.getWidth() + "x" + image.getHeight();
        System.out.println(size);
        Assert.assertEquals(size, sizesList[sizeListNumber]);
        File outputPicture = new File(picturesFolderPath + "/" + protocolName + "_" + domainsList[domainsNumber]  +  "_" + sizesList[sizeListNumber]  + ".jpg");
        ImageIO.write(image, "jpg", outputPicture);
    }
    //Parsing arrays
    public static void getStatusCodes() throws IOException {
        while (sizeListNumber < sizesList.length && domainsNumber < domainsList.length && iterationNumber < 4) {
            getStatusCode();
            sizeListNumber++;
            if (sizeListNumber == 20) {
                sizeListNumber = 0;
                domainsNumber++;
                System.out.println("=============================================");
                if (domainsNumber == domainsList.length){
                    protocol = "https://";
                    protocolName = "https";
                    iterationNumber ++;
                    domainsNumber = 0;
                    if (iterationNumber == 2){
                        teaser = teaserUs;
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
}
