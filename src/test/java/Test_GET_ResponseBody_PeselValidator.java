import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class Test_GET_ResponseBody_PeselValidator {

    @DataProvider
    public Object[][] genderCheck() {
        return new Object[][]{
                {"01071651206", "Female"},
                {"01071651216", "Male"},
                {"01071651226", "Female"},
                {"01071651236", "Male"},
                {"01071651246", "Female"},
                {"01071651256", "Male"},
                {"01071651266", "Female"},
                {"01071651276", "Male"},
                {"01071651286", "Female"},
                {"01071651296", "Male"}
        };
    }

    @Test(dataProvider = "genderCheck")
    public void checkingGenderTest(String listOfPeselas, String expectedPeselas) {
        String urlAPI = ("https://peselvalidatorapsitest.azurewebsites.net/api/Pesel?pesel=");
        Response response=get(urlAPI+=listOfPeselas);
        String gender = response.path("gender");
        Assert.assertEquals(gender,expectedPeselas);
    }


    /** MALE BODY ELEMENTS */
    //Checking Body Elements if male pesel is correct identify date of birth
    @Test
    public static void testMalePeselIsCorrectIdentifiedDate(){
        Response response=get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00013197214");
        String gender = response.path("dateOfBirth");
        Assert.assertEquals(gender, "1900-01-31T00:00:00", "Pesel date is not valid");
    }

    //Checking Body Elements if male pesel is correct identify gender
    @Test
    public static void testMalePeselIsCorrectIdentifiedGender(){
        Response response=get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00013197214");
        String gender = response.path("gender");
        Assert.assertEquals(gender, "Male", "Pesel gender is not valid");
    }

    /** FEMALE BODY ELEMENTS */
    //Checking Body Elements if male pesel is correct identify date of birth
    @Test
    public static void testFemalePeselIsCorrectIdentifiedDate(){
        Response response=get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00013117425");
        String gender = response.path("dateOfBirth");
        Assert.assertEquals(gender, "1900-01-31T00:00:00", "Pesel date is not valid");
    }

    //Checking Body Elements if female pesel is correct identify gender
    @Test
    public static void testFemalePeselIsCorrectIdentified(){
        Response response=get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00013117425");
        String gender = response.path("gender");
        Assert.assertEquals(gender, "Female", "Pesel gender is not valid");
    }

    /**1800-1899*/
    //Valid male pesel 1850-01-31
    @Test
    public void testMaleValidPeselResponseBody1800() {
        String expectedBody = "{\"pesel\":\"50813167115\",\"isValid\":true,\"dateOfBirth\":\"1850-01-31T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=50813167115");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid male pesel leap year 1804-02-29
    @Test
    public void testMaleValidPeselLeapYearResponseBody1800() {
        String expectedBody = "{\"pesel\":\"04822973917\",\"isValid\":true,\"dateOfBirth\":\"1804-02-29T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=04822973917");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid female pesel 1800-01-31
    @Test
    public void testFemaleValidPeselResponseBody1800() {
        String expectedBody = "{\"pesel\":\"00813138808\",\"isValid\":true,\"dateOfBirth\":\"1800-01-31T00:00:00\",\"gender\":\"Female\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00813138808");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid female pesel leap year 1804-02-29
    @Test
    public void testFemaleValidPeselLeapYearResponseBody1800() {
        String expectedBody = "{\"pesel\":\"04822906803\",\"isValid\":true,\"dateOfBirth\":\"1804-02-29T00:00:00\",\"gender\":\"Female\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=04822906803");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Invalid day of month Male pesel 1800-01-32
    @Test
    public void testMaleInvalidDayOfMonthResponseBody1800() {
        String expectedBody = "{\"pesel\":\"00813298813\",\"isValid\":false,\"dateOfBirth\":null,\"gender\":\"Male\",\"errors\":[{\"errorCode\":\"INVC\",\"errorMessage\":\"Check sum is invalid. Check last digit.\"},{\"errorCode\":\"INVD\",\"errorMessage\":\"Invalid day.\"}]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00813298813");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Invalid day of month female pesel 1800-01-32
    @Test
    public void testFemaleVvalidDayOfMonthResponseBody1800() {
        String expectedBody = "{\"pesel\":\"00923287608\",\"isValid\":false,\"dateOfBirth\":null,\"gender\":\"Female\",\"errors\":[{\"errorCode\":\"INVC\",\"errorMessage\":\"Check sum is invalid. Check last digit.\"},{\"errorCode\":\"INVD\",\"errorMessage\":\"Invalid day.\"}]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00923287608");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Invalid month digit MALE 1800-13-31
    @Test
    public void testMaleInvalidMonthDigitResponseBody1800() {
        String expectedBody = "{\"pesel\":\"00933171117\",\"isValid\":false,\"dateOfBirth\":null,\"gender\":\"Male\",\"errors\":[{\"errorCode\":\"INVC\",\"errorMessage\":\"Check sum is invalid. Check last digit.\"},{\"errorCode\":\"INVY\",\"errorMessage\":\"Invalid year.\"},{\"errorCode\":\"INVM\",\"errorMessage\":\"Invalid month.\"},{\"errorCode\":\"INVD\",\"errorMessage\":\"Invalid day.\"}]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00933171117");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid month digit MALE 1800-12-31
    @Test
    public void testMaleValidMonthDigitResponseBody1800() {
        String expectedBody = "{\"pesel\":\"00923129411\",\"isValid\":true,\"dateOfBirth\":\"1800-12-31T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00923129411");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    /**1900-1999*/
    //Valid male pesel 1900-01-31
    @Test
    public void testMaleValidPeselResponseBody() {
        String expectedBody = "{\"pesel\":\"00013158718\",\"isValid\":true,\"dateOfBirth\":\"1900-01-31T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00013158718");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid male pesel leap year 1904-02-29
    @Test
    public void testMaleValidPeselLeapYearResponseBody() {
        String expectedBody = "{\"pesel\":\"04022908856\",\"isValid\":true,\"dateOfBirth\":\"1904-02-29T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=04022908856");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid female pesel 1900-01-31
    @Test
    public void testFemaleValidPeselResponseBody() {
        String expectedBody = "{\"pesel\":\"00013150406\",\"isValid\":true,\"dateOfBirth\":\"1900-01-31T00:00:00\",\"gender\":\"Female\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00013150406");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid female pesel leap year 1904-02-29
    @Test
    public void testFemaleValidPeselLeapYearResponseBody() {
        String expectedBody = "{\"pesel\":\"04022937243\",\"isValid\":true,\"dateOfBirth\":\"1904-02-29T00:00:00\",\"gender\":\"Female\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=04022937243");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Invalid day of month Male pesel 1900-01-32
    @Test
    public void testMaleInvalidDayOfMonthResponseBody() {
        String expectedBody = "{\"pesel\":\"00013258718\",\"isValid\":false,\"dateOfBirth\":null,\"gender\":\"Male\",\"errors\":[{\"errorCode\":\"INVC\",\"errorMessage\":\"Check sum is invalid. Check last digit.\"},{\"errorCode\":\"INVD\",\"errorMessage\":\"Invalid day.\"}]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00013258718");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Invalid day of month female pesel 1900-01-32
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

    /**2000-2099*/
    //Valid male pesel 2001-01-31
    @Test
    public void testMaleValidPeselResponseBody2000() {
        String expectedBody = "{\"pesel\":\"01213128019\",\"isValid\":true,\"dateOfBirth\":\"2001-01-31T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=01213128019");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid male pesel leap year 2004-02-29
    @Test
    public void testMaleValidPeselLeapYearResponseBody2000() {
        String expectedBody = "{\"pesel\":\"04222972611\",\"isValid\":true,\"dateOfBirth\":\"2004-02-29T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=04222972611");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid female pesel 2000-01-31
    @Test
    public void testFemaleValidPeselResponseBody2000() {
        String expectedBody = "{\"pesel\":\"00213102300\",\"isValid\":true,\"dateOfBirth\":\"2000-01-31T00:00:00\",\"gender\":\"Female\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00213102300");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid female pesel leap year 2004-02-29
    @Test
    public void testFemaleValidPeselLeapYearResponseBody2000() {
        String expectedBody = "{\"pesel\":\"04222924908\",\"isValid\":true,\"dateOfBirth\":\"2004-02-29T00:00:00\",\"gender\":\"Female\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=04222924908");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Invalid day of month Male pesel 2000-01-32
    @Test
    public void testMaleInvalidDayOfMonthResponseBody2000() {
        String expectedBody = "{\"pesel\":\"00213208610\",\"isValid\":false,\"dateOfBirth\":null,\"gender\":\"Male\",\"errors\":[{\"errorCode\":\"INVC\",\"errorMessage\":\"Check sum is invalid. Check last digit.\"},{\"errorCode\":\"INVD\",\"errorMessage\":\"Invalid day.\"}]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00213208610");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Invalid day of month female pesel 2000-01-32
    @Test
    public void testMaleVvalidDayOfMonthResponseBody2000() {
        String expectedBody = "{\"pesel\":\"00213260306\",\"isValid\":false,\"dateOfBirth\":null,\"gender\":\"Female\",\"errors\":[{\"errorCode\":\"INVC\",\"errorMessage\":\"Check sum is invalid. Check last digit.\"},{\"errorCode\":\"INVD\",\"errorMessage\":\"Invalid day.\"}]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00213260306");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Valid month digit MALE 2000-06-31
    @Test
    public void testMaleValidMonthDigitResponseBody2000() {
        String expectedBody = "{\"pesel\":\"00263082418\",\"isValid\":true,\"dateOfBirth\":\"2000-06-30T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00263082418");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Invalid month digit MALE 2000-13-31
    @Test
    public void testMaleInvalidMonthDigitResponseBody2000() {
        String expectedBody = "{\"pesel\":\"00323069715\",\"isValid\":true,\"dateOfBirth\":\"2000-12-30T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00323069715");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    /**2000-2199*/
    //Valid male pesel 2100-11-02
    @Test
    public void testMaleValidPeselResponseBody2100() {
        String expectedBody = "{\"pesel\":\"00510286611\",\"isValid\":true,\"dateOfBirth\":\"2100-11-02T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00510286611");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Invalid male pesel 2100-11-02
    @Test
    public void testMaleInvalidPeselResponseBody2100() {
        String expectedBody = "{\"pesel\":\"00511286611\",\"isValid\":false,\"dateOfBirth\":\"2100-11-12T00:00:00\",\"gender\":\"Male\",\"errors\":[{\"errorCode\":\"INVC\",\"errorMessage\":\"Check sum is invalid. Check last digit.\"}]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=00511286611");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    /**2200-2299*/
    //Valid male pesel 2210-11-02
    @Test
    public void testMaleValidPeselResponseBody2200() {
        String expectedBody = "{\"pesel\":\"10710265213\",\"isValid\":true,\"dateOfBirth\":\"2210-11-02T00:00:00\",\"gender\":\"Male\",\"errors\":[]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=10710265213");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }

    //Invalid male pesel 2100-11-02
    @Test
    public void testMaleInvalidPeselResponseBody2200() {
        String expectedBody = "{\"pesel\":\"10710265214\",\"isValid\":false,\"dateOfBirth\":\"2210-11-02T00:00:00\",\"gender\":\"Male\",\"errors\":[{\"errorCode\":\"INVC\",\"errorMessage\":\"Check sum is invalid. Check last digit.\"}]}";
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=10710265214");
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody);
    }
}
