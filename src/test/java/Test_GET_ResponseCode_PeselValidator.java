import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class Test_GET_ResponseCode_PeselValidator {

    //Sending GET request with a valid pesel (male), expected 200 [OK]
    @Test
    public static void validMalePeselGet_ResponseCode(){
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=66101587196");
        Assert.assertEquals(response.statusCode(),200,"Status code does not contain 200");
    }

    //Sending GET request with a valid pesel (female), expected 200 [OK]
    @Test
    public static void validFemalePeselGet_ResponseCode(){
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=57111221909");
        Assert.assertEquals(response.statusCode(),200,"Status code does not contain 200");
    }

    //Sending GET request with invalid pesel (male), expected 200 [OK]
    @Test
    public static void invalidPeselGet_ResponseCode(){
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=661015871W96");
        Assert.assertEquals(response.statusCode(),200,"Status code does not contain 200");
    }

    //Sending GET request with missing pesel argument, expected 200 [OK]
    @Test
    public static void missingPeselArgumentGet_ResponseCode(){
        Response response = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel");
        Assert.assertEquals(response.statusCode(),400,"Status code does not contain 400");
    }

}
