package marketgidPictureTest;

import org.junit.Before;
import org.junit.Test;
import pictureTester.PictureLoaderRuLogic;
import java.io.IOException;

import static pictureTester.PictureLoaderRuLogic.*;

public class PictureLoadTestRu {
    private static String domainsPath = "src/files/domainsRu.txt";
    private static String sizesPath = "src/files/sizesRu.txt";


    @Before //Forming data arrays and deleting picture files
    public void preparingMethod() {
        formArrays();
        deletePictures();
    }
    @Test
    public void statusCodeTest() throws IOException {
        PictureLoaderRuLogic.getStatusCodes();
    }
}
