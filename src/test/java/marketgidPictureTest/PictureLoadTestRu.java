package marketgidPictureTest;

import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;
import org.junit.Test;

import static io.restassured.RestAssured.when;

public class PictureLoadTest {
    public String[] domains = {"marketgid.com","lentainform.com","tovarro.com"};
    public int domainsNumber = 0;
    public String[] sizeList = {"80x53","100x75","100x60","170x90","300x200","150x100","370x200","180x120","260x170","200x150","492x328","300x260","45x45","75x75","60x60","120x120","200x200","140x140","90x90","328x328"};
    public int sizeListNumber = 0;

    @Test
    public void getStatusCodeTest () {
        when().
                get("http://imgg." + domains[domainsNumber] + "/57471/57471266_" + sizeList[sizeListNumber] + ".jpg").
                then().assertThat().statusCode(200);
        System.out.println("http://imgg." + domains[domainsNumber] + "/57471/57471266_" + sizeList[sizeListNumber] + ".jpg");
    }
    @Test
    public void sizeCheck(){
        while (sizeListNumber < 20 && domainsNumber < 3) {
            getStatusCodeTest();
            sizeListNumber++;
            if (sizeListNumber == 20) {
                sizeListNumber = 0;
                domainsNumber++;
                System.out.println(domainsNumber);
            }
        }
    }
    @Test
    public void domainCheck(){
        while (domainsNumber < 3) {
            sizeCheck();
            domainsNumber++;
        }


    }
}
