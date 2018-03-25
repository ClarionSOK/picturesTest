package marketgidPictureTest;

import org.junit.Test;

import static io.restassured.RestAssured.when;

public class PictureLoadTestRu {
    public String[] domains = {"marketgid.com","lentainform.com","tovarro.com"};
    public int domainsNumber = 0;
    public String[] sizeList = {"80x53","100x75","100x60","170x90","300x200","150x100","370x200","180x120","260x170","200x150","492x328","300x260","45x45","75x75","60x60","120x120","200x200","140x140","90x90","328x328"};
    public int sizeListNumber = 0;

    @Test
    public void getStatusCode() {
        when().
                get("http://imgg." + domains[domainsNumber] + "/57471/57471266_" + sizeList[sizeListNumber] + ".jpg").
                then().assertThat().statusCode(200);
        System.out.println("http://imgg." + domains[domainsNumber] + "/57471/57471266_" + sizeList[sizeListNumber] + ".jpg");
    }
    @Test
    public void statusCodeCheck(){
        while (sizeListNumber < 20 && domainsNumber < 3) {
            getStatusCode();
            sizeListNumber++;
            if (sizeListNumber == 20) {
                sizeListNumber = 0;
                domainsNumber++;
                System.out.println(domainsNumber);
            }
        }
    }
}
