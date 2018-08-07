package marketgidPictureTest;


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




public class PictureLoaderOldLogic {

    private static String domainsPathRu = "src/files/domainsRu.txt";
    private static String domainsPathUs = "src/files/domainsUs.txt";
    private static String domainsPath = domainsPathRu;
    private static String[] domainsList;
    private static int domainsListIndex = 0;

    private static String sizesPathRu = "src/files/sizesRu.txt";
    private static String sizesPathUs = "src/files/sizesUs.txt";
    private static String sizesPath = sizesPathRu;
    private static String[] sizesList;
    private static int sizeListIndex = 0;
    private static String actualSize;


    private static String imageLink;
    private static BufferedImage image = null;
    private static URL url;
    private static String picturesFolderPathRu = "src/files/picturesOldRu";
    private static String picturesFolderPathUs = "src/files/picturesOldUs";
    private static String picturesFolderPath = picturesFolderPathRu;

    private static String teaserRu = "/2549/2549810_";
    private static String teaserUs = "/2549/2549810_";
    private static String teaser = teaserRu;

    private static String protocol = "http://";
    private static String protocolName = "http";

    private static int iterationIndex = 0;


    //Form arrays of domains and sizes from files
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

    //Deleting old pictures before test
    public static void deletePictures(){
        for (File picture : new File(picturesFolderPath).listFiles()) {
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
    public static void getImage() throws IOException {
        imageLink = protocol + domainsList[domainsListIndex] + teaser + sizesList[sizeListIndex] + ".jpg";
        System.out.println(imageLink);
        when().
                get(imageLink).
                then().assertThat().statusCode(200);
        url = new URL(imageLink);
        image = ImageIO.read(url);
        actualSize = image.getWidth() + "x" + image.getHeight();
        System.out.println(actualSize);
        if (!((sizesList[sizeListIndex]).equals("quadratic")) && !((sizesList[sizeListIndex]).equals("origin"))) {
            Assert.assertEquals(actualSize, sizesList[sizeListIndex]);
        }
        //File outputPicture = new File(picturesFolderPath + "/" + protocolName + "_" + domainsList[domainsListIndex]  +  "_" + sizesList[sizeListIndex]  + ".jpg");
        //ImageIO.write(image, "jpg", outputPicture);
    }
    //Parsing arrays
    public static void imageCheck() throws IOException {
        while (sizeListIndex < sizesList.length && domainsListIndex < domainsList.length && iterationIndex < 4) {
            getImage();
            sizeListIndex++;
            if (sizeListIndex == sizesList.length) {
                sizeListIndex = 0;
                domainsListIndex++;
                System.out.println("=============================================");
                if (domainsListIndex == domainsList.length){
                    protocol = "http://";
                    protocolName = "http";
                    System.out.println("********************** End of iteration #" + iterationIndex + " **********************");
                    iterationIndex++;
                    domainsListIndex = 0;
                    if (iterationIndex == 2){
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
