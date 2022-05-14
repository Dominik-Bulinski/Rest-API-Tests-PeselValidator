import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class Test_GET_ResponseBody_PeselValidator {

    //  Valid male pesel 1900-01-31
    @Test
    public void testMaleValidPeselResponseBody() {
        String expectedBody = "{\"pesel\":\"00013158718\",\"isValid\":true,\"dateOfBirth\":\"1900-01-31T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00013158718");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //    Valid female pesel 1900-01-31
    @Test
    public void testFemaleValidPeselResponseBody() {
        String expectedBody = "{\"pesel\":\"00013150406\",\"isValid\":true,\"dateOfBirth\":\"1900-01-31T00:00:00\",\"gender\":\"Female\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00013150406");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //  Invalid day of month Male pesel 1900-01-32
    @Test
    public void testMaleInvalidDayOfMonthResponseBody() {
        String expectedBody = "{\"pesel\":\"00013258718\",\"isValid\":false,\"dateOfBirth\":null,\"gender\":\"Male\",\"errors\":[{\"errorCode\":\"INVC\",\"errorMessage\":\"Check sum is invalid. Check last digit.\"},{\"errorCode\":\"INVD\",\"errorMessage\":\"Invalid day.\"}]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00013258718");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //  Invalid day of month female pesel 1900-01-32
    @Test
    public void testMaleVvalidDayOfMonthResponseBody() {
        String expectedBody = "{\"pesel\":\"00013258718\",\"isValid\":false,\"dateOfBirth\":null,\"gender\":\"Male\",\"errors\":[{\"errorCode\":\"INVC\",\"errorMessage\":\"Check sum is invalid. Check last digit.\"},{\"errorCode\":\"INVD\",\"errorMessage\":\"Invalid day.\"}]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00013258718");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid month digit MALE 1900-06-31
    @Test
    public void testMaleValidMonthDigitResponseBody() {
        String expectedBody = "{\"pesel\":\"00070119396\",\"isValid\":true,\"dateOfBirth\":\"1900-07-01T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00070119396");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Invalid month digit MALE 1900-13-31
    @Test
    public void testMaleInvalidMonthDigitResponseBody() {
        String expectedBody = "{\"pesel\":\"00133191091\",\"isValid\":false,\"dateOfBirth\":null,\"gender\":\"Male\",\"errors\":[{\"errorCode\":\"INVY\",\"errorMessage\":\"Invalid year.\"},{\"errorCode\":\"INVM\",\"errorMessage\":\"Invalid month.\"},{\"errorCode\":\"INVD\",\"errorMessage\":\"Invalid day.\"}]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00133191091");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }


}
