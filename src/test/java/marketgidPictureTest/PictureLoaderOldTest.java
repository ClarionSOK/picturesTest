package marketgidPictureTest;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static marketgidPictureTest.PictureLoaderOldLogic.*;

public class PictureLoaderOldTest {


    @Before //Forming data arrays and deleting picture files
    public void preparingMethod() {
        formArrays();
        deletePictures();
    }
    @Test //Perform testing
    public void statusCodeTest() throws IOException {
        PictureLoaderOldLogic.imageCheck();
    }
}
