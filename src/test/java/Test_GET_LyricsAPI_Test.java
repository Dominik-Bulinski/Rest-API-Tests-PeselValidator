import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class Test_GET_LyricsAPI_Test {

    @Test
    public static void validLyricsSearchGet_ResponseCode(){
        Response response = get("https://api.lyrics.ovh/v1/Coldplay/Adventure of a Lifetime");
        Assert.assertEquals(response.statusCode(),200,"Status code does not contain 200");
        System.out.println(response.asString().contains("Turn your magic on"));
    }

    @Test
    public static void invalidLyricsSearchGet_ResponseCode(){
        Response response = get("https://api.lyrics.ovh/v1/Coldplay/XYZ");
        Assert.assertEquals(response.statusCode(),404,"Status code does not contain 404");
        System.out.println(response.asString().contains("XYZ"));
    }
}
