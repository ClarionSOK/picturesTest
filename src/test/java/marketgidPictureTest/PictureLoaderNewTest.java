package marketgidPictureTest;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static marketgidPictureTest.PictureLoaderNewLogic.*;

/**
 * Created by dell on 11.06.2018.
 */
public class PictureLoaderNewTest {
    @Before //Forming data arrays and deleting picture files
    public void preparingMethod() {
        formArrays();
        deletePictures();
    }
    @Test //Perform testing if sizes, domains, types, extension and protocol
    public void statusCodeTest() throws IOException {
        PictureLoaderNewLogic.checkStandardParametersImageLink();
    }

    @Test //Perform crops, logos,  custom sizes and hash check
    public void cropTest() throws IOException, NoSuchAlgorithmException {
        checkLinksWithDifferentCrops();
    }
}
